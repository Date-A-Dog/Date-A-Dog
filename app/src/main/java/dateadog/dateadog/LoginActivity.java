package dateadog.dateadog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessTokenTracker;

import java.util.Set;

import com.facebook.Profile;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

public class LoginActivity extends AppCompatActivity {
    private DADAPI DogManager;
    private UserProfile FormManager;
    private LoginButton loginButton;
    private TextView mTextDetails; //this is for the test that says welcome fb user
    private CallbackManager callbackManager;
    private TextView info;
    private static boolean notFromMain = true;
    private static String fbLoginToken;
    private static String APIAuthenticationToken;
    private AccessTokenTracker accessTokenTracker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        DogManager = DADAPI.getInstance(LoginActivity.this);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        info = (TextView)findViewById(R.id.info);
        mTextDetails = (TextView)findViewById(R.id.text_details);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        /**
         * Registers a callback from the FB login button
         */
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            /** handles a successful login to facebook
             * opens the main activity page.
             * Also, sets the login and access tokens
             *
             * @param loginResult
             */
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbLoginToken = AccessToken.getCurrentAccessToken().getToken();
                loginButton.setVisibility(View.GONE);
                Profile profile = Profile.getCurrentProfile();
                mTextDetails.setText("Welcome!");
                Intent next = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(next);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
            /**
             * Handles a canceled facebook login
             *
             */
            @Override
            public void onCancel() { }

            /**
             * Handles a login with an error
             * Usually this is caused by lack of internet connectivity.
             * @param e Facebook exception
             */
            @Override
            public void onError(FacebookException e) {
                //info.setText("Error: Please check internet connection");
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Warning");
                alertDialog.setMessage("Error: Please check internet connection");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        // Allows the app to bypass the FB login if the user is already logged in
        if (facebookIsLoggedIn()) {
            fbLoginToken = AccessToken.getCurrentAccessToken().getToken();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }


    /** Handles the facebook login button authentication
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /** returns if the user is logged into facebook or not.
     * @return true if facebook is logged in
     */
    public boolean facebookIsLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * Sets if this method is being accessed from the main screen
     */
    public static void setFromMain() { notFromMain = false; }

    /** Gets the name of the currently logged in user of facebook
     *
     * @return the name of the user
     */
    public static String getUserName() {
        return Profile.getCurrentProfile().getName();
    }

    /** gets the ID of the currently logged in FB user
     * Note, the FB ID is the public name of the user.
     * @return the ID of the currently logged in FB user
     */
    public static String getUserID() {
        return Profile.getCurrentProfile().getId();
    }

    /** Gets the single use login token of the FB user
     *
     * @return the token of the FB user
     */
    public static String getUserLoginToken() { return fbLoginToken; }

    /**
     * @return API Authentication key
     */
    public static String getUserAPIAuthenticationToken() { return APIAuthenticationToken; }

}
