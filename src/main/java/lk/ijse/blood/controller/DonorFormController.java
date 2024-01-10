package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.bo.Custom.DonorBO;
import lk.ijse.blood.bo.Custom.Impl.DonorBOImpl;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.tm.DonorTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class DonorFormController {
    public ChoiceBox cmdBloodType;
    @FXML
    private DatePicker txtL_date;

    @FXML
    private DatePicker txtDob;

    @FXML
    private TableView<DonorTm> tblDonor;
    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colDonorid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private AnchorPane donor;


    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtTel;

    DonorBO donorBO = new DonorBOImpl();

    public void initialize(){
        try {
            setCellValueFactory();
            loadAllDonors();
            autoGenarateId();
            cmdBloodType.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
            cmdBloodType.setValue("O+");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) donor.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    private void setCellValueFactory() {
        colDonorid.setCellValueFactory(new PropertyValueFactory<>("D_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("F_name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colType.setCellValueFactory(new PropertyValueFactory<>("blood_type"));
    }

    private void loadAllDonors() throws ClassNotFoundException {
        ObservableList<DonorTm> obList = FXCollections.observableArrayList();

        try {
            List<DonorDto> dtoList = donorBO.loadAllDonor();

            for (DonorDto dto : dtoList) {
                obList.add(new DonorTm(
                        dto.getD_id(),
                        dto.getFirstName(),
                        dto.getDob(),
                        dto.getType()
                ));
            }
            tblDonor.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String d_id = txtId.getText();

        try {
            DonorDto dto = donorBO.searchDonor(d_id);
            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Donor Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(DonorDto dto) {
        txtId.setText(dto.getD_id());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtTel.setText(String.valueOf(dto.getTel()));
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String d_id = txtId.getText();

        try {
            DonorDto dto = donorBO.searchDonor(d_id);
            if (dto != null) {
                boolean isDeleted = donorBO.deleteDonor(d_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Donor Delete Succesfull!!!").show();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Donor Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String d_id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String dob = String.valueOf(txtDob.getValue());
        String type = String.valueOf(cmdBloodType.getValue());
        String l_date = String.valueOf(txtL_date.getValue());

        boolean isDonorValidated = validateDonor();
        if (!isDonorValidated){return;}

        var dto = new DonorDto(d_id, firstName, lastName, dob, type,tel, l_date);

        try {
            boolean isUpdated = donorBO.updateDonor(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donor Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String d_id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = String.valueOf(txtDob.getValue());
        int tel = Integer.parseInt(txtTel.getText());
        String type = String.valueOf(cmdBloodType.getValue());
        String l_date = String.valueOf(txtL_date.getValue());

        boolean isDonorValidated = validateDonor();
        if (!isDonorValidated){return;}

        var dto = new DonorDto(d_id, firstName, lastName, dob, type,tel, l_date);

        try {
            boolean isSaved = donorBO.saveDonor(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donor Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtDob.setValue(null);
        txtTel.setText("");
        cmdBloodType.setValue(null);
        txtL_date.setValue(null);
    }

    private boolean validateDonor() {
        String FirstName =txtFirstName.getText();
        boolean isFirstNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(FirstName).matches();
        if (!isFirstNameValidated){
            txtFirstName.requestFocus();
        }

        String LastName = txtLastName.getText();
        boolean isLastNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(LastName).matches();
        if (!isLastNameValidated){
            txtLastName.requestFocus();
        }

        String tel = txtTel.getText();
        boolean isTelValidated = Pattern.compile("^[0-9]{10}$").matcher(tel).matches();
        if (!isTelValidated) {
            txtTel.requestFocus();
        }
        return true;
    }

    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(donorBO.generateDId());
    }
}
