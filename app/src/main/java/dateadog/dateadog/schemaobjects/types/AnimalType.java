package dateadog.dateadog.schemaobjects.types;


public enum AnimalType {
    HINT("Animal (Optional)", "", null),
    DOG("Dog", DogType.values());

	
    String name;
    String value;
    IBreedType[] values;

    AnimalType(String name, String value, IBreedType[] values) {
	this.name = name;
	this.value = value;
	this.values = values;
    }

    AnimalType(String str, IBreedType[] values) {
	this.name = this.value = str;
	this.values = values;
    }

    public String getName() {
	return this.name;
    }

    public String getValue() {
	return this.value;
    }

    public IBreedType[] getValues() {
	return this.values;
    }
    
    @Override
    public String toString() {
	return this.name;
    }
}
