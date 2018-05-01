package hotel.models;

import java.util.Date;

public class Room {

    private String id;
    private int quality;
    private String adjoining;
    private int bedNumber;
    public boolean smoking;
    private int maxRate;

    public Room(String id, int quality, String adjoining, int bedNumber, boolean smoking,
                        int maxRate) {
        this.id = id;
        this.quality = quality;
        this.adjoining = adjoining;
        this.bedNumber = bedNumber;
        this.smoking = smoking;
        this.maxRate = maxRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getAdjoining() {
        return adjoining;
    }

    public void setAdjoining(String adjoining) {
        this.adjoining = adjoining;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(int maxRate) {
        this.maxRate = maxRate;
    }
}
