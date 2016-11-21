package dateadog.dateadog;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private LinearLayout layout;
    private DADAPI DogManager;

    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DogManager = DADAPI.getInstance(getContext().getApplicationContext());
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFormData();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_form, container, false);
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.formLayout);
        TextView label1 = new TextView(getActivity());
        EditText text1 = new EditText(getActivity());
        label1.setTextSize(25);
        text1.setTextSize(25);
        label1.setText("First Name: ");
        text1.setText("Sally");


        layout.addView(label1);
        layout.addView(text1);

        TextView label2 = new TextView(getActivity());
        EditText text2 = new EditText(getActivity());
        label2.setTextSize(25);
        text2.setTextSize(25);
        label2.setText("Last Name: ");
        text2.setText("Smith");

        layout.addView(label2);
        layout.addView(text2);

        TextView label3 = new TextView(getActivity());
        EditText text3 = new EditText(getActivity());
        label3.setTextSize(25);
        text3.setTextSize(25);
        label3.setText("Email: ");
        text3.setText("sally1@hotmail.com");


        layout.addView(label3);
        layout.addView(text3);

        TextView label4 = new TextView(getActivity());
        EditText text4 = new EditText(getActivity());
        label4.setTextSize(25);
        text4.setTextSize(25);
        label4.setText("Phone number ");
        text4.setText("206-000-1111");

        layout.addView(label4);
        layout.addView(text4);

        TextView label5 = new TextView(getActivity());
        EditText text5 = new EditText(getActivity());
        label5.setTextSize(25);
        text5.setTextSize(25);
        label5.setText("Why do you want to date a dog? ");
        text5.setText("I am looking for my doggie");

        layout.addView(label5);
        layout.addView(text5);
        System.out.println("hi");
        Button button = new Button(getActivity());
        button.setText("Submit");
        button.setGravity(Gravity.CENTER);
        layout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //need to call DogManager.updateForm(form);
            }
        });
        return rootView;
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

    //gets the form data from the Rest API
    private void getFormData() {
        DogManager.login(new DADAPI.DataListener() {
            @Override
            public void onGotDogs(Set<Dog> dogs) {

            }

            @Override
            public void onGotForm(Form formData) {
                //do something with the form data
            }
        });
    }
}
