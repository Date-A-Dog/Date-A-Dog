package dateadog.dateadog;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class DogProfileActivity extends AppCompatActivity {

    /**
     * The dog that this profile displays information for. Passed via an intent when starting
     * this activity.
     * */
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dog = (Dog) getIntent().getExtras().get("Dog");
        refreshUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshUI();
    }

    private void refreshUI() {
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

}
