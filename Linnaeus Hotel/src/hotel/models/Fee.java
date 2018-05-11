package hotel.models;

public class Fee {

    private int id;
    private int reservationId;
    private int fee;
    private String description;

    public Fee(int id, int reservationId, int fee, String description) {
        this.id = id;
        this.reservationId = reservationId;
        this.fee = fee;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
