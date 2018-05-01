package hotel.views.availableRoomSearch;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import hotel.database.DbConnect;
import hotel.models.Reservation;
import hotel.models.Room;
import hotel.views.main.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AvailableRoomSearchController implements Initializable {

    ObservableList<Room> roomsList = FXCollections.observableArrayList();

    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox qualityLevelCmbBox;
    @FXML
    private JFXComboBox bedCountCmbBox;
    @FXML
    private JFXCheckBox smokingChckBox;
    @FXML
    private JFXCheckBox adjoiningChckBox;
    @FXML
    private JFXCheckBox cleanChckBox;
    @FXML
    private JFXCheckBox dirtyChckBox;
    @FXML
    private JFXCheckBox inspectedChckBox;
    @FXML
    private JFXCheckBox outOfOrderChckBox;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXButton roomPlanBtn;
    @FXML
    private JFXButton reserveBtn;
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
    private TableColumn<Room, String> statuscolumn;
    @FXML
    private TableColumn<Room, String> priceRatecolumn;

    private String location;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        location = Room.getLocation();
        System.out.println(location);
        loadData();
        setQualityLevels();
        setBedCount();
    }

    private void setQualityLevels(){
        ObservableList qualityLevels = FXCollections.observableArrayList();
        qualityLevels.add("1");
        qualityLevels.add("2");
        qualityLevels.add("3");
        qualityLevels.add("4");
        qualityLevels.add("5");
        qualityLevelCmbBox.setItems(qualityLevels);
    }

    private void setBedCount(){
        ObservableList bedCounts = FXCollections.observableArrayList();
        bedCounts.add("1");
        bedCounts.add("2");
        bedCountCmbBox.setItems(bedCounts);
    }

    private void initCol() {
        roomcolumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        qualitycolumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
        bedscolumn.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        smokingcolumn.setCellValueFactory(new PropertyValueFactory<>("smoking"));
        adjoiningcolumn.setCellValueFactory(new PropertyValueFactory<>("adjoining"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        priceRatecolumn.setCellValueFactory(new PropertyValueFactory<>("maxRate"));
    }

//    private Stage getStage() {
//        return (Stage) tableView.getScene().getWindow();
//    }

    private void loadData() {
        roomsList.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(location);
        try {
            String query = "SELECT * FROM ROOMS WHERE LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                roomsList.add(new Room(rs.getString("ID"), rs.getInt("QUALITY"),
                        rs.getInt("BEDNUMBER"), rs.getString("SMOKING"),
                        rs.getString("ADJOINING"), rs.getString("STATUS"),
                        rs.getInt("MAXRATE")));
            }
            availableRoomsTableView.setItems(roomsList);
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void handleMemberDelete(ActionEvent event) {
//        //Fetch the selected row
//        AvailableRoomSearchController.Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
//        if (selectedForDeletion == null) {
//            AlertMaker.showErrorMessage("No member selected", "Please select a member for deletion.");
//            return;
//        }
//        if (DatabaseHandler.getInstance().isMemberHasAnyBooks(selectedForDeletion)) {
//            AlertMaker.showErrorMessage("Cant be deleted", "This member has some books.");
//            return;
//        }
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Deleting book");
//        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getName() + " ?");
//        Optional<ButtonType> answer = alert.showAndWait();
//        if (answer.get() == ButtonType.OK) {
//            Boolean result = DatabaseHandler.getInstance().deleteMember(selectedForDeletion);
//            if (result) {
//                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getName() + " was deleted successfully.");
//                list.remove(selectedForDeletion);
//            } else {
//                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getName() + " could not be deleted");
//            }
//        } else {
//            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
//        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void handleMemberEdit(ActionEvent event) {
//        //Fetch the selected row
//        Member selectedForEdit = tableView.getSelectionModel().getSelectedItem();
//        if (selectedForEdit == null) {
//            AlertMaker.showErrorMessage("No member selected", "Please select a member for edit.");
//            return;
//        }
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/ui/guestAccount/guestAccount.fxml"));
//            Parent parent = loader.load();
//
//            GuestAccountController controller = (GuestAccountController) loader.getController();
//            controller.infalteUI(selectedForEdit);
//
//            Stage stage = new Stage(StageStyle.DECORATED);
//            stage.setTitle("Edit Member");
//            stage.setScene(new Scene(parent));
//            stage.show();
//            HotelHelper.setStageIcon(stage);
//
//            stage.setOnCloseRequest((e) -> {
//                handleRefresh(new ActionEvent());
//            });
//
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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

    @FXML
    private void closeStage(ActionEvent event) {
//        getStage().close();
    }

    public static class Member {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        public Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

    }

    private void exampleOfSQL(){
        DbConnect connection = new DbConnect();
        ResultSet rs;
        try {
            rs = connection.execute("SELECT * FROM QUALITYLEVELS");
            while (rs.next()) {
                System.out.println(rs.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        } finally {
            connection.closeConnection();
        }
    }

}
