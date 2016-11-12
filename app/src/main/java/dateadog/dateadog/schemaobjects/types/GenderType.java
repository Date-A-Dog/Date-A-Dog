package dateadog.dateadog.schemaobjects.types;

public enum GenderType {
    	HINT("Gender (Optional)", ""),
	Male("Male", "M"), 
	Female("Female", "F");

	String name;
	String value;

	GenderType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	GenderType(String str) {
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
