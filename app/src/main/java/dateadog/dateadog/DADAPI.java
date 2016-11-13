package dateadog.dateadog;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import com.facebook.login.LoginManager;
import static dateadog.dateadog.LoginActivity.getUserLoginToken;

/**
 * {@code DADAPI} interfaces with the Date-A-Dog server and the Petfinder API to retrieve and
 * update information about the user and dogs available to them.
 */
public class DADAPI {


    private static final String TAG = DADAPI.class.getName();

    /*
    private static DADAPI instance = null;
    public static DADAPI getInstance() {
        if (instance == null) {
            instance = new DADAPI();
        }
        return instance;
    }
    */

    private static String PETFINDER_API_KEY = "d025e514d458e4366c42ea3006fd31b3";
    private static String PETFINDER_URL_BASE = "http://api.petfinder.com/pet.find&format=json&animal=dog&output=full&count=100?key=" + PETFINDER_API_KEY;
    private static String DAD_SERVER_URL_BASE = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/";
    private static String FIND_DOGS_END_POINT = "getNextDogs";
    private static String JUDGE__DOG_ENDPOINT = "judgeDog";
    private static String GET_LIKED_DOGS_ENDPOINT = "getLikedDogs";
    private static String DAD_SERVER_URL_BASE_DEMO = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/getNextDogsDemo";

    private User user;
    private int zipCode;
    private int lastOffset;
    private URL petfinderURL;
    private Context context;

    public DADAPI(Context context) {
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void seenDog(Dog dog) {
        JSONObject obj = new JSONObject();
        // execute method and handle any error responses.
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

    private void addDogsFromJSONList(String data, List<Dog> result) {
        try {
            JSONArray dogs = (JSONArray) new JSONTokener(Constants.DATA).nextValue();
            for (int i = 0; i < dogs.length(); i++) {
                result.add(new Dog((JSONObject) dogs.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addDogsFromJSON(String data, Set<Dog> result) {
        try {
            JSONArray dogs = (JSONArray) new JSONTokener(Constants.DATA).nextValue();
            for (int i = 0; i < dogs.length(); i++) {
                result.add(new Dog((JSONObject) dogs.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Set<Dog> getNextDogs(int zipCode) {
        final Set<Dog> result = new LinkedHashSet<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        addDogsFromJSON("", result);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                                                               DAD_SERVER_URL_BASE + FIND_DOGS_END_POINT,
                                                               new JSONObject(),
                                                               new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("Response = ", "It did something with response" + response.toString());
                addDogsFromJSON(response.toString(), result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null) {
                    // Log.v("Error message", error.getMessage());
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

        return result;

        // return filterSeenDogs(result);
    }

    public void judgeDog(Dog dog, boolean like) {
        JSONObject dogLike = new JSONObject();
        try {
            dogLike.put("id", dog.getDogId());
            dogLike.put("liked", like);
            dogLike.put("epoch", System.currentTimeMillis());
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                                                                   DAD_SERVER_URL_BASE + JUDGE__DOG_ENDPOINT,
                                                                   dogLike,
                                                                   new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.v("Response = ", "It did something with response" + response.toString());
                    //addDogsFromJSON(response.toString(), result);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error == null) {
                        Log.v("Null error", "it died with a null error");
                    } else if (error.getMessage() != null) {
                        // Log.v("Error message", error.getMessage());
                    } else {
                        Log.v("All are null", "");
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("access_token",getUserLoginToken());
                    return params;
                }
            };
            Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //returns a list of liked dogs
    public List<Dog> getLikedDogs() {
        final List<Dog> likedDogs = new ArrayList<Dog>();
        RequestQueue queue = Volley.newRequestQueue(context);
        addDogsFromJSONList("", likedDogs);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                                                               DAD_SERVER_URL_BASE + GET_LIKED_DOGS_ENDPOINT,
                                                               new JSONObject(),
                                                               new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Response = ", "It did something with response" + response.toString());
                        addDogsFromJSONList(response.toString(), likedDogs);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null) {
                    // Log.v("Error message", error.getMessage());
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

    public void likeDog(Dog dog) {
        judgeDog(dog, true);

    }

    public void dislikeDog(Dog dog) {
        judgeDog(dog, false);
    }

    public Set<DateRequest> getRequests() {
        return null;
    }

    public void requestDate(Dog dog, Form form) {

    }

}
