package dateadog.dateadog.schemaobjects.types;

public enum SizeType {
    HINT("Age (Optional)", ""), SMALL("Small", "S"), MED("Medium", "M"), LARGE(
	    "Large", "L"), X_LARGE("X-Large", "XL");

    String name;
    String value;

    SizeType(String name, String value) {
	this.name = name;
	this.value = value;
    }

    SizeType(String str) {
	this.name = this.value = str;
    }

    @Override
    public String toString() {
	return this.name;
    }

    public String getName() {
	return this.name;
    }

    public String getValue() {
	return this.value;
    }
}
