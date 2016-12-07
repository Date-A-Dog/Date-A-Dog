package dateadog.dateadog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Use the {@link LikedDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedDogsFragment extends Fragment {

    private static final int NUM_COLUMNS = 2;

    private DADServer server;
    private List<Dog> likedDogs;
    private LikedDogsRecyclerViewAdapter adapter;

    public LikedDogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return a new instance of fragment LikedDogsFragment
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
        server = server.getInstance(getContext().getApplicationContext());
        likedDogs = new ArrayList<>();
    }

    public void updateUI() {
        System.out.println("LikedDogsFragment: updateUI()");
        server.getLikedDogs(new DADServer.DogsDataListener() {
            @Override
            public void onGotDogs(List<Dog> dogs) {
                likedDogs.clear();
                likedDogs.addAll(dogs);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateUI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_liked_dogs, container, false);

        RecyclerView likedDogsRecyclerView = (RecyclerView) rootView.findViewById(R.id.likedDogsRecyclerView);
        likedDogsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        likedDogsRecyclerView.setNestedScrollingEnabled(false);
        adapter = new LikedDogsRecyclerViewAdapter(getActivity(), likedDogs);
        likedDogsRecyclerView.setAdapter(adapter);
        return rootView;
    }
}