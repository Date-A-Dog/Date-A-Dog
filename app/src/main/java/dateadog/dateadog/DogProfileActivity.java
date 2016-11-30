package dateadog.dateadog;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.Calendar;
import java.util.Date;

public class DogProfileActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListener, TimePickerFragment.TimeDialogListener, UserProfileDialogFragment.OnFragmentInteractionListener {

    /**
     * The dog that this profile displays information for. Passed via an intent when starting
     * this activity.
     * */
    private Dog dog;
    private DADAPI dadapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dadapi = DADAPI.getInstance(getApplicationContext());

        setContentView(R.layout.activity_dog_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button requestDateButton = (Button) findViewById(R.id.requestDateButton);
        requestDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dadapi.getUser(new DADAPI.UserProfileDataListener() {
                    @Override
                    public void onGotUserProfile(final UserProfile userProfile) {
                        if (userProfile.isComplete()) {
                            DatePickerFragment dateDialog = new DatePickerFragment();
                            dateDialog.show(getSupportFragmentManager(), "DateDialog");
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Complete your profile first", Snackbar.LENGTH_LONG)
                                    .setAction("Edit Profile", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            UserProfileDialogFragment dialog = UserProfileDialogFragment.newInstance(userProfile);
                                            dialog.show(getSupportFragmentManager(), "dialog");
                                        }
                                    })
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                });
            }
        });

        dog = (Dog) getIntent().getExtras().get("Dog");
        updateUI();
    }

    @Override
    public void onFinishDialog(int hour, int minute) {
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        dadapi.requestDate(dog.getDogId(), calendar.getTimeInMillis());
    }

    Calendar calendar = Calendar.getInstance();

    @Override
    public void onFinishDialog(Date date) {
        calendar.setTime(date);
        TimePickerFragment timeDialog = new TimePickerFragment();
        timeDialog.show(getSupportFragmentManager(), "TimeDialog");
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        VolleySingleton.getInstance(getApplicationContext()).getImageLoader()
                       .get(dog.getImage(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        ImageView profileImage = (ImageView) findViewById(R.id.profile_image_view);
                        profileImage.setImageBitmap(response.getBitmap());
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        setTitle(dog.getName());
        ((TextView) findViewById(R.id.ageTextView)).setText(dog.getAge());
        ((TextView) findViewById(R.id.sexTextView)).setText(dog.getSex());
        ((TextView) findViewById(R.id.breedsTextView)).setText(dog.getBreedsString());
        ((TextView) findViewById(R.id.sizeTextView)).setText(dog.getSize());
        ((TextView) findViewById(R.id.locationTextView)).setText(dog.getCity());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
