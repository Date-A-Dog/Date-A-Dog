package dateadog.dateadog;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DogAndroidTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("dateadog.dateadog", appContext.getPackageName());
    }

    private Dog dog1;
    private Dog dog2 = null;
    JSONObject dogJson = new JSONObject();

    JSONObject json = new JSONObject();

    @Before
    public void setUp() throws Exception {
        // setting up JSONObject
        try {
            json.put("id", "1234567");
            json.put("name", "Honey Bear");
            json.put("age", "Senior");
            json.put("sex", "F");
            json.put("size", "M");
            JSONObject city = new JSONObject();
            JSONObject photos = new JSONObject();
            JSONObject img = new JSONObject();
            img.put("x", "http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=500&-x.jpg");
            JSONArray breeds = new JSONArray();
            breeds.put("Pit Bull Terrier");
            breeds.put("Bull Terrier");
            JSONObject one = new JSONObject();
            one.put("1", img);
            photos.put("photos", one);
            json.put("media", photos);
            city.put("city", "Seattle");
            json.put("contact", city);
            json.put("shelterId", "WA355");
            // setting up new DogProfile
            //List<String> breeds = new ArrayList<String>();
            //breeds.add("Akita");
            //breeds.add("Harrier");
            json.put("breeds", breeds);
            dogJson.put("dog", json);
            dog1 = new Dog(dogJson );

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void testConstructor() throws Exception {

        // testing whether constructor is null or not
        assertEquals(dog2, null);
    }

    @Test
    public void testConstructor1() throws Exception {

        // testing whether constructor is null or not
        assertNotEquals(dog1, null);
    }


    @Test
    public void testGetDogId() throws Exception {

        // testing getDogId which returns dog Id
        assertEquals(dog1.getDogId(), 1234567);
    }

    @Test
    public void testGetDogIdNotEqual() throws Exception {

        // testing getDogId which returns dog Id
        assertNotEquals(dog1.getDogId(), 231);
    }

    @Test
    public void testGetName() throws Exception {

        // testing getName which returns dog name
        assertEquals(dog1.getName(), "Honey Bear");
    }

    @Test
    public void testGetNameNotEqual() throws Exception {

        // testing getName which returns dog name
        assertNotEquals(dog1.getName(), "Honey");
    }

    @Test
    public void testGetSex() throws Exception {

        // testing getSex which returns dog's sex
        assertEquals(dog1.getSex(), "Female");

    }

    @Test
    public void testGetSexNotEqual() throws Exception {

        // testing getSex which returns dog's sex
        assertNotEquals(dog1.getSex(), "Male");
    }

    @Test
    public void testGetSize() throws Exception {

        // testing getSize which returns dog's size
        assertEquals(dog1.getSize(), "Medium Dog");
    }

    @Test
    public void testGetSizeNotEqual() throws Exception {

        // testing getSize which returns dog's size
        assertNotEquals(dog1.getSize(), "Medium");
    }

    @Test
    public void testGetAge() throws Exception {

        // testing getAge which returns dog's age
        assertEquals(dog1.getAge(), "Senior");
    }

    @Test
    public void testGetAgeNotEqual() throws Exception {

        // testing getAge which returns dog's age
        assertNotEquals(dog1.getAge(), "Junior");
    }

    @Test
    public void testGetImage() throws Exception {

        // testing getImageURL which returns dog's image
        assertEquals(dog1.getImageURL(), "http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=500&-x.jpg");
    }

    @Test
    public void testGetImageNotEqual() throws Exception {

        // testing getImageURL which returns dog's image
        assertNotEquals(dog1.getImageURL(), "http://photos.petfinder.com");
    }

    @Test
    public void testGetCity() throws Exception {

        // testing getCity which returns dog's city
        assertEquals(dog1.getCity(), "Seattle");
    }

    @Test
    public void testGetCityNotEqual() throws Exception {

        // testing getCity which returns dog's city
        assertNotEquals(dog1.getCity(), "Seatac");
    }

    @Test
    public void testGetShelterId() throws Exception {

        // testing getshelterId which returns dog's city
        assertEquals(dog1.getShelterId(), "WA355");
    }

    @Test
    public void testGetShelterIdNotEqual() throws Exception {

        // testing getshelterId which returns dog's city
        assertNotEquals(dog1.getShelterId(), "WA300");
    }

    @Test
    public void testGetBreedsString() throws Exception {

        // testing getBreedsString which returns dog's breeds
        assertEquals(dog1.getBreedsString(), "Pit Bull Terrier, Bull Terrier");
    }

    @Test
    public void testGetBreedsStringNotEqual() throws Exception {

        // testing getBreedsString which returns dog's breeds
        assertNotEquals(dog1.getBreedsString(), "Bull Terrier, Bull Terrier");
    }

    @Test
    public void testGetFixNameFoster() throws Exception {

        // testing fixName which fixes dog's name
        assertEquals(dog1.fixName("Foster Needed"), "Dog(s) Intended for Foster Care (No Name)");
    }

    @Test
    public void testGetFixNameFosterNotEqual() throws Exception {

        // testing fixName which fixes dog's name
        assertNotEquals(dog1.fixName("Needed"), "Dog(s) Intended for Foster Care (No Name)");
    }

    @Test
    public void testGetFixNameAdoption() throws Exception {

        // testing fixName which fixes dog's name
        assertEquals(dog1.fixName("Wanted Adoption"), "Dog(s) Intended for Adoption (No Name)");
    }

    @Test
    public void testGetFixNameAdoptionNotEqual() throws Exception {

        // testing fixName which fixes dog's name
        assertNotEquals(dog1.fixName("Wanted"), "Dog(s) Intended for Adoption (No Name)");
    }

    @Test
    public void testGetFixNameDonation() throws Exception {

        // testing fixName which fixes dog's name
        assertEquals(dog1.fixName("Wanted Donations"), "Dog(s) Needing Donations (No Name)");
    }

    @Test
    public void testGetFixNameDonationNotEqual() throws Exception {

        // testing fixName which fixes dog's name
        assertNotEquals(dog1.fixName("Wanted"), "Dog(s) Needing Donations (No Name)");
    }

    @Test
    public void testGetFixName() throws Exception {

        // testing fixName which fixes dog's name
        assertEquals(dog1.fixName("Honey Bear"), "Honey Bear");
    }

    @Test
    public void testGetFixNameNotEqual() throws Exception {

        // testing fixName which fixes dog's name
        assertNotEquals(dog1.fixName("Honey Bear"), "Bear");
    }

    @Test
    public void testGetFixSizeSmall() throws Exception {

        // testing fixSize which fixes dog's size
        assertEquals(dog1.fixSize("S"), "Small Dog");
    }

    @Test
    public void testGetFixSizeSmallNotEqual() throws Exception {

        // testing fixSize which fixes dog's size
        assertNotEquals(dog1.fixSize("S"), "Medium Dog");;
    }

    @Test
    public void testGetFixSizeMedium() throws Exception {

        // testing fixSize which fixes dog's size
        assertEquals(dog1.fixSize("M"), "Medium Dog");
    }

    @Test
    public void testGetFixSizeMediumNotEqual() throws Exception {

        // testing fixSize which fixes dog's size
        assertNotEquals(dog1.fixSize("M"), "Small Dog");;
    }

    @Test
    public void testGetFixSizeLarge() throws Exception {

        // testing fixSize which fixes dog's size
        assertEquals(dog1.fixSize("L"), "Large Dog");
    }

    @Test
    public void testGetFixSizeLargeNotEqual() throws Exception {

        // testing fixSize which fixes dog's size
        assertNotEquals(dog1.fixSize("L"), "Medium Dog");;
    }

    @Test
    public void testGetFixSizeUnknown() throws Exception {

        // testing fixSize which fixes dog's size
        assertEquals(dog1.fixSize("XL"), "Unknown Size");
    }

    @Test
    public void testGetFixSizeUnknownNotEqual() throws Exception {

        // testing fixSize which fixes dog's size
        assertNotEquals(dog1.fixSize("XL"), "Medium Dog");;
    }

    @Test
    public void testGetFixSexMale() throws Exception {

        // testing fixSex which fixes dog's sex
        assertEquals(dog1.fixSex("M"), "Male");
    }

    @Test
    public void testGetFixSexMaleNotEequal() throws Exception {

        // testing fixSex which fixes dog's sex
        assertNotEquals(dog1.fixSex("M"), "Female");
    }

    @Test
    public void testGetFixSexFemale() throws Exception {

        // testing fixSex which fixes dog's sex
        assertEquals(dog1.fixSex("F"), "Female");
    }

    @Test
    public void testGetFixSexFemaleNotEequal() throws Exception {

        // testing fixSex which fixes dog's sex
        assertNotEquals(dog1.fixSex("F"), "Male");
    }

    @Test
    public void testGetFixSexUnknown() throws Exception {

        // testing fixSex which fixes dog's sex
        assertEquals(dog1.fixSex("A"), "A");
    }

    @Test
    public void testGetFixSexUnknownNotEqual() throws Exception {

        // testing fixSex which fixes dog's sex
        assertNotEquals(dog1.fixSex("A"), "Male");
    }



}
