/****************************************
 * Copyright (c) 2016 Date-A-Dog.       *
 * All rights reserved.                 *
 ***************************************/

package dateadog.dateadog;

import com.facebook.AccessToken;

import org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class DADAPI {

    /** Holds the sole instance of this class. */
    private static DADAPI instance;
    /** URLs for server endpoints. */
    private static String DAD_SERVER_URL_BASE = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/";
    private static String GET_NEXT_DOGS_URL = DAD_SERVER_URL_BASE + "getNextDogs";
    private static String JUDGE_DOG_URL = DAD_SERVER_URL_BASE + "judgeDog";
    private static String GET_LIKED_DOGS_URL = DAD_SERVER_URL_BASE + "getLikedDogs";

    /**
     * Constructs an instance of the DADAPI class.
     */
    private DADAPI() {
    }

    /**
     * Returns an instance of the {@code DADAPI} class.
     *
     * @return an instance of the {@code DADAPI} class
     */
    public static DADAPI getInstance() {
        if (instance == null) {
            instance = new DADAPI();
        }
        return instance;
    }

    /**
     * Makes a POST request (with authentication) to the given URL with the given {@code jsonBody}
     * as the body of the POST request. Returns the JSON response as a {@code String} or {@code null}
     * if some error occurred.
     *
     * @param url the URL to make a POST request to
     * @param jsonBody the JSON body of the POST request
     * @return the JSON response from the DAD server, or {@code null} if some error occurred
     */
    public JSONTokener makeDADRequest(String url, JSONObject jsonBody) {
        try {
            // Initialize an HTTP connection to the server.
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // Add the user's Facebook login token to the request header.
            connection.setRequestProperty("access_token", AccessToken.getCurrentAccessToken().toString());
            connection.setRequestProperty("Content-Type", "application/json");
            // Send the request body.
            OutputStream output = connection.getOutputStream();
            output.write(jsonBody.toString().getBytes());
            // Get and return the response.
            InputStream response = connection.getInputStream();
            String responseText = IOUtils.toString(response);
            return new JSONTokener(responseText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Makes a DAD server request at the given URL, parses the response as a JSON
     * array of dogs and returns a set containing these dogs.
     *
     * @param url a DAD endpoint that returns a JSON array of dogs
     * @return a set of dogs retrieved from the given URL
     */
    private Set<Dog> getDogsAtUrl(String url) {
        Set<Dog> result = new HashSet<>();

        JSONObject parameters = new JSONObject();
        JSONTokener response = makeDADRequest(url, parameters);
        try {
            JSONArray dogsArray = (JSONArray) response.nextValue();
            for (int i = 0; i < dogsArray.length(); i++) {
                result.add(new Dog(dogsArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns a set of dogs that the user has not yet judged.
     *
     * @return a set of dogs that the user has not yet judged
     */
    public Set<Dog> getNextDogs() {
        return getDogsAtUrl(GET_NEXT_DOGS_URL);
    }

    /**
     * Returns a set of dogs that the user has liked.
     *
     * @return a set of dogs that the user has liked
     */
    public Set<Dog> getLikedDogs() {
        return getDogsAtUrl(GET_LIKED_DOGS_URL);
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
        makeDADRequest(JUDGE_DOG_URL, parameters);
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
