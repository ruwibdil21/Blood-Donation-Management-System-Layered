package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.bo.Custom.BloodInventoryBO;
import lk.ijse.blood.bo.Custom.DonationBO;
import lk.ijse.blood.bo.Custom.Impl.BloodInventoryBOImpl;
import lk.ijse.blood.bo.Custom.Impl.DonationBOImpl;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.BloodInventoryDto;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.tm.BloodInventoryTm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class BloodInventoryFormController {
    public AnchorPane inventoryPane;
    public TextField txtBloodBag_id;
    public DatePicker txtDonation_Date;
    public DatePicker txtEx_Date;
    public TableView tblBlood_Inventory;
    public TableColumn colBloodBag_id;
    public TableColumn colDonation_id;
    public TableColumn colEx_Date;
    public TableColumn colBlood_Type;
    public ChoiceBox cmbType;
    public ComboBox cmbDId;
    @FXML
    private TableColumn<?, ?> colDonationDate;

    BloodInventoryBO bloodInventoryBO = new BloodInventoryBOImpl();
    DonationBO donationBO = new DonationBOImpl();

    public void initialize() {
        try {
            loadAllBloodInvenorys();
            setCellValueFactory();
            autoGenarateId();
            loadAllDonation();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        cmbType.getItems().addAll("A+","A-","B+","B-","AB+","AB-","O+","O-");
        cmbType.setValue("O+");
        txtDonation_Date.setValue(LocalDate.now());
    }
    private void setCellValueFactory() {
        colBloodBag_id.setCellValueFactory(new PropertyValueFactory<>("bloodBagId"));
        colDonation_id.setCellValueFactory(new PropertyValueFactory<>("donation_id"));
        colDonationDate.setCellValueFactory(new PropertyValueFactory<>("donation_date"));
        colEx_Date.setCellValueFactory(new PropertyValueFactory<>("ex_date"));
        colBlood_Type.setCellValueFactory(new PropertyValueFactory<>("blood_type"));
    }

    private void loadAllDonation() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DonationDto> doList = donationBO.loadAllDonation();

            for (DonationDto donationDto  : doList) {
                obList.add(donationDto.getDo_id());
            }
            cmbDId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllBloodInvenorys() throws ClassNotFoundException {
        ObservableList<BloodInventoryTm> obList = FXCollections.observableArrayList();
        try{
            List<BloodInventoryDto> dtoList = bloodInventoryBO.loadAllBloodInventoy();

            for(BloodInventoryDto dto : dtoList){
                obList.add(new BloodInventoryTm(
                        dto.getBloodBagId(),
                        dto.getDonation_id(),
                        dto.getDonation_date(),
                        dto.getEx_date(),
                        dto.getBlood_type()

                ));
            }
            tblBlood_Inventory.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
   public void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String bloodBag_id = txtBloodBag_id.getText();

        try{
            BloodInventoryDto dto = bloodInventoryBO.searchBloodInventory(bloodBag_id);
            if(dto != null) {
                boolean isDeleted = bloodInventoryBO.deleteBloodInventory(bloodBag_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Blood Inventory Delete Succesfull!!!").show();
                    initialize();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Blood Inventory Not Found!!!").show();
                initialize();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String bloodBagId = txtBloodBag_id.getText();
        String donationId = String.valueOf(cmbDId.getValue());
        String donationDate = String.valueOf(txtDonation_Date.getValue());
        String exDate = String.valueOf(txtEx_Date.getValue());
        String bloodType = String.valueOf(cmbType.getValue());
        
        var dto = new BloodInventoryDto(bloodBagId,donationId,donationDate,exDate,bloodType);

        try {
            boolean isSaved = bloodInventoryBO.saveBloodInventory(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "BloodInventory Added Succesfull").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    public void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String bloodBagId = colBloodBag_id.getText();
        String donationId = String.valueOf(cmbDId.getValue());
        String donationDate = String.valueOf(txtDonation_Date.getValue());
        String exDate = String.valueOf(txtEx_Date.getValue());
        String bloodType = String.valueOf(cmbType.getValue());;

        var dto = new BloodInventoryDto(bloodBagId,donationId,donationDate,exDate,bloodType);

        try {
            boolean isUpdated = bloodInventoryBO.updateBloodInventoy(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Blood Inventory Update Succesfull!!!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtBloodBag_id.setText(bloodInventoryBO.generateBloodBagId());
    }
}



