package dateadog.dateadog;
/**
 * A DateRequest is a user submission of what time and what dog they want to have a date with
 * @author Amanda Loh
 * @version 1.0
 */

import Date;

public class DateRequest {
    private int dogID;
    private Date date;
    private int requestID;
    private char status;
    private String token;

    /**
     * Constructor
     * @param dogId identifies the dog
     * @param date the time fo the date for the dog
     * @param requestID for retrirving the date request from the database
     */
    public DateRequest(int dogId, Date date, int requestID, char status, String token) {
        this.dogID = dogID;
        this.date = date;
        this.requestID = requestID;
        this.status = status;
        this.token = token;

    }

    /**
     * Getters
     */
    int getDogID() {
        return this.dogID;
    }

    long getEpoch() {
        return this.epoch;
    }



}