package dateadog.dateadog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LikedDogsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LikedDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedDogsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final int MAX_DOGS_SHOWN = 100;
    private OnFragmentInteractionListener mListener;
    private List<Dog> likedDogs;
    private DADAPI DogManager;
    ListView likedDogsListView;
    LikedDogsListViewAdapter adapter;

    public LikedDogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment LikedDogsFragment.
     */
    public static LikedDogsFragment newInstance() {
        LikedDogsFragment fragment = new LikedDogsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DogManager = DADAPI.getInstance(getContext().getApplicationContext());
        likedDogs = new ArrayList<>();
        refreshLikedDogs();
    }

    private void refreshLikedDogs() {
        DogManager.getLikedDogs(new DADAPI.DataListener() {
            @Override
            public void onGotDogs(Set<Dog> dogs) {
                System.out.println("Got " + dogs.size() + " dogs back!!!");
                likedDogs.clear();
                likedDogs.addAll(dogs);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshLikedDogs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dog dog = (Dog) parent.getItemAtPosition(position);
        Intent showDogProfile = new Intent(getContext(), DogProfileActivity.class);
        showDogProfile.putExtra("Dog", dog);
        startActivity(showDogProfile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_liked_dogs, container, false);

        likedDogsListView = (ListView) rootView.findViewById(R.id.likedDogsListView);
        adapter = new LikedDogsListViewAdapter(getContext(), R.layout.row, likedDogs);
        likedDogsListView.setAdapter(adapter);
        likedDogsListView.setOnItemClickListener(this);
        refreshLikedDogs();
        return rootView;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}