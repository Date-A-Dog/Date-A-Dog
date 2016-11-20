package dateadog.dateadog.wrappers;

public class IntWrapper {
    private int $t;

    public IntWrapper() {

    }

    public IntWrapper(int $t) {
	this.$t = $t;
    }

    public int getInt() {
	return $t;
    }

    @Override
    public String toString() {
	return Integer.toString($t);
    }
}
