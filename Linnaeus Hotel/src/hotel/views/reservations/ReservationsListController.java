package hotel.views.reservations;

import hotel.database.DbConnect;
import hotel.models.Reservation;
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
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        creditCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));
        passportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalDaysColumn.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
    }

    private void loadData() {
        list.clear();
        DbConnect connection = new DbConnect();
        try {
            String query = "SELECT * FROM RESERVATIONS";
            ResultSet rs = connection.execute(query);
            while (rs.next()) {
                list.add(new Reservation(rs.getString("ID"), rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"), rs.getString("ADDRESS"),
                        rs.getString("PHONENUMBER"), rs.getString("CREDITCARDNUMBER"),
                        rs.getString("PASSPORTNUMBER"), rs.getDate("CHECKINDATE"),
                        rs.getDate("CHECKOUTDATE"), rs.getInt("TOTALDAYS")));
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
