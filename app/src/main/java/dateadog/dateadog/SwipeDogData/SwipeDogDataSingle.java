package dateadog.dateadog.SwipeDogData;
import java.util.List;
/**
 * Created by Amanda on 11/8/16.
 */

public class SwipeDogDataSingle {
    private String name;
    private String age;
    private List<String> pictureUrls;
    private String description;

    public SwipeDogDataSingle(String name, String age, List<String> pictureUrls, String description) {
        this.name = name;
        this.age = age;
        this.pictureUrls = pictureUrls;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getAge() {
        return age;
    }

    public List<String> getListUrls() {
        return getListUrls();
    }

    public String getDescrition() {
        return description;
    }

}
