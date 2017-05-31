package lt.sciil.eLPA.database.model;


import io.reactivex.Observable;

public enum PictureQuality {
    SD("Small"),
    HD("Mid"),
    FULL_HD("Big"),
    ORIGINAL("Original");

    private final String quality;

    PictureQuality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public static PictureQuality getPictureQuality(String quality) {
        return Observable.fromArray(PictureQuality.values())
                .filter(pictureQuality -> pictureQuality.getQuality().equals(quality))
                .blockingFirst(PictureQuality.FULL_HD);
    }
}
