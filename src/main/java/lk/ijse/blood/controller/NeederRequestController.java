package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.bo.Custom.BloodInventoryBO;
import lk.ijse.blood.bo.Custom.DonationBO;
import lk.ijse.blood.bo.Custom.Impl.BloodInventoryBOImpl;
import lk.ijse.blood.bo.Custom.Impl.DonationBOImpl;
import lk.ijse.blood.bo.Custom.Impl.NeederBOImpl;
import lk.ijse.blood.bo.Custom.Impl.NeederRequestBOImpl;
import lk.ijse.blood.bo.Custom.NeederBO;
import lk.ijse.blood.bo.Custom.NeederRequestBO;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.*;
import lk.ijse.blood.entity.BloodInventory;
import lk.ijse.blood.entity.NeederRequest;
import lk.ijse.blood.entity.RequestDetails;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class NeederRequestController {
    public DatePicker dtpDate;
    public ComboBox cmbNeederid;
    public ComboBox cmbDonationid;
    public DatePicker dtpExdate;
    public ChoiceBox cmbType;
    public TextField txtBbid;

    @FXML
    private AnchorPane neederRequest;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtNeeReq;

    NeederRequestBO neederRequestBO= new NeederRequestBOImpl();
    NeederBO neederBO = new NeederBOImpl();
    DonationBO donationBO = new DonationBOImpl();
    BloodInventoryBO bloodInventoryBO = new BloodInventoryBOImpl();

    public void initialize(){
        try {
            autoGenerateRequestId();
            autoGenerateBloodBagId();
            loadAllNeeder();
            loadAllDonation();
            dtpDate.setValue(LocalDate.now());
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        cmbType.getItems().addAll("A+","A-","B+","B-","AB+","AB-","O+","O-");
        cmbType.setValue("O+");
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String neeReq = txtNeeReq.getText();
        String blood_bag_id = txtBbid.getText();
        String needer_id = String.valueOf(cmbNeederid.getValue());
        String date = String.valueOf(dtpDate.getValue());
        String donation_id = String.valueOf(cmbDonationid.getValue());
        String amount = txtAmount.getText();
        String type = String.valueOf(cmbType.getValue());
        String exdate = String.valueOf(dtpExdate.getValue());

        boolean isNeederRequestValidated  = validateNeederRequest();
        if (!isNeederRequestValidated){return;}

        var neederrequestdto = new NeederRequest(neeReq,needer_id,date,amount);
        var bagdto = new BloodInventory(blood_bag_id, donation_id,date,exdate,type);
        var requestdto = new RequestDetails(neeReq, blood_bag_id,type);

        try {
            boolean isSaved = neederRequestBO.placeNeederRequest(neederrequestdto,bagdto,requestdto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Needer Request Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtNeeReq.setText("");
        txtBbid.setText("");
        cmbType.getItems().clear();
        cmbNeederid.getItems().clear();
        cmbDonationid.getItems().clear();
        dtpDate.setValue(null);
        txtAmount.setText("");
        dtpExdate.setValue(null);
    }

    private void loadAllNeeder() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<NeederDto> needList = neederBO.loadAllNeeder();

            for (NeederDto neederDto  : needList) {
                obList.add(neederDto.getNeederId());
            }
            cmbNeederid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllDonation() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DonationDto> donList = donationBO.loadAllDonation();

            for (DonationDto donationDto : donList) {
                obList.add(donationDto.getDo_id());
            }
            cmbDonationid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateNeederRequest() {
        String amount = txtAmount.getText();
        boolean isAmountValidated = Pattern.compile("/d").matcher(amount).matches();
        if (!isAmountValidated) {
            txtAmount.requestFocus();
        }
        return true;
    }

    private void autoGenerateRequestId() throws SQLException, ClassNotFoundException {
       txtNeeReq.setText(neederRequestBO.generateNeedrRequwst());
    }

    private void autoGenerateBloodBagId() throws SQLException, ClassNotFoundException {
        txtBbid.setText(bloodInventoryBO.generateBloodBagId());
    }
}


