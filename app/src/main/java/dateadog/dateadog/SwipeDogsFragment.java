package dateadog.dateadog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dateadog.dateadog.tindercard.FlingCardListener;
import dateadog.dateadog.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwipeDogsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwipeDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeDogsFragment extends Fragment implements FlingCardListener.ActionDownInterface {

    private static final String TAG = MainActivity.class.getName();

    private OnFragmentInteractionListener mListener;

    /**
     * The minimum number of dogs left in the stack before more dogs are retrieved from the server.
     */
    private static final int REFRESH_DOGS_THRESHOLD = 5;

    private ArrayList<DogCard> dogCards;
    private SwipeFlingAdapterView flingContainer;
    private DADAPI dadapi;

    public static DogCardsAdapter dogCardsAdapter;
    public static ViewHolder viewHolder;

    public void updateUI() {
        dadapi.getNextDogs(new DADAPI.DogsDataListener() {
            @Override
            public void onGotDogs(Set<Dog> dogs) {
                for (Dog dog : dogs) {
                    dogCards.add(new DogCard(dog));
                }
                dogCardsAdapter.notifyDataSetChanged();
            }
        });
    }

    public SwipeDogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment SwipeDogsFragment
     */
    public static SwipeDogsFragment newInstance() {
        SwipeDogsFragment fragment = new SwipeDogsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dadapi = DADAPI.getInstance(getContext().getApplicationContext());
        dogCards = new ArrayList<>();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_dogs, container, false);
        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.swipeFlingAdapterView);
        dogCardsAdapter = new DogCardsAdapter(dogCards, getActivity());
        flingContainer.setAdapter(dogCardsAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() { }

            @Override
            public void onLeftCardExit(Object dataObject) {
                // This is a dislike dog swipe.
                DogCard dogCard = dogCards.get(0);
                dadapi.judgeDog(dogCard.getDogId(), false);
                dogCards.remove(0);
                dogCardsAdapter.notifyDataSetChanged();
                if (dogCards.size() <= REFRESH_DOGS_THRESHOLD) {
                    updateUI();
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                // This is a like dog swipe.
                DogCard dogProfile = dogCards.get(0);
                dadapi.judgeDog(dogProfile.getDogId(), true);
                dogCards.remove(0);
                dogCardsAdapter.notifyDataSetChanged();
                if (dogCards.size() <= REFRESH_DOGS_THRESHOLD) {
                    updateUI();
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) { }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActionDownPerform() { }

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        dogCardsAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static class ViewHolder {
        public FrameLayout background;
        public TextView description;
        public ImageView cardImage;
    }

    public class DogCardsAdapter extends BaseAdapter {
        public List<DogCard> dogCards;
        public Context context;

        private DogCardsAdapter(List<DogCard> dogCards, Context context) {
            this.dogCards = dogCards;
            this.context = context;
        }

        @Override
        public int getCount() {
            return dogCards.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                view = inflater.inflate(R.layout.dog_card, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.description = (TextView) view.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) view.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) view.findViewById(R.id.cardImage);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.description.setText(dogCards.get(position).getDescription());
            Glide.with(getActivity()).load(dogCards.get(position).getImagePath()).into(viewHolder.cardImage);

            return view;
        }
    }
}
