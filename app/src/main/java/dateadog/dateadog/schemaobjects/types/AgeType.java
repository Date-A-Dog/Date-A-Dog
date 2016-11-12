package dateadog.dateadog.schemaobjects.types;

public enum AgeType {
    	HINT("Age (Optional)", ""),
	SMALL("Baby"),
	MED("Young"),
	LARGE("Adult"),
	X_LARGE("Senior");

	String name;
	String value;

	AgeType(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	AgeType(String str) {
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
