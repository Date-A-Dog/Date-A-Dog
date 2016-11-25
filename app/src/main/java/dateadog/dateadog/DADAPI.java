/****************************************
 * Copyright (c) 2016 Date-A-Dog.       *
 * All rights reserved.                 *
 ***************************************/

package dateadog.dateadog;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * DADAPI communicates with the DAD server to retrieve and update data about dog profiles and users.
 */
public class DADAPI {

    /** Holds the sole instance of this class. */
    private static DADAPI instance;
    /** Used to identify this class in logging messages. */
    private static String TAG = DADAPI.class.getName();
    /** URLs for server endpoints. */
    private static String DAD_SERVER_URL_BASE = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/";
    private static String GET_NEXT_DOGS_URL = DAD_SERVER_URL_BASE + "getNextDogs";
    private static String GET_LIKED_DOGS_URL = DAD_SERVER_URL_BASE + "getLikedDogs";
    private static String JUDGE_DOG_URL = DAD_SERVER_URL_BASE + "judgeDog";
    private static String REQUEST_DATE_URL = DAD_SERVER_URL_BASE + "requestDate";
    private static String LOGIN_URL = DAD_SERVER_URL_BASE + "login";
    private static String UPDATE_USER_URL = DAD_SERVER_URL_BASE + "updateUser";
    /** Number of dogs to request each time a request is made. */
    private static int NUM_DOGS_REQUESTED = 50;
    private static String DEFAULT_ZIP = "98105";

    private Context context;

    /**
     * Constructs an instance of the DADAPI class with the given context.
     *
     * @param context the application context in which this class will be used
     */
    public DADAPI(Context context) {
        this.context = context;
    }

    /**
     * Returns an instance of the {@code DADAPI} class.
     *
     * @param context the application context in which this class will be used
     * @return an instance of the {@code DADAPI} class
     */
    public static DADAPI getInstance(Context context) {
        if (instance == null) {
            instance = new DADAPI(context);
        }
        return instance;
    }

    /**
     * Makes a POST request (with authentication) to the given URL with the given {@code jsonBody}
     * as the body of the POST request. Returns the JSON response via the given
     * {@code responseListener}.
     *
     * @param url the URL to make a POST request to
     * @param jsonBody the JSON body of the POST request
     * @param responseListener a response listener that will receive a callback with the response
     *                         data, or null to ignore response data
     */
    public void makeRequest(final String url, final JSONObject jsonBody, Response.Listener<String> responseListener) {
        System.out.println(jsonBody.toString());
        if (responseListener == null) {
            // The caller would like not to receive response data. This can be done by setting an
            // empty response listener.
            responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {}
            };
        }

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, url, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e(TAG, "Request to DAD server failed.\nURL: " + url + "\nBody: " + jsonBody.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("access_token", AccessToken.getCurrentAccessToken().getToken());
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }
        };
        queue.add(request);
    }

    /**
     * Clients implement this interface to receive dogs from DADAPI requests.
     */
    public interface DogsDataListener {
        /**
         * Called when the requested dogs have been retrieved.
         *
         * @param dogs the requested dogs
         */
        public void onGotDogs(Set<Dog> dogs);
    }

    /**
     * Clients implement this interface to receive user profile data from DADAPI requests.
     */
    public interface UserProfileDataListener {
        /**
         * Called when the requested user profile has been retrieved.
         *
         * @param userProfile the requested user profile
         */
        public void onGotUserProfile(UserProfile userProfile);
    }

    /**
     * Makes a DAD server request at the given URL, parses the response as a JSON
     * array of dogs and returns a set containing these dogs via the given callback listener.
     *
     * @param url a DAD endpoint that returns a JSON array of dogs
     * @param dataListener a data listener that will receive a callback with the dogs
     */
    private void getDogsAtUrl(String url, final DogsDataListener dataListener) {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("count", NUM_DOGS_REQUESTED);
            parameters.put("zip", DEFAULT_ZIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequest(url, parameters, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Set<Dog> result = new HashSet<>();
                    JSONArray dogsArray = (JSONArray) new JSONTokener(response).nextValue();
                    for (int i = 0; i < dogsArray.length(); i++) {
                        result.add(new Dog(dogsArray.getJSONObject(i)));
                    }
                    dataListener.onGotDogs(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Retrieves the user's profile from the DAD server.
     *
     * @param dataListener a data listener that will receive a callback with the user profile
     */
    public void getUser(final UserProfileDataListener dataListener) {
        JSONObject parameters = new JSONObject();
        makeRequest(LOGIN_URL, parameters, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonForm = (JSONObject) new JSONTokener(response).nextValue();
                    UserProfile userProfile = new UserProfile(jsonForm);
                    dataListener.onGotUserProfile(userProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Updates the user's profile information using the given {@code UserProfile}.
     *
     * @param userProfile the userProfile containing the user's information
     */
    public void updateUser(UserProfile userProfile) {
        makeRequest(UPDATE_USER_URL, userProfile.asJSONObject(), null);
    }


    /**
     * Retrieves a set of dogs that the user has not yet judged.
     *
     * @param dataListener a data listener that will receive a callback with the dogs
     */
    public void getNextDogs(DogsDataListener dataListener) {
        getDogsAtUrl(GET_NEXT_DOGS_URL, dataListener);
    }

    /**
     * Retrieves a set of dogs that the user has liked.
     *
     * @param dataListener a data listener that will receive a callback with the dogs
     */
    public void getLikedDogs(DogsDataListener dataListener) {
        getDogsAtUrl(GET_LIKED_DOGS_URL, dataListener);
    }

    /**
     * Sends the DAD server a request to schedule a date with the dog that has the
     * given {@code dogId} at the time given by {@code epoch}.
     *
     * @param dogId the id of the dog to schedule the date with
     * @param epoch the time at which to schedule the date, expressed as milliseconds after epoch
     */
    public void requestDate(long dogId, long epoch) {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("id", dogId);
            parameters.put("epoch", epoch);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        makeRequest(REQUEST_DATE_URL, parameters, null);
    }

    /**
     * Marks the dog with the given {@code dogId} as liked or disliked.
     *
     * @param dogId the id of the dog to judge
     * @param like true if the dog should be marked as liked, false otherwise
     */
    public void judgeDog(long dogId, boolean like) {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("id", dogId);
            parameters.put("liked", like);
            parameters.put("epoch", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        makeRequest(JUDGE_DOG_URL, parameters, null);
    }

    /**
     * Marks the dog with the given {@code dogId} as liked.
     *
     * @param dogId the id of the dog to like
     */
    public void likeDog(long dogId) {
        judgeDog(dogId, true);
    }

    /**
     * Marks the dog with the given {@code dogId} as disliked.
     *
     * @param dogId the id of the dog to dislike
     */
    public void dislikeDog(long dogId) {
        judgeDog(dogId, false);
    }

}
