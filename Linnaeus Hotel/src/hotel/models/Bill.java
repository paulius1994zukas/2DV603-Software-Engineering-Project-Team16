package hotel.models;

import java.util.Date;

public class Bill {

    private String reservationId;

    public Bill(String reservationId, String firstName, String lastName, String address, String phoneNumber,
                        String creditCardNumber, String passportNumber, Date checkInDate, Date checkOutDate,
                        int totalDays, float toPay) {
        this.reservationId = reservationId;
    }

}
