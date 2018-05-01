package hotel.models;

import java.util.Date;

public class Room {

    private String roomID;
    private int quality;
    private int bedNumber;
    public String smoking;
    private String adjoining;
    private String status;
    private int maxRate;
    private static String location;

    public Room(){

    }

    public Room(String roomID, int quality, int bedNumber, String smoking, String adjoining,
                String status, int maxRate) {
        this.roomID = roomID;
        this.quality = quality;
        this.bedNumber = bedNumber;
        this.smoking = smoking;
        this.adjoining = adjoining;
        this.status = status;
        this.maxRate = maxRate;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String isSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getAdjoining() {
        return adjoining;
    }

    public void setAdjoining(String adjoining) {
        this.adjoining = adjoining;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(int maxRate) {
        this.maxRate = maxRate;
    }

    public static String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
