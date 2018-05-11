package hotel.views.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import hotel.database.DbConnect;
import hotel.helpers.AlertMaker;
import hotel.helpers.HotelHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordTextField;

    AlertMaker alert = new AlertMaker();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onLoginBtnClick(ActionEvent event) {
        int userCount = 0;
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(usernameTextField.getText());
        parameters.add(passwordTextField.getText());
        try {
            String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                userCount++;
            }
            if (userCount == 0) {
                alert.showSimpleAlert("Something went wrong!", String.format("User was not found or password was incorrect!"));
            } else {
                loadMain();
                closeStage();
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    @FXML
    private void onCancelBtnClick(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) usernameTextField.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/hotel/views/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Linnaeus Hotel");
            stage.setScene(new Scene(parent));
            stage.setMaximized(true);
            stage.show();
            HotelHelper.setStageIcon(stage);
        } catch (IOException ex) {

        }
    }
}
