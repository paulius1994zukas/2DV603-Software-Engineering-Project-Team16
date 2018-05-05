package hotel.views.addAFee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.models.GuestAccount;
import hotel.models.Reservation;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddAFeeController implements Initializable {
    @FXML
    private JFXTextField feeTxtField;
    @FXML
    private JFXTextField descriptionTxtField;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXButton cancelBtn;

    private Room room = new Room();

    private AlertMaker alert = new AlertMaker();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

//    @FXML
//    private void onAddAFeeCntxtBtnClick(ActionEvent event) {
//        DbConnect connection = new DbConnect();
//        ArrayList parameters = new ArrayList();
//        parameters.add(createFirstNameTxtField.getText());
//        parameters.add(createLastNameTxtField.getText());
//        parameters.add(createAddressTxtField.getText());
//        parameters.add(createPhoneNumberTxtField.getText());
//        parameters.add(createCreditCardNumberTxtField.getText());
//        parameters.add(createPassportNumberTxtField.getText());
//        try {
//            String query = "INSERT INTO GUESTS(ID, FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER, CREDITCARDNUMBER, PASSPORTNUMBER) VALUES(NULL,?,?,?,?,?,?)";
//            ResultSet rs = connection.executeWithParameters(query, parameters);
//        } catch (Exception ex) {
//            System.err.println(ex);
//            alert.showErrorMessage(ex);
//        } finally {
//            connection.closeConnection();
//            alert.showSimpleAlert("Guest account was created!",
//                    String.format("User account for %s %s was successfully created!",
//                            createFirstNameTxtField.getText(), createLastNameTxtField.getText()));
//        }
//        clearCreateFields();
//    }
}
