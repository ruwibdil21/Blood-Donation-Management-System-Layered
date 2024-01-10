package lk.ijse.blood.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.bo.Custom.AdminBO;
import lk.ijse.blood.bo.Custom.Impl.AdminBOImpl;
import lk.ijse.blood.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;

public class UserFormController {
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;
    @FXML
    private AnchorPane user;
    AdminBO adminBO = new AdminBOImpl();

    public void initialize(){
        try {
            autoGenarateId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        if (id.isEmpty() || name.isEmpty() || password.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields").show();
        }

        var dto = new UserDto(id,name,password);

        try {
            boolean isSaved = false;
            try {
                isSaved = adminBO.saveAdmin(dto);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Register Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtUserId.setText("");
        txtPassword.setText("");
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) user.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Register Form");
        stage.centerOnScreen();
    }

    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        adminBO.generateAdminId();
    }
}

