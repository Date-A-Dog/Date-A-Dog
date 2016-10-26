package dateadog.dateadog;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the DogProfile class.
 */
public class DogProfileUnitTest {

    private DogProfile dog1;
    private DogProfile dog2 = null;

    @Before
    public void setUp() throws Exception {
        // setting up new DogProfile
        dog1 = new DogProfile(1,"Akita", "5", null, null, "Grey" , "Cosette", "M", true );
    }

    @Test
    public void testConstructor() throws Exception {

        // testing whether constructor is null or not
        assertEquals(dog2, null);
        assertNotEquals(dog1, null);
    }



}