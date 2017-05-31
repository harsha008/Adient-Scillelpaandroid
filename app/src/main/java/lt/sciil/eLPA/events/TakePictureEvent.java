package lt.sciil.eLPA.events;

public class TakePictureEvent {

    private final String userPhotoPath;

    public TakePictureEvent(String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }
}
