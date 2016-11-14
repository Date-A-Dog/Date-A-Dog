package dateadog.dateadog;

/**
 * This is the object created that stores the data for the tindercard
 * Created by nirav on 05/10/15.
 */
public class Data_TinderUI {

    private String description;

    private long dogId;

    private String imagePath;

    public Data_TinderUI(String imagePath, long dogId, String description) {
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
