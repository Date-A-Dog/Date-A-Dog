package dateadog.dateadog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView welcomeText;
    private CallbackManager callbackManager;
    private DADAPI dadapi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        callbackManager = CallbackManager.Factory.create();
        dadapi = DADAPI.getInstance(getApplicationContext());

        // Registers a callback from the FB login button.
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            /**
             * Handles a successful login to Facebook. Starts MainActivity.
             *
             * @param loginResult
             */
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.GONE);
                welcomeText.setVisibility(View.VISIBLE);
                dadapi.getUser(new DADAPI.UserProfileDataListener() {
                    @Override
                    public void onGotUserProfile(UserProfile userProfile) {
                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(main);
                        finish();
                    }
                });
            }

            @Override
            public void onCancel() { }

            /**
             * Handles a login with an error. Usually this is caused by lack of internet
             * connectivity.
             * @param e Facebook exception
             */
            @Override
            public void onError(FacebookException e) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Please check your internet connection");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("LoginActivity: onStart()");
        // Bypass LoginActivity if the user is already logged in:
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
