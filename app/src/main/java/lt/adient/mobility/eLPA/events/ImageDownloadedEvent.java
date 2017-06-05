package lt.adient.mobility.eLPA.events;

/**
 * Created by Simonas on 2016-11-30.
 */

public class ImageDownloadedEvent {
    private final String imagePath;
    private final PhotoType photoType;

    public ImageDownloadedEvent(String imagePath, PhotoType photoType) {
        this.imagePath = imagePath;
        this.photoType = photoType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }
}
