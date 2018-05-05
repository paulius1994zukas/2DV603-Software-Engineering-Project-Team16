package hotel.views.addAFee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.models.Fee;
import hotel.models.Reservation;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddAFeeController implements Initializable {
    ObservableList<Fee> feesList = FXCollections.observableArrayList();
    @FXML
    private TableView<Fee> feesTableView;
    @FXML
    private TableColumn<Fee, Integer> idColumn;
    @FXML
    private TableColumn<Fee, Integer> reservationIdColumn;
    @FXML
    private TableColumn<Fee, Integer> feeColumn;
    @FXML
    private TableColumn<Fee, String> descriptionColumn;
    @FXML
    private JFXTextField feeTxtField;
    @FXML
    private JFXTextField descriptionTxtField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private Text totalFeesLbl;

    private Room room = new Room();

    private AlertMaker alert = new AlertMaker();

    private Reservation reservation = new Reservation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        getFees();
    }

    private void initCol() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void onAddBtnClick(ActionEvent event) {
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(reservation.getReservation().getId());
        parameters.add(feeTxtField.getText());
        parameters.add(descriptionTxtField.getText());
        try {
            String query = "INSERT INTO FEES(ID, RESERVATIONID, FEE, DESCRIPTION) VALUES(NULL,?,?,?)";
            connection.executeWithParameters(query, parameters);
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
            alert.showSimpleAlert("Guest account was created!",
                    String.format("Fee on reservation ID %s was successfully added.", "! RESERVATION ID !"));
            getFees();
        }
        clearFields();
    }

    private void getFees() {
        feesList.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(reservation.getReservation().getId());
        int totalFee = 0;
        try {
            String query = "SELECT * FROM FEES WHERE RESERVATIONID=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                feesList.add(new Fee(rs.getInt("ID"), rs.getInt("RESERVATIONID"),
                        rs.getInt("FEE"), rs.getString("DESCRIPTION")));
                totalFee += rs.getInt("FEE");
            }
            feesTableView.setItems(feesList);
            totalFeesLbl.setText(String.format("Total fees: %s", totalFee));
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void clearFields() {
        feeTxtField.clear();
        descriptionTxtField.clear();
    }
}
