package dateadog.dateadog.schemaobjects;

import java.util.ArrayList;
import java.util.List;

import dateadog.dateadog.wrappers.StringWrapper;

public class Photos {
    public static final String ID_XSMALL = "pnt";
    public static final String ID_SMALL = "t";
    public static final String ID_MED = "fpm";
    public static final String ID_LARGE = "pn";
    public static final String ID_XLARGE = "x";

    private StringWrapper[] photo;

    public Photos() {

    }

    public String[] getPhotos() {
	String[] array = new String[photo.length];
	for (int i = 0; i < photo.length; i++) {
	    array[i] = photo[i].toString();
	}
	return array;
    }

    public void setPhotos(String[] photos) {
	photo = new StringWrapper[photos.length];
	for (int i = 0; i < photos.length; i++) {
	    photo[i] = new StringWrapper(photos[i]);
	}
    }

    public String getFirstXSmallUrl() {
	return getFirst(ID_XSMALL);
    }

    public String getFirstMedUrl() {
	return getFirst(ID_MED);
    }

    public String getFirstLargeUrl() {
	return getFirst(ID_LARGE);
    }

    public String getFirstXLargeUrl() {
	return getFirst(ID_XLARGE);
    }

    public ArrayList<String> getLargePhotoUrlList() {
	return getPhotoUrlList(ID_LARGE);
    }

    public ArrayList<String> getXLargePhotoUrlList() {
	return getPhotoUrlList(ID_XLARGE);
    }

    private String getFirst(String sizeStr) {
	for (StringWrapper p : photo) {
	    if (p.toString().contains(sizeStr))
		return p.toString();
	}
	return null;
    }

    private ArrayList<String> getPhotoUrlList(String sizeStr) {
	ArrayList<String> photoList = new ArrayList<String>(0);

	for (StringWrapper p : photo) {
	    if (p.toString().contains(sizeStr))
		photoList.add(p.toString());
	}
	return photoList;
    }
}