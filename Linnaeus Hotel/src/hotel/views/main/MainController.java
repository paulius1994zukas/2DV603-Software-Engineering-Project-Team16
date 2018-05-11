package hotel.views.main;

import com.jfoenix.controls.JFXComboBox;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.helpers.HotelHelper;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private AlertMaker alert = new AlertMaker();
    @FXML
    private JFXComboBox locationComboBox;
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
    private JFXComboBox roomSelectionCmbBox;
    @FXML
    private JFXComboBox roomStatusCmbBox;
    @FXML
    private VBox room100VBox;
    @FXML
    private VBox room101VBox;
    @FXML
    private VBox room102VBox;
    @FXML
    private VBox room103VBox;
    @FXML
    private VBox room104VBox;
    @FXML
    private VBox room105VBox;
    @FXML
    private VBox room106VBox;
    @FXML
    private VBox room107VBox;
    @FXML
    private VBox room108VBox;
    @FXML
    private VBox room109VBox;
    @FXML
    private VBox room200VBox;
    @FXML
    private VBox room201VBox;
    @FXML
    private VBox room202VBox;
    @FXML
    private VBox room203VBox;
    @FXML
    private VBox room204VBox;
    @FXML
    private VBox room205VBox;
    @FXML
    private VBox room206VBox;
    @FXML
    private VBox room207VBox;
    @FXML
    private VBox room208VBox;
    @FXML
    private VBox room209VBox;

    private int totalRoomsCount = 20;
    private int occupiedCount = 0;
    private int availableCount = 0;
    private int totalGuestsCount = 0;
    private int maleGuestsCount = 0;
    private int femaleGuestsCount = 0;
    private int checkInsTodayCount = 0;
    private int checkOutsTodayCount = 0;

    Room room = new Room();
    ObservableList<Room> roomsAndStatuses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList locations = FXCollections.observableArrayList();
        locations.add("Kalmar");
        locations.add("Växjö");
        locationComboBox.setItems(locations);
        locationComboBox.getSelectionModel().select(1);
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
        updateMainScreenInfo();
        loadRoomsToComboBox();
        loadStatusesToComboBox();
        getStatusesAndRooms();
        setRoomVBoxStyles();
    }

    private void loadRoomsToComboBox() {
        ObservableList roomsList = FXCollections.observableArrayList();
        roomsList.add("Room100");
        roomsList.add("Room101");
        roomsList.add("Room102");
        roomsList.add("Room103");
        roomsList.add("Room104");
        roomsList.add("Room105");
        roomsList.add("Room106");
        roomsList.add("Room107");
        roomsList.add("Room108");
        roomsList.add("Room109");
        roomsList.add("Room200");
        roomsList.add("Room201");
        roomsList.add("Room202");
        roomsList.add("Room203");
        roomsList.add("Room204");
        roomsList.add("Room205");
        roomsList.add("Room206");
        roomsList.add("Room207");
        roomsList.add("Room208");
        roomsList.add("Room209");
        roomSelectionCmbBox.setItems(roomsList);
    }

    private void loadStatusesToComboBox() {
        ObservableList statusesList = FXCollections.observableArrayList();
        statusesList.add("Available");
        statusesList.add("Occupied");
        statusesList.add("Cleaning");
        statusesList.add("Maintenance");
        roomStatusCmbBox.setItems(statusesList);
    }

    private void setRoomVBoxStyles() {
        for (int i = 0; i < roomsAndStatuses.size(); i++) {
            switch (roomsAndStatuses.get(i).getRoomID()) {
                case "Room100":
                    room100VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room101":
                    room101VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room102":
                    room102VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room103":
                    room103VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room104":
                    room104VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room105":
                    room105VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room106":
                    room106VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room107":
                    room107VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room108":
                    room108VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room109":
                    room109VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room200":
                    room200VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room201":
                    room201VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room202":
                    room202VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room203":
                    room203VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room204":
                    room204VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room205":
                    room205VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room206":
                    room206VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room207":
                    room207VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room208":
                    room208VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                case "Room209":
                    room209VBox.setStyle(getStatusColor(roomsAndStatuses.get(i).getStatus()));
                    break;
                default:
                    System.out.println("Invalid grade");
            }
        }
    }

    private String getStatusColor(String status) {
        String result = "";
        if (status.equals("Available"))
            result = "-fx-background-color: #8A9A5B;";
        if (status.equals("Occupied"))
            result = "-fx-background-color: #CD5C5C;";
        if (status.equals("Cleaning"))
            result = "-fx-background-color: #FADA5E;";
        if (status.equals("Maintenance"))
            result = "-fx-background-color: #007791;";
        return result;
    }

    @FXML
    private void onLocationComboBoxChanged(ActionEvent event) {
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
        updateMainScreenInfo();
        getStatusesAndRooms();
        setRoomVBoxStyles();
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

    @FXML
    private void onLogoutBtnClick(ActionEvent event) {
        closeStage();
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/login/login.fxml"),
                "Login", null, false);
    }

    private void closeStage() {
        ((Stage) locationComboBox.getScene().getWindow()).close();
    }

    @FXML
    private void onStatusUpadteBtnClick(ActionEvent event) {
        updateRoomStatus(roomStatusCmbBox.getSelectionModel().getSelectedItem().toString(),
                roomSelectionCmbBox.getSelectionModel().getSelectedItem().toString(),
                locationComboBox.getSelectionModel().getSelectedItem().toString());
        getStatusesAndRooms();
        setRoomVBoxStyles();
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

    private void updateRoomStatus(String status, String roomID, String location) {
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(status);
        parameters.add(roomID);
        parameters.add(location);
        try {
            String query = "UPDATE ROOMS SET STATUS=? WHERE ID=? AND LOCATION=?";
            connection.executeWithParameters(query, parameters);
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getStatusesAndRooms() {
        roomsAndStatuses.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(locationComboBox.getSelectionModel().getSelectedItem());
        try {
            String query = "SELECT ID, STATUS FROM ROOMS WHERE LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                roomsAndStatuses.add(new Room(rs.getString("ID"), rs.getString("STATUS")));
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }
}
