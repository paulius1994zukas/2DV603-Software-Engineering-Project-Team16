package hotel.views.main;

import com.jfoenix.controls.JFXComboBox;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.helpers.HotelHelper;
import hotel.models.GuestAccount;
import hotel.models.Reservation;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private String location;
    private AlertMaker alert = new AlertMaker();
    @FXML
    private JFXComboBox locationComboBox;
    @FXML
    private Label totalRoomsLbl;
    @FXML
    private Label occupiedRoomsLbl;
    @FXML
    private Label availableRoomsLbl;
    @FXML
    private Label availablePenthouseLbl;
    @FXML
    private Label availableLakeViewLbl;
    @FXML
    private Label availableGardenViewLbl;
    @FXML
    private Label availableStreetViewLbl;
    @FXML
    private Label availableBasicLbl;
    @FXML
    private Label totalGuestsLbl;
    @FXML
    private Label maleGuestsLbl;
    @FXML
    private Label femaleGuestsLbl;
    @FXML
    private Label checkingInTodayLbl;
    @FXML
    private Label checkingOutTodayLbl;
    @FXML
    private VBox room209VBox;

    private int totalRoomsCount = 20;
    private int occupiedCount = 0;
    private int availableCount = 0;
    private int totalGuestsCount = 0;
    private int maleGuestsCount = 0;
    private int femaleGuestsCount = 0;
    private int penthouseCount = 0;
    private int lakeViewCount = 0;
    private int gardenViewCount = 0;
    private int streetViewCount = 0;
    private int basicCount = 0;
    private int checkInsTodayCount = 0;
    private int checkOutsTodayCount = 0;

    Room room = new Room();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList locations = FXCollections.observableArrayList();
        locations.add("Kalmar");
        locations.add("Växjö");
        locationComboBox.setItems(locations);
        locationComboBox.getSelectionModel().select(1);
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
        updateMainScreenInfo();
    }

    @FXML
    private void onLocationComboBoxChanged(ActionEvent event) {
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
        updateMainScreenInfo();
    }

    @FXML
    private void onGuestAccountBtnClick(ActionEvent event) {
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/guestAccount/guestAccount.fxml"),
                "Guest Account", null, false);
    }

    @FXML
    private void onReservationsBtnClick(ActionEvent event) {
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/reservations/reservationsList.fxml"),
                "Reservations", null, true);
    }

    @FXML
    private void onAvailableRoomsBtnClick(ActionEvent event) {
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/availableRoomSearch/availableRoomSearch.fxml"),
                "Available Rooms", null, false);
    }

    @FXML
    private void onHelpBtnClick(ActionEvent event) {
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/help/help.fxml"),
                "Help", null, false);
    }

    private void updateMainScreenInfo() {
        getOccupiedCount();
        occupiedRoomsLbl.setText(String.format("%s", occupiedCount));
        getAvailableCount();
        availableRoomsLbl.setText(String.format("%s", availableCount));
        getGuestsCount();
        totalGuestsLbl.setText(String.format("%s", totalGuestsCount));
        getMaleGuestsCount();
        maleGuestsLbl.setText(String.format("%s", maleGuestsCount));
        getFemaleGuestsCount();
        femaleGuestsLbl.setText(String.format("%s", femaleGuestsCount));
        getAvailablePenthouseCount();
        getAvailableLakeViewCount();
        getAvailableGardenViewCount();
        getAvailableStreetViewCount();
        getAvailableBasicCount();
        getCheckInsTodayCount();
        checkingInTodayLbl.setText(String.format("%s", checkInsTodayCount));
        getCheckOutsTodayCount();
        checkingOutTodayLbl.setText(String.format("%s", checkOutsTodayCount));
    }

    private LocalDate getTodaysDate() {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

    private void getOccupiedCount() {
        int count = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(getTodaysDate());
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE ? BETWEEN CHECKINDATE AND CHECKOUTDATE AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                count++;
            }
            occupiedCount = count;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getAvailableCount() {
        availableCount = totalRoomsCount - occupiedCount;
    }

    private void getGuestsCount() {
        totalGuestsCount = occupiedCount;
    }

    private void getMaleGuestsCount() {
        int count = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(getTodaysDate());
        parameters.add("Male");
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE ? BETWEEN CHECKINDATE AND CHECKOUTDATE AND SEX=? AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                count++;
            }
            maleGuestsCount = count;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getFemaleGuestsCount() {
        int count = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(getTodaysDate());
        parameters.add("Female");
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE ? BETWEEN CHECKINDATE AND CHECKOUTDATE AND SEX=? AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                count++;
            }
            femaleGuestsCount = count;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getAvailablePenthouseCount() {
        if (locationComboBox.getSelectionModel().getSelectedItem() == "Växjö")
            availablePenthouseLbl.setText("2");
        else
            availablePenthouseLbl.setText("2");
    }

    private void getAvailableLakeViewCount() {
        if (locationComboBox.getSelectionModel().getSelectedItem() == "Växjö")
            availableLakeViewLbl.setText("4");
        else
            availableLakeViewLbl.setText("4");
    }

    private void getAvailableGardenViewCount() {
        if (locationComboBox.getSelectionModel().getSelectedItem() == "Växjö")
            availableGardenViewLbl.setText("5");
        else
            availableGardenViewLbl.setText("4");
    }

    private void getAvailableStreetViewCount() {
        if (locationComboBox.getSelectionModel().getSelectedItem() == "Växjö")
            availableStreetViewLbl.setText("5");
        else
            availableStreetViewLbl.setText("5");
    }

    private void getAvailableBasicCount() {
        if (locationComboBox.getSelectionModel().getSelectedItem() == "Växjö")
            availableBasicLbl.setText("4");
        else
            availableBasicLbl.setText("5");
    }

    private void getCheckInsTodayCount() {
        int count = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(getTodaysDate());
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE CHECKINDATE=? AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                count++;
            }
            checkInsTodayCount = count;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getCheckOutsTodayCount() {
        int count = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(getTodaysDate());
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE CHECKOUTDATE=? AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                count++;
            }
            checkOutsTodayCount = count;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }
}
