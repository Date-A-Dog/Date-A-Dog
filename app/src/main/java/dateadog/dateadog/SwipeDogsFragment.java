package dateadog.dateadog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dateadog.dateadog.tindercard.SwipeFlingAdapterView;

/**
 * Use the {@link SwipeDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeDogsFragment extends Fragment {

    /**
     * The minimum number of dogs left in the stack before more dogs are retrieved from the server.
     */
    private static final int REFRESH_DOGS_THRESHOLD = 5;

    private List<DogCard> dogCards;
    private SwipeFlingAdapterView flingContainer;
    private DADServer server;

    public static ViewHolder viewHolder;

    public static class ViewHolder {
        public FrameLayout background;
        public TextView description;
        public ImageView cardImage;
    }

    public SwipeDogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return a new instance of fragment SwipeDogsFragment
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
        server = server.getInstance(getContext().getApplicationContext());
        dogCards = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_dogs, container, false);
        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.swipeFlingAdapterView);
        flingContainer.setAdapter(new BaseAdapter() {
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
                if (convertView == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    convertView = inflater.inflate(R.layout.dog_card, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.description = (TextView) convertView.findViewById(R.id.bookText);
                    viewHolder.background = (FrameLayout) convertView.findViewById(R.id.background);
                    viewHolder.cardImage = (ImageView) convertView.findViewById(R.id.cardImage);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.description.setText(dogCards.get(position).getDescription());

                Glide.with(getActivity()).load(dogCards.get(position).getImagePath()).into(viewHolder.cardImage);

                return convertView;
            }
        });
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() { }

            @Override
            public void onLeftCardExit(Object dataObject) {
                // This is a dislike dog swipe.
                DogCard dogCard = dogCards.get(0);
                server.dislikeDog(dogCard.getDogId());
                dogCards.remove(0);
                ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();
                if (dogCards.size() <= REFRESH_DOGS_THRESHOLD) {
                    updateUI();
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                // This is a like dog swipe.
                DogCard dogProfile = dogCards.get(0);
                server.likeDog(dogProfile.getDogId());
                dogCards.remove(0);
                ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    public void updateUI() {
        System.out.println("SwipeDogsFragment: updateUI()");
        server.getNextDogs(new DADServer.DogsDataListener() {
            @Override
            public void onGotDogs(List<Dog> dogs) {
                for (Dog dog : dogs) {
                    dogCards.add(new DogCard(dog));
                }
                ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();
            }
        });
    }

}
