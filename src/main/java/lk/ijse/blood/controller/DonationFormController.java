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
import lk.ijse.blood.bo.Custom.DonationBO;
import lk.ijse.blood.bo.Custom.DonorBO;
import lk.ijse.blood.bo.Custom.Impl.DonationBOImpl;
import lk.ijse.blood.bo.Custom.Impl.DonorBOImpl;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.tm.DonationTm;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class DonationFormController {
    public ChoiceBox cmdBloodType;
    public ChoiceBox cmdHemoLevel;
    @FXML
    private TableView<DonationTm> tblDonation;

    @FXML
    private TableColumn<?, ?> colBloodType;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDonationId;

    @FXML
    private TableColumn<?, ?> colDonorId;

    @FXML
    private TableColumn<?, ?> colHemoglobinLevel;
    @FXML
    private TextField txtDoId;

    @FXML
    private DatePicker txtDate;
    @FXML
    private AnchorPane donation;
    @FXML
    private ComboBox cmbDonorid;
    DonationBO donationBO = new DonationBOImpl();
    DonorBO donorBO = new DonorBOImpl();

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) donation.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("donation Form");
        stage.centerOnScreen();
    }

    public void initialize(){
        try {
            loadAllDonations();
            setCellValueFactory();
            loadAllDonors();
            autoGenerateId();
            txtDate.setValue(LocalDate.now());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        cmdBloodType.getItems().addAll("A+","A-","B+","B-","AB+","AB-","O+","O-");
        cmdHemoLevel.getItems().addAll("12","13","14","15","16","17","18");
        cmdBloodType.setValue("O+");
        cmdHemoLevel.setValue("14");
    }

    private void setCellValueFactory() {
        colDonationId.setCellValueFactory(new PropertyValueFactory<>("Do_id"));
        colDonorId.setCellValueFactory(new PropertyValueFactory<>("D_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("Blood_type"));
        colHemoglobinLevel.setCellValueFactory(new PropertyValueFactory<>("Hemoglobin_level"));
    }

    public void loadAllDonations(){
        ObservableList<DonationTm> obList = FXCollections.observableArrayList();
        try {
            List<DonationDto> dtoList = donationBO.loadAllDonation();

            for (DonationDto dto : dtoList) {
                obList.add(new DonationTm(
                        dto.getDo_id(),
                        dto.getD_id(),
                        dto.getDate(),
                        dto.getBlood_type(),
                        dto.getHemoglobin_level()));
            }
            tblDonation.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String do_id = txtDoId.getText();

        try {
            DonationDto dto = donationBO.searchDonation(do_id);
            if (dto != null) {
                boolean isDeleted = donationBO.deleteDonation(do_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Donation Delete Succesfull!!!").show();
                    initialize();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Donation Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(DonationDto dto) {
        txtDoId.setText(dto.getDo_id());
        txtDate.setValue(LocalDate.parse(dto.getDate()));
    }

    @FXML
    void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String do_id = txtDoId.getText();
        String d_id = String.valueOf(cmbDonorid.getValue());
        String date = String.valueOf(txtDate.getValue());
        String blood_type = String.valueOf(cmdBloodType.getValue());
        String hemoglobin_level = String.valueOf(cmdHemoLevel.getValue());

        var dto = new DonationDto(do_id, d_id, date, blood_type, hemoglobin_level);

        try {
            boolean isSaved = donationBO.saveDonation(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donation Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String do_id = txtDoId.getText();
        try {
            DonationDto dto = donationBO.searchDonation(do_id);
            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Donation Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void clearFields() {
        txtDoId.setText("");
    }

    private void loadAllDonors() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DonorDto> donList = donorBO.loadAllDonor();

            for (DonorDto donorDto : donList) {
                obList.add(donorDto.getD_id());
            }
            cmbDonorid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoGenerateId() throws SQLException, ClassNotFoundException {
        txtDoId.setText(donationBO.autogenerateDonationId());
    }
}

