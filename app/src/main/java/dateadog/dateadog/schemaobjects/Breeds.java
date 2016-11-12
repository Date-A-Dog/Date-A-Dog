package dateadog.dateadog.schemaobjects;

import java.util.Arrays;

import dateadog.dateadog.wrappers.StringArrayWrapper;

public class Breeds {
    private StringArrayWrapper[] breed;

    public Breeds() {

    }

    public String[] getBreeds() {
	String[] strList = new String[breed.length];
	for (int i = 0; i < breed.length; i++) {
	    strList[i] = breed[i].toString();
	}
	return strList;
    }

    public void setBreeds(String[] breeds) {
	breed = new StringArrayWrapper[breeds.length];
	for (int i = 0; i < breeds.length; i++) {
	    breed[i] = new StringArrayWrapper(breeds[i]);
	}
    }

    @Override
    public String toString() {
	return Arrays.toString(breed);
    }
}
