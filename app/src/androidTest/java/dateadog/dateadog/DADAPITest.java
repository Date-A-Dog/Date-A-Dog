package dateadog.dateadog;

import android.content.Context;
import android.os.StrictMode;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DADAPITest {
    @Test
    public void makeDADRequestTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DADAPI api = DADAPI.getInstance(appContext);
        api.makeRequest("http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/getNextDogsDemo", new JSONObject(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        });
    }

    @Test
    public void getNextDogsTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        DADAPI api = DADAPI.getInstance(appContext);
        // api.getNextDogs();

    }
}
