package hotel.views.availableRoomSearch;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.helpers.HotelHelper;
import hotel.models.GuestAccount;
import hotel.models.Room;
import hotel.views.guestAccount.GuestAccountController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvailableRoomSearchController implements Initializable {

    ObservableList<Room> roomsList = FXCollections.observableArrayList();
    ObservableList qualityLevels = FXCollections.observableArrayList();

    @FXML
    private JFXDatePicker checkInDatePicker;
    @FXML
    private JFXDatePicker checkOutDatePicker;
    @FXML
    private JFXComboBox qualityLevelCmbBox;
    @FXML
    private JFXComboBox bedCountCmbBox;
    @FXML
    private JFXComboBox smokingCmbBox;
    @FXML
    private JFXComboBox adjoiningCmbBox;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private TableView<Room> availableRoomsTableView;
    @FXML
    private TableColumn<Room, String> roomcolumn;
    @FXML
    private TableColumn<Room, String> qualitycolumn;
    @FXML
    private TableColumn<Room, String> bedscolumn;
    @FXML
    private TableColumn<Room, String> smokingcolumn;
    @FXML
    private TableColumn<Room, String> adjoiningcolumn;
    @FXML
    private TableColumn<Room, String> priceRatecolumn;

    private String location;
    private Room room = new Room();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        location = Room.getLocation();
        setQualityLevels();
        setBedCount();
        setSmoking();
        setAdjoining();
    }

    private void setQualityLevels() {
        DbConnect connection = new DbConnect();
        try {
            String query = "SELECT * FROM QUALITYLEVELS";
            ResultSet rs = connection.execute(query);
            while (rs.next()) {
                qualityLevels.add(rs.getString(2));
            }
            qualityLevelCmbBox.setItems(qualityLevels);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void setBedCount() {
        ObservableList bedCounts = FXCollections.observableArrayList();
        bedCounts.add("1");
        bedCounts.add("2");
        bedCountCmbBox.setItems(bedCounts);
    }

    private void setSmoking() {
        ObservableList smoking = FXCollections.observableArrayList();
        smoking.add("Yes");
        smoking.add("No");
        smokingCmbBox.setItems(smoking);
    }

    private void setAdjoining() {
        ObservableList adjoining = FXCollections.observableArrayList();
        adjoining.add("Yes");
        adjoining.add("No");
        adjoiningCmbBox.setItems(adjoining);
    }

    private void initCol() {
        roomcolumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        qualitycolumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
        bedscolumn.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        smokingcolumn.setCellValueFactory(new PropertyValueFactory<>("smoking"));
        adjoiningcolumn.setCellValueFactory(new PropertyValueFactory<>("adjoining"));
        priceRatecolumn.setCellValueFactory(new PropertyValueFactory<>("maxRate"));
    }

    private void loadData() {
        roomsList.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(location);
        try {
            String query = "SELECT * FROM ROOMS WHERE LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                roomsList.add(new Room(rs.getString("ID"), rs.getString("QUALITY"),
                        rs.getString("BEDNUMBER"), rs.getString("SMOKING"),
                        rs.getString("ADJOINING"), rs.getInt("MAXRATE")));
            }
            availableRoomsTableView.setItems(roomsList);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void onSearchBtnClick(ActionEvent event) {
        if ((checkInDatePicker.getValue() == null) || (checkOutDatePicker.getValue() == null)) {
            AlertMaker alert = new AlertMaker();
            alert.showSimpleAlert("Oops! Forgot something?", "You have left " +
                    "check in or check out date fields empty. Please choose two dates before " +
                    "proceeding to Search.");
        } else {
            loadData();
            DbConnect connection = new DbConnect();
            ArrayList parameters = new ArrayList();
            ArrayList rooms = new ArrayList();
            parameters.add(checkInDatePicker.getValue());
            parameters.add(checkOutDatePicker.getValue());
            try {
                String query = "SELECT ROOMID FROM RESERVATIONS WHERE CHECKINDATE BETWEEN ? AND ?";
                ResultSet rs = connection.executeWithParameters(query, parameters);
                while (rs.next()) {
                    rooms.add(rs.getString("roomid"));
                }
                rooms.forEach(room -> {
                    roomsList.removeIf(room2 -> room2.getRoomID().equals(room.toString()));
                });
                if (bedCountCmbBox.getSelectionModel().getSelectedItem() != null) {
                    roomsList.removeIf(room -> !room.getBedNumber().equals(bedCountCmbBox.getSelectionModel().getSelectedItem().toString()));
                }
                if (qualityLevelCmbBox.getSelectionModel().getSelectedItem() != null) {
                    roomsList.removeIf(room -> !room.getQuality().equals(qualityLevelCmbBox.getSelectionModel().getSelectedItem().toString()));
                }
                if (smokingCmbBox.getSelectionModel().getSelectedItem() != null) {
                    roomsList.removeIf(room -> !room.getSmoking().equals(smokingCmbBox.getSelectionModel().getSelectedItem().toString()));
                }
                if (adjoiningCmbBox.getSelectionModel().getSelectedItem() != null) {
                    if (adjoiningCmbBox.getSelectionModel().getSelectedItem() == "No")
                        roomsList.removeIf(room -> !room.getAdjoining().equals(adjoiningCmbBox.getSelectionModel().getSelectedItem().toString()));
                    else
                        roomsList.removeIf(room -> room.getAdjoining().equals("No"));
                }
            } catch (Exception ex) {
                System.err.println(ex);
            } finally {
                connection.closeConnection();
            }
        }
    }

    @FXML
    private void onReserveRoomCntxtBtnClick(ActionEvent event) {
        Room selectedRoom = availableRoomsTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            AlertMaker.showErrorMessage("No room selected", "Please select a room to be reserved.");
            return;
        }
        room.setRoom(selectedRoom);
        room.setCheckInDate(checkInDatePicker.getValue());
        room.setCheckOutDate(checkOutDatePicker.getValue());
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/guestAccount/guestAccount.fxml"),
                "Guest Account", null, false);
        Stage stage = (Stage) searchBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void exportAsPDF(ActionEvent event) {
//        List<List> printData = new ArrayList<>();
//        String[] headers = {"   Name    ", "ID", "Mobile", "    Email   "};
//        printData.add(Arrays.asList(headers));
//        for (Member member : list) {
//            List<String> row = new ArrayList<>();
//            row.add(member.getName());
//            row.add(member.getId());
//            row.add(member.getMobile());
//            row.add(member.getEmail());
//            printData.add(row);
//        }
//        HotelHelper.initPDFExprot(rootPane, contentPane, getStage(), printData);
    }
}
