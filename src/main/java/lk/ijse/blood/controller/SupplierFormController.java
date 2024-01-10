package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.bo.Custom.AdminBO;
import lk.ijse.blood.bo.Custom.Impl.AdminBOImpl;
import lk.ijse.blood.bo.Custom.Impl.SupplierBOImpl;
import lk.ijse.blood.bo.Custom.SupplierBO;
import lk.ijse.blood.dto.SupplierDto;
import lk.ijse.blood.dto.UserDto;
import lk.ijse.blood.dto.tm.SupplierTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {
    public ComboBox cmbUserid;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtTel;

    SupplierBO supplierBO = new SupplierBOImpl();
    AdminBO adminBO = new AdminBOImpl();
    public void initialize() throws SQLException, ClassNotFoundException {
        try {
            loadAllSuppliers();
            setCellValueFactory();
            loadAllUsers();
            autoGenerateId();
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) supplierPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("Sup_id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("User_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));

    }

    public void loadAllSuppliers() throws ClassNotFoundException {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = supplierBO.loadAllSupplier();

            for (SupplierDto dto : dtoList) {
                obList.add(new SupplierTm(
                        dto.getSup_id(),
                        dto.getUser_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getTel()


                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String supId = txtSupId.getText();

        try {
            boolean isDeleted = supplierBO.deleteSupplier(supId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Succesfull").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnMedicleInventoryOnAction(ActionEvent event) {
        try {
            URL resource = BloodInventoryFormController.class.getResource("/view/blood_Inventory_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Blood inventory Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnOrderDetailOnAction(ActionEvent event) {
        try {
            URL resource = OrderDetailsController.class.getResource("/view/order_details.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("OrderDetails Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String supId = txtSupId.getText();
        String userId = String.valueOf(cmbUserid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        boolean isSupplierValidated = validateSupplier();
        if (!isSupplierValidated){return;}

        var dto = new SupplierDto(supId, userId, name, address, tel);

        try {
            boolean isSaved = supplierBO.saveSupplier(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSupId.setText("");
        cmbUserid.setValue("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnSupOrderOnAction(ActionEvent event) {
        try {
            URL resource = OrderDetailsController.class.getResource("/view/supplier_order_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("SupplierOrder Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
            String supId = txtSupId.getText();
            String userId = String.valueOf(cmbUserid.getValue());
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtTel.getText();


            boolean isSupplierValidated = validateSupplier();
            if (!isSupplierValidated){return;}

            var dto = new SupplierDto(supId,userId,name,address,tel);

            try {
                boolean isUpdated = supplierBO.updateSupplier(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Update Succesfull!!!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


        private boolean validateSupplier() {
        String name = txtName.getText();
        boolean isNameValidated = Pattern.compile("/w").matcher(name).matches();
        if (!isNameValidated) {
            txtName.requestFocus();
        }

        String Address = txtAddress.getText();
        boolean isAddressValidated = Pattern.compile("^[A-z]{1,20}$").matcher(Address).matches();
        if (!isAddressValidated) {
            txtAddress.requestFocus();
        }

        String tel = txtTel.getText();
        boolean isTelValidated = Pattern.compile("^[0-9]{10}$").matcher(tel).matches();
        if (!isTelValidated) {
            txtTel.requestFocus();
        }
        return true;
    }

    private void loadAllUsers() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<UserDto> userList = adminBO.loadAllAdmin();
            for (UserDto userDto : userList) {
                obList.add(userDto.getUser_id());
            }
            cmbUserid.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void autoGenerateId() throws SQLException, ClassNotFoundException {
        txtSupId.setText(supplierBO.generateSup_id());
    }
}

