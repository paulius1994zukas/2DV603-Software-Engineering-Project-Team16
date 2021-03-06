package hotel.views.guestAccount;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.helpers.HotelHelper;
import hotel.helpers.InvoicePrinter;
import hotel.models.GuestAccount;
import hotel.models.Reservation;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class GuestAccountController implements Initializable {
    ObservableList<GuestAccount> guestAccountsList = FXCollections.observableArrayList();
    ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();
    ObservableList<Room> roomsList = FXCollections.observableArrayList();

    private Reservation reservation = new Reservation();

    @FXML
    private TableView<Reservation> reservationsTableView;
    @FXML
    private TableColumn<Reservation, String> idcolumn;
    @FXML
    private TableColumn<Reservation, String> firstNamecolumn;
    @FXML
    private TableColumn<Reservation, String> lastNamecolumn;
    @FXML
    private TableColumn<Reservation, String> addresscolumn;
    @FXML
    private TableColumn<Reservation, String> sexColumn;
    @FXML
    private TableColumn<Reservation, String> phoneNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> creditCardNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> passportNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> roomIDcolumn;
    @FXML
    private TableColumn<Reservation, String> checkInDatecolumn;
    @FXML
    private TableColumn<Reservation, String> checkOutDatecolumn;
    @FXML
    private TableColumn<Reservation, Integer> totalDayscolumn;
    @FXML
    private TableColumn<Reservation, String> toPayColumn;
    @FXML
    private TableColumn<Reservation, String> checkedInCollumn;
    @FXML
    private TableColumn<Reservation, String> locationCollumn;
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
    @FXML
    private JFXTextField passportNumberTextField;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXTextField firstNameTextField;
    @FXML
    private JFXTextField lastNameTextField;
    @FXML
    private JFXTextField addressTextField;
    @FXML
    private JFXTextField phoneNumberTextField;
    @FXML
    private JFXTextField creditCardNumberTextField;
    @FXML
    private JFXTextField createFirstNameTxtField;
    @FXML
    private JFXTextField createLastNameTxtField;
    @FXML
    private JFXTextField createAddressTxtField;
    @FXML
    private JFXTextField createPhoneNumberTxtField;
    @FXML
    private JFXTextField createCreditCardNumberTxtField;
    @FXML
    private JFXTextField createPassportNumberTxtField;
    @FXML
    private JFXComboBox createSexCmbBox;
    @FXML
    private JFXButton createCancelBtn;
    @FXML
    private VBox currenctlyBookingVBox;
    @FXML
    private JFXComboBox sexCmbBox;
    @FXML
    private GridPane mainGridPane;

    private Room room = new Room();

    private AlertMaker alert = new AlertMaker();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearCreateFields();
        clearSearchFields();
        initCol();
        if (Room.getRoom() != null) {
            onRoomReservation();
        } else
            currenctlyBookingVBox.setManaged(false);
        setSexCmbBox();
    }

    private void setSexCmbBox() {
        ObservableList sex = FXCollections.observableArrayList();
        sex.add("Male");
        sex.add("Female");
        sex.add("Not Specified");
        sexCmbBox.setItems(sex);
        createSexCmbBox.setItems(sex);
    }

    private void onRoomReservation() {
        currenctlyBookingVBox.setVisible(true);
        currenctlyBookingVBox.setManaged(true);
        room = Room.getRoom();
        roomsList.add(room);
        availableRoomsTableView.setItems(roomsList);
    }

    private void initCol() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNamecolumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNamecolumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addresscolumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phoneNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        passportNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
        roomIDcolumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        creditCardNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));
        checkInDatecolumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDatecolumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalDayscolumn.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        toPayColumn.setCellValueFactory(new PropertyValueFactory<>("toPay"));
        checkedInCollumn.setCellValueFactory(new PropertyValueFactory<>("checkedIn"));
        locationCollumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        roomcolumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        qualitycolumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
        bedscolumn.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        smokingcolumn.setCellValueFactory(new PropertyValueFactory<>("smoking"));
        adjoiningcolumn.setCellValueFactory(new PropertyValueFactory<>("adjoining"));
        priceRatecolumn.setCellValueFactory(new PropertyValueFactory<>("maxRate"));
    }

    @FXML
    private void onCheckInCntxtBtnClick(ActionEvent event) {
        checkInOut(true);
    }

    @FXML
    private void onCheckOutCntxtBtnClick(ActionEvent event) {
        if (LocalTime.now().isAfter(LocalTime.parse("11:30"))) {
            Reservation selectedReservation = reservationsTableView.getSelectionModel().getSelectedItem();
            reservation.setReservation(selectedReservation);
            addLateCheckOutFee();
        }
        checkInOut(false);
        alert.showSimpleAlert("Invoice is Printing",
                String.format("%s %s is going to be checked out and an invoice printed!",
                        firstNameTextField.getText(), lastNameTextField.getText()));
        printBill(true);
    }

    private void addLateCheckOutFee(){
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(reservation.getReservation().getId());
        parameters.add("500");
        parameters.add("Late checkout (after 11:30) fee");
        try {
            String query = "INSERT INTO FEES(ID, RESERVATIONID, FEE, DESCRIPTION) VALUES(NULL,?,?,?)";
            connection.executeWithParameters(query, parameters);
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
            alert.showSimpleAlert("Guest account was created!",
                    String.format("Fee on reservation ID %s was successfully added.", reservation.getReservation().getId()));
        }
    }

    private void checkInOut(boolean yesOrNo) {
        Reservation selectedForEdit = reservationsTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No reservation selected", "Please select a reservation to be deleted.");
            return;
        }
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(selectedForEdit.getId());
        String query = "";
        try {
            if (yesOrNo) {
                query = "UPDATE RESERVATIONS SET CHECKEDIN = 'Yes' WHERE ID = ?";
            } else {
                query = "UPDATE RESERVATIONS SET CHECKEDIN = 'No' WHERE ID = ?";
            }
            connection.executeWithParameters(query, parameters);
            getReservations();
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void onAddCntxtBtnClick(ActionEvent event) {
        Stage stage = (Stage) createCancelBtn.getScene().getWindow();
        stage.close();
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/availableRoomSearch/availableRoomSearch.fxml"),
                "Available Rooms", null, false);
    }

    @FXML
    private void onDeleteCntxtBtnClick(ActionEvent event) {
        Reservation selectedForEdit = reservationsTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No reservation selected", "Please select a reservation to be deleted.");
            return;
        }
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(selectedForEdit.getId());
        try {
            String query = "DELETE FROM RESERVATIONS WHERE ID=?";
            connection.executeWithParameters(query, parameters);
            getReservations();
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void onManageFeesCntxtBtnClick(ActionEvent event) {
        Reservation selectedReservation = reservationsTableView.getSelectionModel().getSelectedItem();
        reservation.setReservation(selectedReservation);
        HotelHelper.loadWindow(getClass().getResource("/hotel/views/addAFee/addAFee.fxml"),
                "Add a Fee", null, false);
    }

    @FXML
    private void onPrintBillCntxtBtnClick(ActionEvent event) {
        printBill(false);
    }

    private void printBill(boolean onCheckout) {
        Stage stage = (Stage) searchBtn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PDF");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File saveLoc = fileChooser.showSaveDialog(stage);
        if (saveLoc != null) {
            InvoicePrinter generateInvoice = new InvoicePrinter();
            if(!onCheckout) {
                Reservation reservation = new Reservation();
                reservation.setReservation(reservationsTableView.getSelectionModel().getSelectedItem());
            }
            generateInvoice.createPDF(saveLoc.toString());
            alert.showSimpleAlert("Invoice Printed!",
                    String.format("Your invoice has been successfully printed and saved at selected location."));
        }
    }

    @FXML
    private void onSearchBtnClick(ActionEvent event) {
        getGuestDetails();
        getReservations();
    }

    private void getGuestDetails() {
        guestAccountsList.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(passportNumberTextField.getText());
        try {
            String query = "SELECT * FROM GUESTS WHERE PASSPORTNUMBER=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                guestAccountsList.add(new GuestAccount(rs.getString("ID"),
                        rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                        rs.getString("ADDRESS"), rs.getString("SEX"),
                        rs.getString("PHONENUMBER"), rs.getString("CREDITCARDNUMBER"),
                        rs.getString("PASSPORTNUMBER")));
            }
            if (guestAccountsList.isEmpty()) {
                alert.showSimpleAlert("No guest account!",
                        String.format("Guest account for passport number %s does not exist!",
                                passportNumberTextField.getText()));
            } else {
                firstNameTextField.setText(guestAccountsList.get(0).getFirstName());
                lastNameTextField.setText(guestAccountsList.get(0).getLastName());
                addressTextField.setText(guestAccountsList.get(0).getAddress());
                sexCmbBox.getSelectionModel().select(guestAccountsList.get(0).getSex());
                phoneNumberTextField.setText(guestAccountsList.get(0).getPhoneNumber());
                creditCardNumberTextField.setText(guestAccountsList.get(0).getCreditCardNumber());
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getReservations() {
        reservationsList.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(passportNumberTextField.getText());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE PASSPORTNUMBER=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                reservationsList.add(new Reservation(rs.getString("ID"), rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"), rs.getString("ADDRESS"),
                        rs.getString("SEX"), rs.getString("PHONENUMBER"),
                        rs.getString("CREDITCARDNUMBER"), rs.getString("PASSPORTNUMBER"),
                        rs.getString("ROOMID"), rs.getDate("CHECKINDATE"),
                        rs.getDate("CHECKOUTDATE"), rs.getInt("TOTALDAYS"), rs.getInt("TOPAY"),
                        rs.getString("CHECKEDIN"), rs.getString("LOCATION")));
            }
            reservationsTableView.setItems(reservationsList);
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void onCreateSubmitBtnClick(ActionEvent event) {
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(createFirstNameTxtField.getText());
        parameters.add(createLastNameTxtField.getText());
        parameters.add(createAddressTxtField.getText());
        parameters.add(createSexCmbBox.getSelectionModel().getSelectedItem());
        parameters.add(createPhoneNumberTxtField.getText());
        parameters.add(createCreditCardNumberTxtField.getText());
        parameters.add(createPassportNumberTxtField.getText());
        try {
            if (createFirstNameTxtField.getText().isEmpty() || createLastNameTxtField.getText().isEmpty() ||
                    createAddressTxtField.getText().isEmpty() ||
                    createPhoneNumberTxtField.getText().isEmpty() || createCreditCardNumberTxtField.getText().isEmpty()
                    || createPassportNumberTxtField.getText().isEmpty()) {
                alert.showSimpleAlert("Oops, you have left something out!", "In order to create a " +
                        "new guest account all the fields above must contain value. Please do not leave any fields blank!");
            } else {
                if (!checkIfPassportNumberExists()) {
                    String query = "INSERT INTO GUESTS(ID, FIRSTNAME, LASTNAME, ADDRESS, SEX, PHONENUMBER, CREDITCARDNUMBER, PASSPORTNUMBER) VALUES(NULL,?,?,?,?,?,?,?)";
                    connection.executeWithParameters(query, parameters);
                    connection.closeConnection();
                    alert.showSimpleAlert("Guest account was created!",
                            String.format("User account for %s %s was successfully created!",
                                    createFirstNameTxtField.getText(), createLastNameTxtField.getText()));
                    clearCreateFields();
                } else {
                    alert.showSimpleAlert("Something went wrong!",
                            String.format("Passport numbers must be unique! Passport number %s already exists!",
                                    passportNumberTextField.getText()));
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {

        }
    }

    private boolean checkIfPassportNumberExists() {
        int counter = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(createPassportNumberTxtField.getText());
        try {
            String query = "SELECT PASSPORTNUMBER FROM GUESTS WHERE PASSPORTNUMBER=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                counter++;
            }
            return true;
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {

        }
        return false;
    }

    @FXML
    private void onBookRoomBtnClick(ActionEvent event) {
        if (passportNumberTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                phoneNumberTextField.getText().isEmpty() || creditCardNumberTextField.getText().isEmpty()) {
            alert.showSimpleAlert("Oops, you have left something out!", "In order to add a " +
                    "new reservation all the fields above must contain value. Please do not leave any fields blank!");
        } else {
            DbConnect connection = new DbConnect();
            ArrayList parameters = new ArrayList();
            parameters.add(firstNameTextField.getText());
            parameters.add(lastNameTextField.getText());
            parameters.add(addressTextField.getText());
            parameters.add(sexCmbBox.getSelectionModel().getSelectedItem());
            parameters.add(phoneNumberTextField.getText());
            parameters.add(creditCardNumberTextField.getText());
            parameters.add(passportNumberTextField.getText());
            parameters.add(roomsList.get(0).getRoomID());
            parameters.add(Room.getLocation());
            parameters.add(Room.getCheckInDate());
            parameters.add(Room.getCheckOutDate());
            parameters.add(java.time.temporal.ChronoUnit.DAYS.between(Room.getCheckInDate(), Room.getCheckOutDate()));
            parameters.add(Room.getRoom().getMaxRate() * java.time.temporal.ChronoUnit.DAYS.between(Room.getCheckInDate(), Room.getCheckOutDate()));
            parameters.add("No");
            try {
                String query = "INSERT INTO RESERVATIONS(ID, FIRSTNAME, LASTNAME, ADDRESS, SEX, PHONENUMBER, CREDITCARDNUMBER, PASSPORTNUMBER, ROOMID, LOCATION, CHECKINDATE, CHECKOUTDATE, TOTALDAYS, TOPAY, CHECKEDIN) VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                connection.executeWithParameters(query, parameters);
            } catch (Exception ex) {
                System.err.println(ex);
                alert.showErrorMessage(ex);
            } finally {
                connection.closeConnection();
                alert.showSimpleAlert("Reservation successful!",
                        String.format("New reservation for guest %s %s was successfully added!",
                                firstNameTextField.getText(), lastNameTextField.getText()));
                room.setRoom(null);
            }
            getReservations();
            roomsList.clear();
            currenctlyBookingVBox.setManaged(false);
            currenctlyBookingVBox.setVisible(false);
        }
    }

    @FXML
    private void onCreateCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) createCancelBtn.getScene().getWindow();
        stage.close();
    }

    private void clearCreateFields() {
        createFirstNameTxtField.clear();
        createLastNameTxtField.clear();
        createAddressTxtField.clear();
        createPhoneNumberTxtField.clear();
        createCreditCardNumberTxtField.clear();
        createPassportNumberTxtField.clear();
    }

    private void clearSearchFields() {
        passportNumberTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        phoneNumberTextField.clear();
        creditCardNumberTextField.clear();
    }
}
