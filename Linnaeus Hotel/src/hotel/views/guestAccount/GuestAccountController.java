package hotel.views.guestAccount;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import hotel.database.DbConnect;
import hotel.helpers.HotelHelper;
import hotel.models.GuestAccount;
import hotel.models.Reservation;
import hotel.views.main.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GuestAccountController implements Initializable {
    ObservableList<GuestAccount> guestAccountsList = FXCollections.observableArrayList();
    ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();

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
    private TableColumn<Reservation, String> phoneNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> creditCardNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> passportNumbercolumn;
    @FXML
    private TableColumn<Reservation, String> checkInDatecolumn;
    @FXML
    private TableColumn<Reservation, String> checkOutDatecolumn;
    @FXML
    private TableColumn<Reservation, Integer> totalDayscolumn;
    @FXML
    private TableColumn<Reservation, String> toPayColumn;
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
    private JFXButton createSubmitBtn;
    @FXML
    private JFXButton createCancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearCreateFields();
        clearSearchFields();
        initCol();
    }

    private void initCol() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNamecolumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNamecolumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addresscolumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        passportNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
        creditCardNumbercolumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));
        checkInDatecolumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDatecolumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalDayscolumn.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        toPayColumn.setCellValueFactory(new PropertyValueFactory<>("toPay"));
    }

    //    @FXML
//    private void handleBookDeleteOption(ActionEvent event) {
//        //Fetch the selected row
//        Book selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
//        if (selectedForDeletion == null) {
////            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
//            return;
//        }
////        if (DatabaseHandler.getInstance().isBookAlreadyIssued(selectedForDeletion)) {
//////            AlertMaker.showErrorMessage("Cant be deleted", "This book is already issued and cant be deleted.");
////            return;
////        }
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Deleting book");
//        alert.setContentText("Are you sure want to delete the book " + selectedForDeletion.getTitle() + " ?");
//        Optional<ButtonType> answer = alert.showAndWait();
//        if (answer.get() == ButtonType.OK) {
//            Boolean result = DatabaseHandler.getInstance().deleteBook(selectedForDeletion);
//            if (result) {
////                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getTitle() + " was deleted successfully.");
//                list.remove(selectedForDeletion);
//            } else {
////                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getTitle() + " could not be deleted");
//            }
//        } else {
////            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
//        }
//    }

    @FXML
    private void onReservationEditClick(ActionEvent event) {
        //Fetch the selected row
        Reservation selectedForEdit = reservationsTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
//            AlertMaker.showErrorMessage("No book selected", "Please select a book for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/ui/addReservation/add_book.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.show();
            HotelHelper.setStageIcon(stage);

            stage.setOnCloseRequest((e) -> {
                handleRefresh(new ActionEvent());
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {

    }

//    @FXML
//    private void exportAsPDF(ActionEvent event) {
//        List<List> printData = new ArrayList<>();
//        String[] headers = {"   Title   ", "ID", "  Author  ", "  Publisher ", "Avail"};
//        printData.add(Arrays.asList(headers));
//        for (Reservation reservation : list) {
//            List<String> row = new ArrayList<>();
//            row.add(reservation.getTitle());
//            row.add(reservation.getId());
//            row.add(reservation.getAuthor());
//            row.add(reservation.getPublisher());
//            row.add(reservation.getAvailabilty());
//            printData.add(row);
//        }
//        HotelHelper.initPDFExprot(rootPane, contentPane, getStage(), printData);
//    }

    @FXML
    private void onSearchBtnClick(ActionEvent event) {
        getGuestDetails();
        getReservations();
    }

    private void getGuestDetails() {
        guestAccountsList.clear();
        clearSearchFields();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(passportNumberTextField.getText());
        System.out.println(passportNumberTextField.getText());
        try {
            String query = "SELECT * FROM GUESTS WHERE PASSPORTNUMBER=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                guestAccountsList.add(new GuestAccount(rs.getString("ID"),
                        rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                        rs.getString("ADDRESS"), rs.getString("PHONENUMBER"),
                        rs.getString("CREDITCARDNUMBER"), rs.getString("PASSPORTNUMBER")));
            }
            System.out.println(guestAccountsList.size());
            firstNameTextField.setText(guestAccountsList.get(0).getFirstName());
            lastNameTextField.setText(guestAccountsList.get(0).getLastName());
            addressTextField.setText(guestAccountsList.get(0).getAddress());
            phoneNumberTextField.setText(guestAccountsList.get(0).getPhoneNumber());
            creditCardNumberTextField.setText(guestAccountsList.get(0).getCreditCardNumber());
        } catch (Exception ex) {
            System.err.println(ex);
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
                        rs.getString("PHONENUMBER"), rs.getString("CREDITCARDNUMBER"),
                        rs.getString("PASSPORTNUMBER"), rs.getDate("CHECKINDATE"),
                        rs.getDate("CHECKOUTDATE"), rs.getInt("TOTALDAYS")));
            }
            reservationsTableView.setItems(reservationsList);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void clearSearchFields() {
        passportNumberTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        phoneNumberTextField.clear();
        creditCardNumberTextField.clear();
    }

    @FXML
    private void onCreateSubmitBtnClick(ActionEvent event) {
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(createFirstNameTxtField.getText());
        parameters.add(createLastNameTxtField.getText());
        parameters.add(createAddressTxtField.getText());
        parameters.add(createPhoneNumberTxtField.getText());
        parameters.add(createCreditCardNumberTxtField.getText());
        parameters.add(createPassportNumberTxtField.getText());
        try {
            String query = "INSERT INTO GUESTS(ID, FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER, CREDITCARDNUMBER, PASSPORTNUMBER) VALUES(NULL,?,?,?,?,?,?)";
            ResultSet rs = connection.executeWithParameters(query, parameters);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
        clearCreateFields();
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

    private void getCheckingInTodayCount(){

    }

    private void getCheckingOutTodayCount(){

    }
}
