package dateadog.dateadog;

/**
 * This class stores the data necessary to display a single dog card.
 */
public class DogCard {

    private String description;

    private long dogId;

    private String imagePath;

    public DogCard(String imagePath, long dogId, String description) {
        this.imagePath = imagePath;
        this.description = description;
        this.dogId = dogId;
    }

    public long getDogId() { return dogId; }
    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

}
