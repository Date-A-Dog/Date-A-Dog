package dateadog.dateadog.schemaobjects;


public class Pets<T extends Pet> {

    private T[] pet;

    public Pets() {

    }

    public T[] getPets() {
	return pet;
    }
}
