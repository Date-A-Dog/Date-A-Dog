package dateadog.dateadog;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the {@code User} class.
 */
public class UserUnitTest {

    private User user1;
    private User user2 = null;

    @Before
    public void setUp() throws Exception {
        // setting up new User
        user1 = new User("1235400kl", "John");

    }

    @Test
    public void testConstructor() throws Exception {

        // testing whether constructor is null or not
        assertEquals(user2, null);
        assertNotEquals(user1, null);
    }

    @Test
    public void testGetUserToken() throws Exception {
        // testing getUserToken
        assertEquals(user1.getUserToken(), "1235400kl");
        assertNotEquals(user1.getUserToken(), "12330455");
    }

    @Test
    public void testGetUserName() throws Exception {
        // testing getUserNAme
        assertEquals(user1.getUserName(), "John");
        assertNotEquals(user1.getUserName(), "Robert");
    }




}