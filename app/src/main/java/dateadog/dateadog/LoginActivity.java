package dateadog.dateadog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info; //text view for errors
    private static boolean notFromMain = true; //to tell if we are in main or not
    private static String fbLoginToken; //a string for the login token
    private static String APIAuthenticationToken; //string for the auth token


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("I got into the success loop!");
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        // Don't do anything.
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        info.setText("Error: Please check internet connection");
                    }
        });
        // Allows the app to bypass the FB login if the user is already logged in
        if (facebookIsLoggedIn() && notFromMain) {
            fbLoginToken = AccessToken.getCurrentAccessToken().getToken();
            authenticateAPI();
            Intent intent = new Intent(LoginActivity.this, DogSwipeActivity.class);
            startActivity(intent);
        }

        // sets that the next time the user opens the app that it should skip this page
        notFromMain = true;
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
    public static void setFromMain() {
        notFromMain = false;
    }

    /** Gets the single use login token of the FB user
     *
     * @return the token of the FB user
     */
    public static String getUserLoginToken() {
        return fbLoginToken;
    }

    /**
     * @return API Authentication key
     */
    public static String getUserAPIAuthenticationToken() {
        return APIAuthenticationToken;
    }

    /**
     * Authenticates the app with the API using the POST method and a custom HTML header
     * Also, uses the volley library
     */
    private void authenticateAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);

        //TO DO: CHANGE THIS TO A URL FOR PAUL WANTS
        String url = "http://54.68.38.196/authenticate";

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("Reponse = ", "It did something with response" + response);
                APIAuthenticationToken = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null){
                    Log.v("Error message", error.getMessage());
                } else {
                    Log.v("All are null", "");
                }
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("access_token",getUserLoginToken());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }


}
