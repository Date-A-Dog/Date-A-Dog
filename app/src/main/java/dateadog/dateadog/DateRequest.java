package dateadog.dateadog;
/**
 * A DateRequest is a user submission of what time and what dog they want to have a date with
 * @author Amanda Loh
 * @version 1.0
 */

import java.util.Date;

public class DateRequest {

    /**
     * Types of status of dog requests
     */
    public enum status {
        ACCEPT, DENY, PENDING
    };

    private int dogID;
    private Date date;
    private int requestID;
    private int status;
    private String token;

    /**
     * Constructor
     * @param dogId identifies the dog
     * @param date the time fo the date for the dog
     * @param requestID for retrirving the date request from the database
     */
    public DateRequest(int dogId, Date date, int requestID, int status, String token) {
        this.dogID = dogID;
        this.date = date;
        this.requestID = requestID;
        this.status = status;
        this.token = token;

    }

    //GETTERS
    /*
     * @return returns the dogID
     */
    int getDogID() {
        return this.dogID;
    }

    /**
     * @return returns the date of the dog date
     */
    Date getDate() {
        return this.date;
    }

    /**
     * @return returns the ID we need to get request from database
     */
    int getRequestID() {
        return this.requestID;
    }

    /**
     *
     * @return the status of request
     */
    int getStatus() {
        return this.status;
    }

    /**
     *
     * @return the token of this request
     */
    String getToken() {
        return this.token;
    }

    //SETTERS

    /**
     * @param newStatus what the new status ofthe request has to be set to
     */
    void setStatus(char newStatus) {
       this.status = newStatus;
    }
}

