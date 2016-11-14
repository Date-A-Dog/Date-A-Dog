package dateadog.dateadog;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessTokenTracker;
import java.util.HashMap;
import java.util.Map;
import com.facebook.Profile;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
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
            //authenticateAPI();
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

    /**
     * Authenticates the app with the API using the POST method and a custom HTML header
     * Also, uses the volley library.
     */
    private void authenticateAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject login = new JSONObject();
        try {
            login.put("count", "20");
            login.put("zip", "98105");
        } catch (JSONException e) {
            Log.v("Error in add zip", "");
        }

        //TO DO: CHANGE THIS TO A URL FOR PAUL WANTS
        String host = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com";
        String url = host + "/api/getNextDogs";

        JsonObjectToArrayRequest jsObjRequest = new JsonObjectToArrayRequest(Request.Method.POST, url, login, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v("Entering onResponse", "");
                System.err.println("Error I been here");
                //Log.v("Reponse = ", "It did something with response" + response.toString());
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    ;
                } else if (error.getMessage() != null){
                    ;
                } else {
                    ;
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("access_token",getUserLoginToken());
                return params;
            }
        };
        Singleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }


}
