package hotel.views.main;

import com.jfoenix.controls.JFXComboBox;
import hotel.helpers.HotelHelper;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private String location;
    @FXML
    private JFXComboBox locationComboBox;
    @FXML
    private Label totalRoomsLbl;
    @FXML
    private Label occupiedRoomsLbl;
    @FXML
    private Label availableRoomsLbl;
    @FXML
    private Label availableDoubleDeluxeLbl;
    @FXML
    private Label availableSignleDeluxeLbl;
    @FXML
    private Label availableTwinBedLbl;
    @FXML
    private Label availableDoubleLbl;
    @FXML
    private Label availableSingleLbl;
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

    Room room = new Room();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList locations = FXCollections.observableArrayList();
        locations.add("Kalmar");
        locations.add("Växjö");
        locationComboBox.setItems(locations);
        locationComboBox.getSelectionModel().select(1);
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    private void onLocationComboBoxChanged(ActionEvent event) {
        room.setLocation(locationComboBox.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    private void onGuestAccountBtnClick(ActionEvent event) {
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/guestAccount/guestAccount.fxml"),
                "Guest Account", null, true);
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
}
