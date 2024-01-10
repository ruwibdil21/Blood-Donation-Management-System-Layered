package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.bo.Custom.Impl.OrderDetailsBOImpl;
import lk.ijse.blood.bo.Custom.Impl.SupplierBOImpl;
import lk.ijse.blood.bo.Custom.Impl.SupplierOrderBOImpl;
import lk.ijse.blood.bo.Custom.OrderDetailsBO;
import lk.ijse.blood.bo.Custom.SupplierBO;
import lk.ijse.blood.bo.Custom.SupplierOrderBO;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.*;
import lk.ijse.blood.dto.tm.OrderDetailsTm;
import lk.ijse.blood.entity.Inventory;
import lk.ijse.blood.entity.OrderDetails;
import lk.ijse.blood.entity.SupplierOrders;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class OrderDetailsController {
    @FXML
    private DatePicker DtpDate;

    @FXML
    private ComboBox<String> cmbSup_id;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBlood_type;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDecription;

    @FXML
    private TableColumn<?, ?> colMed_id;

    @FXML
    private TableColumn<?, ?> colSupOrder_id;

    @FXML
    private TableColumn<?, ?> colSup_id;

    @FXML
    private AnchorPane orderDetails;

    @FXML
    private static TableView<OrderDetailsTm> tblOrder_details;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBlood_type;

    @FXML
    private TextField txtDecription;

    @FXML
    private TextField txtMedid;

    @FXML
    private TextField txtOrderid;

    OrderDetailsBO orderDetailsBO = new OrderDetailsBOImpl();
    SupplierOrderBO supplierOrderBO = new SupplierOrderBOImpl();
    SupplierBO supplierBO = new SupplierBOImpl();

    public void initialize(){
        try {
            //setCellValueFactory();
            //loadAllOrderDetails();
            autoGenarateInventoryId();
            autoGenarateOrderId();
            loadAllSuppliers();
            DtpDate.setValue(LocalDate.now());
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colSupOrder_id.setCellValueFactory(new PropertyValueFactory<>("Order_id"));
        colMed_id.setCellValueFactory(new PropertyValueFactory<>("Med_id"));
        colSup_id.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colBlood_type.setCellValueFactory(new PropertyValueFactory<>("Blood_type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDecription.setCellValueFactory(new PropertyValueFactory<>("Description"));
    }

   private void loadAllOrderDetails() throws SQLException {
        ObservableList<OrderDetailsTm> obList = FXCollections.observableArrayList();
        try {
            List<OrderDetailsDto> dtoList = orderDetailsBO.loadAllOrderDetails();

            for (OrderDetailsDto dto : dtoList) {
                obList.add(new OrderDetailsTm(
                        dto.getOrder_id(),
                        dto.getMed_id(),
                        dto.getDescription()
                ));
            }
            tblOrder_details.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String supOder_id = txtOrderid.getText();
        String med_id = txtMedid.getText();
        String sup_id = String.valueOf(cmbSup_id.getValue());
        String amount = txtAmount.getText();
        String blood_type = txtBlood_type.getText();
        String date = String.valueOf(DtpDate.getValue());
        String description = txtDecription.getText();


        boolean isOrderDetailsValidated = validateOrderDetails();
        if (!isOrderDetailsValidated) {
            return;
        }

        var supplierOrdersdto = new SupplierOrders(supOder_id, sup_id, date, amount);
        var inventorydto = new Inventory(med_id, date, blood_type);
        var orderDetailsdto = new OrderDetails(supOder_id, med_id, description);

        try {
            boolean isSaved = orderDetailsBO.placeOrderDetails(supplierOrdersdto, inventorydto, orderDetailsdto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Details Added Succesfull").show();
                clearFields();
                //initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateOrderDetails() {
        String Blood_type = txtBlood_type.getText();
        boolean isBloodTypeValidated = Pattern.compile("(A|B|AB|O)[+-]").matcher(Blood_type).matches();
        if (!isBloodTypeValidated) {
            txtBlood_type.requestFocus();
        }
        return true;
    }

    private void clearFields() {
        cmbSup_id.getItems().clear();
        txtAmount.setText("");
        txtBlood_type.setText("");
        DtpDate.setValue(null);
        txtDecription.setText("");
    }

    private void loadAllSuppliers() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = supplierBO.loadAllSupplier();
            for (SupplierDto supplierDto : supList) {
                obList.add(supplierDto.getSup_id());
            }
            cmbSup_id.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void autoGenarateOrderId() throws SQLException, ClassNotFoundException {
        txtOrderid.setText(supplierOrderBO.generateSupOrder_id());
    }

    private void autoGenarateInventoryId() throws SQLException, ClassNotFoundException {
        txtMedid.setText(orderDetailsBO.generateOrderDetails());
    }
}