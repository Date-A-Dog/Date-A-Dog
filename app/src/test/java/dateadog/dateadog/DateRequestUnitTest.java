package dateadog.dateadog;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.text.*;
import java.util.*;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the {@code DateRequest} class.
 */
public class DateRequestUnitTest {

    private DateRequest request1;
    private DateRequest request2 = null;
    Date date1 = new Date();
    Date date2 = null;

    @Before
    public void setUp() throws Exception {
        // setting up new DateRequest
        request1 = new DateRequest(12,date1, 10, DateRequest.Status.ACCEPTED );

    }

    @Test
    public void testConstructor() throws Exception {

        // testing whether constructor is null or not
        assertEquals(request2, null);
        assertNotEquals(request1, null);
    }

    @Test
    public void testGetRequestID() throws Exception {
        // testing GetRequestID
        assertEquals(request1.getRequestId(), 12);
        assertNotEquals(request1.getRequestId(), 10);
    }

    @Test
    public void testGetDate() throws Exception {
        // testing GetDate
        assertEquals(request1.getDate(), date1);
        assertNotEquals(request1.getDate(), date2);
    }

    @Test
    public void testGetDogID() throws Exception {
        // testing GetDogID
        assertEquals(request1.getDogId(), 10);
        assertNotEquals(request1.getDogId(), 16);
    }

    @Test
    public void testGetStatus() throws Exception {
        // testing GetStatus
        assertEquals(request1.getStatus(), DateRequest.Status.ACCEPTED);
        assertNotEquals(request1.getStatus(), DateRequest.Status.DENIED);
    }



}