package hotel.models;

import java.time.LocalDate;

public class Room {

    private String roomID;
    private String quality;
    private String bedNumber;
    public String smoking;
    private String adjoining;
    private int maxRate;
    private static String location;
    private static Room room;
    private static LocalDate checkInDate;
    private static LocalDate checkOutDate;

    public Room() {

    }

    public Room(String roomID, String quality, String bedNumber, String smoking, String adjoining, int maxRate) {
        this.roomID = roomID;
        this.quality = quality;
        this.bedNumber = bedNumber;
        this.smoking = smoking;
        this.adjoining = adjoining;
        this.maxRate = maxRate;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getSmoking() {
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

    public static Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public static LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public static LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
