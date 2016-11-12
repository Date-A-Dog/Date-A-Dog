package dateadog.dateadog.schemaobjects.types;

public enum StatusType {
	Adoptable("Adoptable", "A"), 
	Hold("On Hold", "H"), Pending("Pending", "P"), Removed(
			"Adopted/removed", "X");

	String name;
	String value;

	StatusType(String name, String value) {
		this.name = name;
		this.value = value;
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
