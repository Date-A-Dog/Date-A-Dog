package dateadog.dateadog;

import java.util.Date;

/**
 * A {@code DateRequest} is a user submission of what time and what dog they want to have a date with.
 */
public class DateRequest {

    /**
     * States that a DateRequest can be in.
     */
    public enum Status {
        ACCEPTED, DENIED, PENDING
    };

    /** The ID for this date request. */
    private int requestId;
    /** The date/time at which the user will have a date with the dog. */
    private Date date;
    /** The ID of the dog the date is with. */
    private int dogId;
    /** The current status of the date request. */
    private Status status;

    /**
     * Constructs and initializes a new {@code DateRequest} with the given data.
     *
     * @param requestId the ID for this date request
     * @param date the date/time at which the user will have a date with the given dog
     * @param dogId the ID of the dog the date is with
     * @param status the status of the date request (accepted, denied or pending)
     */
    public DateRequest(int requestId, Date date, int dogId, Status status) {
        this.requestId = requestId;
        this.date = new Date(date.getTime());
        this.dogId = dogId;
        this.status = status;
    }

    /**
     * Returns a number that uniquely identifies this {@code DateRequest}.
     *
     * @return a number that uniquely identifies this {@code DateRequest}
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Returns the date/time at which the user will have a date with the dog.
     *
     * @return the date/time at which the user will have a date with the dog
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the ID of the dog the date is with.
     *
     * @return the ID of the dog the date is with
     */
    public int getDogId() {
        return dogId;
    }

    /**
     * Returns the current status of the date request.
     *
     * @return the current status of the date request
     */
    public Status getStatus() {
        return status;
    }

}
