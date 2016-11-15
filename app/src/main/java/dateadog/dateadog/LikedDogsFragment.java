package dateadog.dateadog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static dateadog.dateadog.R.attr.height;
import static dateadog.dateadog.R.id.top;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LikedDogsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LikedDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedDogsFragment extends Fragment {
    private static final int MAX_DOGS_SHOWN = 100;
    private OnFragmentInteractionListener mListener;
    private List<Dog> likedDogs;
    private DADAPI DogManager;
    private LinearLayout layout;
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

    public void setLayoutScreen(List<Dog> likedDoggies, LinearLayout layout) {
        int dogsToShow = Math.min(likedDoggies.size(), MAX_DOGS_SHOWN);
        for (int i = 0; i < dogsToShow; i++) {
            Dog currentDog = likedDoggies.get(i);
            ImageView dogView = new ImageView(getActivity());
            TextView dogText = new TextView(getActivity());
            Drawable imageDog = LoadImageFromWebOperations(currentDog.getImage());
            dogView.setImageDrawable(imageDog);
            dogText.setText(currentDog.getName());
            dogText.setGravity(Gravity.CENTER);
            layout.addView(dogView);
            layout.addView(dogText);
        }
    }

    private static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private void getLikedDoggies(final LinearLayout layout) {
        Set<Dog> dogs = DogManager.getLikedDogs();
        likedDogs.addAll(dogs);
        setLayoutScreen(likedDogs, layout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DogManager = DADAPI.getInstance();
        likedDogs = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_liked_dogs, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.dogLikeLayout);
        getLikedDoggies(layout);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLikedDoggies(layout);
                // this will be called whenever user click anywhere in Fragment
            }
        });
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