package hotel.views.reservations;

import hotel.database.DbConnect;
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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationsListController implements Initializable {

    ObservableList<Reservation> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Reservation> reservationsTableView;
    @FXML
    private TableColumn<Reservation, String> idColumn;
    @FXML
    private TableColumn<Reservation, String> firstNameColumn;
    @FXML
    private TableColumn<Reservation, String> lastNameColumn;
    @FXML
    private TableColumn<Reservation, String> addressColumn;
    @FXML
    private TableColumn<Reservation, String> sexColumn;
    @FXML
    private TableColumn<Reservation, String> phoneNumberColumn;
    @FXML
    private TableColumn<Reservation, String> creditCardNumberColumn;
    @FXML
    private TableColumn<Reservation, String> passportNumberColumn;
    @FXML
    private TableColumn<Reservation, String> checkInDateColumn;
    @FXML
    private TableColumn<Reservation, String> checkOutDateColumn;
    @FXML
    private TableColumn<Reservation, Boolean> totalDaysColumn;
    @FXML
    private TableColumn<Reservation, Boolean> toPayColumn;
    @FXML
    private TableColumn<Reservation, Boolean> checkedInColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private Stage getStage() {
        return (Stage) reservationsTableView.getScene().getWindow();
    }

    private void initCol() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        creditCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));
        passportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        toPayColumn.setCellValueFactory(new PropertyValueFactory<>("toPay"));
        totalDaysColumn.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        checkedInColumn.setCellValueFactory(new PropertyValueFactory<>("checkedIn"));
    }

    private void loadData() {
        list.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(Room.getLocation());
        try {
            String query = "SELECT * FROM RESERVATIONS WHERE LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                list.add(new Reservation(rs.getString("ID"), rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"), rs.getString("ADDRESS"),
                        rs.getString("SEX"), rs.getString("PHONENUMBER"),
                        rs.getString("CREDITCARDNUMBER"), rs.getString("PASSPORTNUMBER"),
                        rs.getString("ROOMID"), rs.getDate("CHECKINDATE"),
                        rs.getDate("CHECKOUTDATE"), rs.getInt("TOTALDAYS"),
                        rs.getInt("TOPAY"), rs.getString("CHECKEDIN"),
                        rs.getString("LOCATION")));
            }
            reservationsTableView.setItems(list);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }
}
