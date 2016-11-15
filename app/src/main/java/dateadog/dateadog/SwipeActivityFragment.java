package dateadog.dateadog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import dateadog.dateadog.tindercard.FlingCardListener;
import dateadog.dateadog.tindercard.SwipeFlingAdapterView;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwipeActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwipeActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeActivityFragment extends Fragment implements FlingCardListener.ActionDownInterface{
    private static final String DOGS_TO_SPAWN = "40"; //how many dogs to spawn every time we run out of doggies
    public static MyAppAdapter myAppAdapter; //holds the app adapter
    private TextView noDogs; //displays when there are no dogs left
    public static ViewHolder viewHolder;
    private ArrayList<Data_TinderUI> al; //the gui form of all dogs
    private static List<Dog> pendingDogs; //the actual dog objects of all the pending dogs ot be swiped
    private SwipeFlingAdapterView flingContainer;
    private OnFragmentInteractionListener mListener;
    private DADAPI DogManager;


    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    //adds the Dog object values for the view to the view list
    //takes in Dog list and UI list as parameters to transfer necessary attributes
    private static void addDogsToAL(List<Dog> dogs, ArrayList<Data_TinderUI> al) {
        for (Dog dog : dogs) {
            String Breeds = dog.getStringBreeds();
            String SizeDog = dog.getSizeStringDog();
            String profileInfo = "Name: " + dog.getName() + "\nAge: " +
                                 dog.getAge() + "\nSex: " + dog.getSex() +
                                 "\nBreeds: " + Breeds + "\nSize of Dog: " + SizeDog +
                                 "\nDog Location: " + dog.getCity();
            al.add(new Data_TinderUI(dog.getImage(), dog.getDogId(), profileInfo));
        }
    }
    private void getDoggies() {
        DogManager.getNextDogs("1000", "98105", new VolleyResponseListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    JSONArray doggies = (JSONArray) result; //cast the response to a json array
                    for (int i = 0; i < ((JSONArray) result).length(); i++) {
                        pendingDogs.add(new Dog((JSONObject) doggies.get(i)));
                    }
                    addDogsToAL(pendingDogs, al);
                    myAppAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Error on succ", e.getMessage(), e);
                }
            }
        });
    }
    public SwipeActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SwipeActivityFragment.
     */
    public static SwipeActivityFragment newInstance() {
        SwipeActivityFragment fragment = new SwipeActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DogManager = new DADAPI(getActivity());
        al = new ArrayList<Data_TinderUI>();
        pendingDogs = new ArrayList<Dog>();
        getDoggies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_swipe_activity, container, false);
        noDogs = (TextView)v.findViewById(R.id.textnodogs);
        flingContainer = (SwipeFlingAdapterView)v.findViewById(R.id.framefrag);
        myAppAdapter = new MyAppAdapter(al, getActivity());
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            //this is the reject swipe
            public void onLeftCardExit(Object dataObject) {
                Data_TinderUI dogProfile = al.get(0);
                DogManager.judgeDog(dogProfile.getDogId(), false);
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                if (al.size() == 0) {
                    getDoggies();
                    //noDogs.setText("No More Dogs.\nRefresh Page Soon!");
                    //noDogs.setTextColor(Color.BLUE);
                }
            }

            @Override
            //this is the like a dog swipe
            public void onRightCardExit(Object dataObject) {
                Data_TinderUI dogProfile = al.get(0);
                DogManager.judgeDog(dogProfile.getDogId(), true);
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                if (al.size() == 0) {
                    getDoggies();
                    //noDogs.setText("No More Dogs.\nRefresh Page Soon!");
                    //noDogs.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;

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

    public class MyAppAdapter extends BaseAdapter {


        public List<Data_TinderUI> parkingList;
        public Context context;

        private MyAppAdapter(List<Data_TinderUI> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
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

            View rowView = convertView;


            if (rowView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.DataText.setTextColor(Color.BLACK);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.background.setBackgroundColor(Color.BLUE);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                viewHolder.cardImage.setBackgroundColor(Color.BLUE);
                //viewHolder.cardImage.setMinimumHeight(50);
                //viewHolder.cardImage.setMaxHeight(50);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(getActivity()).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}






