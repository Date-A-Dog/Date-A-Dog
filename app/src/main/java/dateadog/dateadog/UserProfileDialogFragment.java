package dateadog.dateadog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Use the {@link UserProfileDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileDialogFragment extends DialogFragment {

    private DADServer server;
    private static final String USER_PROFILE_ARG = "UserProfile";

    TextInputEditText firstNameText;
    TextInputEditText lastNameText;
    TextInputEditText emailText;
    TextInputEditText streetText;
    TextInputEditText cityText;
    TextInputEditText stateText;
    TextInputEditText zipText;
    TextInputEditText phoneText;

    public UserProfileDialogFragment() {

    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @param profile the profile to initially populate this fragment with
     * @return a new instance of fragment UserProfileDialogFragment.
     */
    public static UserProfileDialogFragment newInstance(UserProfile profile) {
        UserProfileDialogFragment fragment = new UserProfileDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_PROFILE_ARG, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = server.getInstance(getContext().getApplicationContext());
    }

    /**
     * Updates the fields of the {@code UserProfile} class using the text input into this fragment.
     * Then sends the updated profile to the DAD server.
     */
    private void updateUserProfile() {
        UserProfile profile = new UserProfile();
        profile.setFirstName(firstNameText.getText().toString());
        profile.setLastName(lastNameText.getText().toString());
        profile.setEmail(emailText.getText().toString());
        profile.setStreet(streetText.getText().toString());
        profile.setCity(cityText.getText().toString());
        profile.setState(stateText.getText().toString());
        profile.setZip(zipText.getText().toString());
        profile.setPhone(phoneText.getText().toString());
        server.updateUser(profile);
    }

    public View onCreateDialogView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile_dialog, container, false);

        // Retrieve the text fields from the view.
        firstNameText = (TextInputEditText) rootView.findViewById(R.id.firstNameText);
        lastNameText = (TextInputEditText) rootView.findViewById(R.id.lastNameText);
        emailText = (TextInputEditText) rootView.findViewById(R.id.emailText);
        streetText = (TextInputEditText) rootView.findViewById(R.id.streetText);
        cityText = (TextInputEditText) rootView.findViewById(R.id.cityText);
        stateText = (TextInputEditText) rootView.findViewById(R.id.stateText);
        zipText = (TextInputEditText) rootView.findViewById(R.id.zipText);
        phoneText = (TextInputEditText) rootView.findViewById(R.id.phoneText);


        // Populate the text fields with the user's profile.
        UserProfile profile = (UserProfile) getArguments().getSerializable(USER_PROFILE_ARG);
        firstNameText.setText(profile.getFirstName());
        lastNameText.setText(profile.getLastName());
        emailText.setText(profile.getEmail());
        streetText.setText(profile.getStreet());
        cityText.setText(profile.getCity());
        stateText.setText(profile.getState());
        zipText.setText(profile.getZip());
        phoneText.setText(profile.getPhone());

        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_profile_dialog)
                .setPositiveButton(R.string.save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateUserProfile();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        View view = onCreateDialogView(getActivity().getLayoutInflater(), null, null);
        onViewCreated(view, null);
        dialogBuilder.setView(view);

        return dialogBuilder.create();
    }
}
