package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.bo.Custom.Impl.SupplierBOImpl;
import lk.ijse.blood.bo.Custom.Impl.SupplierOrderBOImpl;
import lk.ijse.blood.bo.Custom.SupplierBO;
import lk.ijse.blood.bo.Custom.SupplierOrderBO;
import lk.ijse.blood.dto.SupplierDto;
import lk.ijse.blood.dto.SupplierOrdersDto;
import lk.ijse.blood.dto.tm.SupplierOrdersTm;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierOrderFormController {
    public ComboBox cmbSupId;
    public DatePicker DtpDate;
    @FXML
    private AnchorPane SupplierOrder;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableView<SupplierOrdersTm> tblSupplierOrder;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtOrderId;

    SupplierOrderBO supplierOrderBO = new SupplierOrderBOImpl();
    SupplierBO supplierBO = new SupplierBOImpl();

    public void initialize() {
        try {
            loadAllSupplierOrders();
            setCellValueFactory();
            autoGenarateOrderId();
            loadAllSuppliers();
            DtpDate.setValue(LocalDate.now());
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("supOrder_id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void loadAllSupplierOrders() throws ClassNotFoundException {
        ObservableList<SupplierOrdersTm> obList = FXCollections.observableArrayList();

        try{
            List<SupplierOrdersDto> dtoList = supplierOrderBO.loadAllSupplierOrders();

            for(SupplierOrdersDto dto : dtoList){
                obList.add(new SupplierOrdersTm(
                        dto.getSupOrder_id(),
                        dto.getSupplier_id(),
                        dto.getDate(),
                        dto.getAmount()

                ));
            }
            tblSupplierOrder.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllSuppliers() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = supplierBO.loadAllSupplier();

            for (SupplierDto supplierDto  : supList) {
                obList.add(supplierDto.getSup_id());
            }
            cmbSupId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String orderIdText = txtOrderId.getText();

        try{
            SupplierOrdersDto dto = supplierOrderBO.searchSupplierOrders(orderIdText);
            if(dto != null) {
                boolean isDeleted = supplierOrderBO.deleteSupplierOrders(orderIdText);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "SupplierOrders Delete Succesfull!!!").show();
                    clearFields();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "SupplierOrders Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String order_id = txtOrderId.getText();
        String supplier_id = String.valueOf(cmbSupId.getValue());
        String date = String.valueOf(DtpDate.getValue());
        String amount = txtAmount.getText();

        var dto = new SupplierOrdersDto(order_id,supplier_id,date,amount);

        try {
            boolean isSaved = supplierOrderBO.saveSupplierOrders(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "SupplierOrder Added Succesfull").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtAmount.setText("");
    }

    private void autoGenarateOrderId() throws SQLException, ClassNotFoundException {
        txtOrderId.setText(supplierOrderBO.generateSupOrder_id());
    }
}




