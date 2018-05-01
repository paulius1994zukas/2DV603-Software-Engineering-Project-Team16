package hotel.views.availableRoomSearch;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import hotel.database.DbConnect;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AvailableRoomSearchController implements Initializable {

    ObservableList<Member> list = FXCollections.observableArrayList();

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
    private TableView<Member> availableRoomsTableView;
    @FXML
    private TableColumn<Member, String> roomcolumn;
    @FXML
    private TableColumn<Member, String> qualitycolumn;
    @FXML
    private TableColumn<Member, String> bedscolumn;
    @FXML
    private TableColumn<Member, String> smokingcolumn;
    @FXML
    private TableColumn<Member, String> adjoiningcolumn;
    @FXML
    private TableColumn<Member, String> statuscolumn;
    @FXML
    private TableColumn<Member, String> priceRatecolumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

//    private Stage getStage() {
//        return (Stage) tableView.getScene().getWindow();
//    }

    private void loadData() {
//        list.clear();
//
//        DatabaseHandler handler = DatabaseHandler.getInstance();
//        String qu = "SELECT * FROM MEMBER";
//        ResultSet rs = handler.execQuery(qu);
//        try {
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String mobile = rs.getString("mobile");
//                String id = rs.getString("id");
//                String email = rs.getString("email");
//
//                list.add(new Member(name, id, mobile, email));
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(addReservationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
////        tableView.setItems(list);
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
