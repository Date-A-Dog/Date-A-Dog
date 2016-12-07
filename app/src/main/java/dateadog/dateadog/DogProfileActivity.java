package dateadog.dateadog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class DogProfileActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListener, TimePickerFragment.TimeDialogListener {
    int MAX_REQUESTS = 1; //can't have more than 1 request
    /**
     * The dog that this profile displays information for. Passed via an intent when starting
     * this activity.
     * */
    private Dog dog;
    private DADServer server;
    private Button requestDateButton;
    private TextView feedbackTitle;
    private TextView feedback;

    //count the number of pending doggie date requests
    private int countPendingRequests(Set<DateRequest> dates) {
        int count = 0;
        for (DateRequest date : dates) {
            if (date.getStatus() == DateRequest.Status.PENDING) {
                count++;
            }
        }
        return count;
    }
    private static final boolean DEVELOPER_MODE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = server.getInstance(getApplicationContext());

        setContentView(R.layout.activity_dog_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        requestDateButton = (Button) findViewById(R.id.requestDateButton);
        RelativeLayout locationRelativeLayout = (RelativeLayout) findViewById(R.id.locationRelativeLayout);
        locationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + URLEncoder.encode(dog.getCity())));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        feedbackTitle = (TextView) findViewById(R.id.title_feedback);
        feedback = (TextView) findViewById(R.id.feedback);
        requestDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                server.getUser(new DADServer.UserProfileDataListener() {
                    @Override
                    public void onGotUserProfile(final UserProfile userProfile) {
                        if (userProfile.isComplete()) {
                            server.getDateRequests(new DADServer.DateRequestsDataListener() {
                                @Override
                                public void onGotDateRequests(Set<DateRequest> dateRequests) {
                                    int pendingRequests = countPendingRequests(dateRequests);
                                    if (pendingRequests > MAX_REQUESTS) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(DogProfileActivity.this).create();
                                        alertDialog.setTitle(R.string.no_more_dates);
                                        alertDialog.setMessage(getString(R.string.no_more_dates));
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();
                                    } else {
                                        DatePickerFragment dateDialog = new DatePickerFragment();
                                        dateDialog.show(getSupportFragmentManager(), "DateDialog");
                                    }
                                }
                            });

                        } else {
                            Snackbar.make(findViewById(android.R.id.content), R.string.complete_profile_message, Snackbar.LENGTH_LONG)
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
    public void onFinishDialog(int hour, int minute, String description) {
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        server.requestDate(dog.getDogId(), calendar.getTimeInMillis(), description);
        findViewById(R.id.requestDateButton).setEnabled(false);
        ((TextView) findViewById(R.id.requestDateButton)).setText(R.string.request_sent);
    }

    Calendar calendar = Calendar.getInstance();

    @Override
    public void onFinishDialog(Date date) {
        calendar.setTime(date);
        Date today = Calendar.getInstance().getTime();
        if (date.before(today) && !DEVELOPER_MODE) {
            // The user is attempting to set a date for today or earlier.
            AlertDialog alertDialog = new AlertDialog.Builder(DogProfileActivity.this).create();
            alertDialog.setTitle(R.string.past_date_error_title);
            alertDialog.setMessage(getString(R.string.past_date_error_message));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            TimePickerFragment timeDialog = new TimePickerFragment();
            timeDialog.show(getSupportFragmentManager(), "TimeDialog");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        VolleySingleton.getInstance(getApplicationContext()).getImageLoader()
                       .get(dog.getImageURL(), new ImageLoader.ImageListener() {
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
        // Get and display the request status for this dog.
        requestDateButton.setEnabled(false);
        server.getDateRequests(new DADServer.DateRequestsDataListener() {
            @Override
            public void onGotDateRequests(Set<DateRequest> dateRequests) {
                boolean existingDateRequest = false;
                for (DateRequest request : dateRequests) {
                    if (request.getDogId() == dog.getDogId()) {
                        existingDateRequest = true;
                        DateRequest.Status status = request.getStatus();
                        CharSequence dateString = DateUtils.getRelativeDateTimeString(DogProfileActivity.this, request.getDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
                        if (status == DateRequest.Status.APPROVED) {
                            requestDateButton.setText(getString(R.string.request_approved)
                                                      + " for " + dateString);
                        } else if (status == DateRequest.Status.REJECTED) {
                            requestDateButton.setText(getString(R.string.request_rejected));
                            feedbackTitle.setText(R.string.feedback_title);
                            feedback.setText(request.getFeedback());
                        } else if (status == DateRequest.Status.PENDING) {
                            requestDateButton.setText(getString(R.string.request_pending)
                                                      + " for " + dateString);
                        }
                    }
                }
                if (!existingDateRequest) {
                    requestDateButton.setEnabled(true);
                }
            }
        });
    }

}
