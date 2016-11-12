package dateadog.dateadog.schemaobjects;

import dateadog.dateadog.schemaobjects.types.AgeType;
import dateadog.dateadog.schemaobjects.types.AnimalType;
import dateadog.dateadog.schemaobjects.types.GenderType;
import dateadog.dateadog.schemaobjects.types.SizeType;
import dateadog.dateadog.schemaobjects.types.StatusType;
import dateadog.dateadog.wrappers.IntWrapper;
import dateadog.dateadog.wrappers.StringWrapper;

public class Pet {

    protected IntWrapper id;
    protected StringWrapper name;
    protected StringWrapper age;
    protected StringWrapper sex;
    protected StringWrapper size;
    protected StringWrapper animal;
    protected StringWrapper description;
    protected StringWrapper mix;
    protected StringWrapper shelterId;
    protected StringWrapper shelterPetId;
    protected StringWrapper lastUpdate;
    protected StringWrapper status;

    protected Breeds breeds;
    protected Options options;

    protected Media media;
    protected Contact contact;

    public Pet() {
    }

    public int getId() {
	return this.id.getInt();
    }

    public String getName() {
	return name.toString();
    }

    public String getMix() {
	return mix.toString();
    }

    public String getShelterId() {
	return shelterId.toString();
    }

    public String getLastUpdate() {
	return lastUpdate.toString();
    }

    public StringWrapper getShelterPetId() {
	return shelterPetId;
    }

    public Media getMedia() {
	return media;
    }

    public Options getOptions() {
	return options;
    }

    @Override
    public int hashCode() {
	return id.getInt();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (!(obj instanceof Pet))
	    return false;

	Pet pet = (Pet) obj;

	if (id.getInt() == pet.getId()) {
	    return true;
	}
	return false;
    }

    public String getBreedListString() {
	return breeds.toString();
    }

    public String getOptionsListString() {
	return options.toString();
    }

    public String getDescription() {
	if (description.toString() == null || description.toString().isEmpty())
	    return "No description.";

	return description.toString().replace("<div>", "")
		.replace("</div>", "");
    }

    public Contact getContact() {
	return contact;
    }

    public AnimalType getAnimal() {
	return AnimalType.valueOf(animal.toString());
    }

    public GenderType getSex() {
	for (GenderType type : GenderType.values()) {
	    if (type.getValue().equals(this.sex))
		return type;
	}
	return null;
    }

    public SizeType getSize() {
	return SizeType.valueOf(size.toString());
    }

    public AgeType getAge() {
	for (AgeType type : AgeType.values()) {
	    if (type.getValue().equals(this.age))
		return type;
	}
	return null;
    }

    public StatusType getStatus() {
	for (StatusType type : StatusType.values()) {
	    if (type.getValue().equals(this.status))
		return type;
	}
	return null;
    }

    public String getShortDescription() {
	String age = (getAge() == null) ? "Unknown Age" : getAge().getName();
	String sex = (getSex() == null) ? "Unknown Sex" : getSex().getName();
	String size = (getSize() == null ? "Unknown Size" : getSize().getName());
	return String.format("%s/%s, %s", age, sex, size);
    }
}
