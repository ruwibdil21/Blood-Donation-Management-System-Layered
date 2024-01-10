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
import lk.ijse.blood.bo.Custom.Impl.InventoryBOImpl;
import lk.ijse.blood.bo.Custom.InventoryBO;
import lk.ijse.blood.dto.InventoryDto;
import lk.ijse.blood.dto.tm.InventoryTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InventoryController {
    public TableView tblMedicalInventory;
    public ChoiceBox cmbType;

    @FXML
    private DatePicker txtDate;
    @FXML
    private TableColumn<?, ?> colBloodType;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colMedicalId;

    @FXML
    private AnchorPane invnetory;

    @FXML
    private TableView<InventoryTm> tblInventory;

    @FXML
    private TextField txtMedicalId;

    InventoryBO inventoryBO = new InventoryBOImpl();

    public void initialize() {
        try {
            autoGenerateId();
            loadAllInventories();
            setCellValueFactory();
            txtDate.setValue(LocalDate.now());
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        cmbType.getItems().addAll("A+","A-","B+","B-","AB+","AB-","O+","O-");
        cmbType.setValue("O+");
    }

    private void setCellValueFactory() {
        colMedicalId.setCellValueFactory(new PropertyValueFactory<>("medical_id"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void loadAllInventories() {
        ObservableList<InventoryTm> obList = FXCollections.observableArrayList();
        try {
            List<InventoryDto> dtoList =inventoryBO.loadAllInventory();

            for (InventoryDto dto : dtoList) {
                obList.add(new InventoryTm(
                        dto.getMedical_id(),
                        dto.getDate(),
                        dto.getBloodType()
                ));
            }
            tblMedicalInventory.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) invnetory.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String med_id = txtMedicalId.getText();
        String bloodType = String.valueOf(cmbType.getValue());
        String date = String.valueOf(txtDate.getValue());

        if (med_id.isEmpty() || bloodType.isEmpty() || date.isEmpty()) {
            System.out.println("Please fill all the fields");
        }

        var dto = new InventoryDto(med_id, bloodType,date);

        try {
            boolean isSaved = inventoryBO.saveInventory(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Added Succesfull").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    private void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String medical_id = txtMedicalId.getText();
        String blood_type = String.valueOf(cmbType.getValue());
        String date = String.valueOf(txtDate.getValue());


        if (medical_id.isEmpty() || blood_type.isEmpty() || date.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields").show();
        }

        var dto = new InventoryDto(medical_id, blood_type,date);
        try {
            boolean isUpdated = inventoryBO.updateInventory(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Update Succesfull!!!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void autoGenerateId() throws ClassNotFoundException, SQLException {
        txtMedicalId.setText(inventoryBO.generateInventory());
    }
}

