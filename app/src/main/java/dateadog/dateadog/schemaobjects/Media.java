package dateadog.dateadog.schemaobjects;

public class Media {

    private Photos photos;

    public Media() {

    }

    public Photos getPhotos() {
	return photos;
    }

    public String[] getPhotoUrlList() {
	return photos.getPhotos();
    }

    public void setPhotos(String[] photos) {
	if (this.photos == null)
	    this.photos = new Photos();
	this.photos.setPhotos(photos);
    }
}
