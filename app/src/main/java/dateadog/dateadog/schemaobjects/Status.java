package dateadog.dateadog.schemaobjects;

import dateadog.dateadog.wrappers.IntWrapper;
import dateadog.dateadog.wrappers.StringWrapper;

public class Status {

    private IntWrapper code;
    private StringWrapper message;

    public Status() {

    }

    public int getCode() {
	return code.getInt();
    }

    public String getMessage() {
	return message.toString();
    }

    @Override
    public String toString() {
	return "Status [code=" + code + ", message=" + message + "]";
    }

}
