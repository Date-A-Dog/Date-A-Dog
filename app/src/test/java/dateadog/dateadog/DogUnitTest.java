package dateadog.dateadog;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the {@code Dog} class.
 */
public class DogUnitTest {

    private Dog dog1;
    private Dog dog2 = null;

    @Before
    public void setUp() throws Exception {
        // setting up new DogProfile
        List<String> breeds = new ArrayList<String>();
        breeds.add("Akita");
        dog1 = new Dog(1,breeds, "5", 0, null, "Grey" , "Cosette", "M", true );
    }

    @Test
    public void testConstructor() throws Exception {

        // testing whether constructor is null or not
        assertEquals(dog2, null);
        assertNotEquals(dog1, null);
    }

}