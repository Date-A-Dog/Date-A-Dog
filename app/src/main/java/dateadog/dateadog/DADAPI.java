package dateadog.dateadog;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import static dateadog.dateadog.LoginActivity.getUserLoginToken;

/**
 * {@code DADAPI} interfaces with the Date-A-Dog server and the Petfinder API to retrieve and
 * update information about the user and dogs available to them.
 */
public class DADAPI {
    private static String PETFINDER_API_KEY = "d025e514d458e4366c42ea3006fd31b3";
    private static String PETFINDER_URL_BASE = "http://api.petfinder.com/pet.find&format=json&animal=dog&output=full&count=100?key=" + PETFINDER_API_KEY;
    private static String DAD_SERVER_URL_BASE = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/";
    private static String FIND_DOGS_END_POINT = "getNextDogs";
    private static String JUDGE_DOG_ENDPOINT = "judgeDog";
    private static String GET_LIKED_DOGS_ENDPOINT = "getLikedDogs";
    private static String DAD_SERVER_URL_BASE_DEMO = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/getNextDogsDemo";

    private static final String TAG = DADAPI.class.getName();
    private User user;
    private int zipCode;
    private int lastOffset;
    private URL petfinderURL;
    private Context context;

    /*This is the constructor fo the DADAPI class*/
    public DADAPI(Context context) {
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Set<Dog> filterSeenDogs(Set<Dog> result) {
        // backend.getSeenDogs(I) | filter result
        return null;
    }

    public void setLocation(int zipCode) {
        this.zipCode = zipCode;
        this.lastOffset = 0;

        try {
            petfinderURL = new URL(PETFINDER_URL_BASE + "&location=" + zipCode + "&offset=" + lastOffset);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void getNextDogs(String count, String zip, final VolleyResponseListener listener) {
        JSONObject login = new JSONObject();
        try {
            login.put("count", count);
            login.put("zip", zip);
        } catch (JSONException e) {
            Log.v("Error in JSON", "For zip and count");
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectToArrayRequest jsObjRequest = new JsonObjectToArrayRequest(Request.Method.POST,
                                                               DAD_SERVER_URL_BASE + FIND_DOGS_END_POINT,
                                                               login,
                                                               new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v("Response = ", "It did something with response");
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null) {
                    Log.v("Error in getNextDogs", error.getMessage());
                } else {
                    Log.v("All are null", "");
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type","application/json");
                params.put("Content-Type","application/json");
                String pass = AccessToken.getCurrentAccessToken().getToken();
                params.put("access_token", pass);
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);


        // return filterSeenDogs(result);
    }

    public void judgeDog(long dogId, boolean like) {
        JSONObject dogLike = new JSONObject();
        try {
            dogLike.put("id", dogId);
            dogLike.put("liked", like);
            dogLike.put("epoch", System.currentTimeMillis());
            JsonObjectToStringRequest jsObjRequest = new JsonObjectToStringRequest(Request.Method.POST,
                                                                   DAD_SERVER_URL_BASE + JUDGE_DOG_ENDPOINT,
                                                                   dogLike,
                                                                   new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //we don't do anything with the OK response
                    Log.v("Response = ", "It did something with judge dog response" + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error == null) {
                        Log.v("Null error", "it died with a null error");
                    } else if (error.getMessage() != null) {
                        Log.v("Error message JudgeDog", error.getMessage());
                    } else {
                        Log.v("All are null", "");
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("access_token",getUserLoginToken());
                    return params;
                }
            };
            Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            Log.v("Error in judge dog", "");
            e.printStackTrace();
        }

    }

    //returns a list of liked dogs
    private List<Dog> getLikedDogs() {
        final List<Dog> likedDogs = new ArrayList<Dog>();
        RequestQueue queue = Volley.newRequestQueue(context);
        //addDogsFromJSONList("", likedDogs);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                                                               DAD_SERVER_URL_BASE + GET_LIKED_DOGS_ENDPOINT,
                                                               new JSONObject(),
                                                               new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Response = ", "It did something with response" + response.toString());
                        //addDogsFromJSONList(response.toString(), likedDogs);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null) {
                    // Log.v("Error message Liked Dogs", error.getMessage());
                } else {
                    Log.v("All are null", "");
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("access_token",getUserLoginToken());
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
        return likedDogs;
    }

    private void createDogsListFromJSON(String json) {

    }

    public void likeDog(long dogId) {
        judgeDog(dogId, true);

    }

    public void dislikeDog(long dogId) {
        judgeDog(dogId, false);
    }

    public Set<DateRequest> getRequests() {
        return null;
    }

    public void requestDate(Dog dog, Form form) {

    }

    public interface VolleyCallback{
        void onSuccess(JSONArray response);
        //void OnSuccess(JSONObject string);
    }

}

