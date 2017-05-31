package lt.sciil.eLPA.events;

public class DeletePictureEvent {

    private final String userPhotoPath;

    public DeletePictureEvent(String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }
}
