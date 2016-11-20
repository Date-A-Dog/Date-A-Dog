package dateadog.dateadog.wrappers;

public class StringWrapper {
    private String $t;

    public StringWrapper() {

    }

    public StringWrapper(String $t) {
	this.$t = $t;
    }

    @Override
    public String toString() {
	return $t;
    }
}
