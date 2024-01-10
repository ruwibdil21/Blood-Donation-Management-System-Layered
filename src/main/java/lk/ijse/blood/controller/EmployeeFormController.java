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
import lk.ijse.blood.bo.Custom.EmployeeBO;
import lk.ijse.blood.bo.Custom.Impl.AdminBOImpl;
import lk.ijse.blood.bo.Custom.Impl.EmployeeBOImpl;
import lk.ijse.blood.dto.EmployeeDto;
import lk.ijse.blood.dto.UserDto;
import lk.ijse.blood.dto.tm.EmployeeTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public DatePicker DtpDob;
    public ComboBox cmbUserid;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private AnchorPane employee;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;

    EmployeeBO employeeBO = new EmployeeBOImpl();

    AdminBO adminBO = new AdminBOImpl();

    public void initialize() {
        try {
            loadAllEmployees();
            setCellValueFactory();
            loadAllUsers();
            autoGenarateId();
        } catch (SQLException| ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("Emp_id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
    }

    public void loadAllEmployees() throws ClassNotFoundException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> dtoList = employeeBO.loadAllEmployee();

            for (EmployeeDto dto : dtoList) {
                obList.add(new EmployeeTm(
                        dto.getEmp_id(),
                        dto.getUser_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getRole(),
                        dto.getDOB()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/dashboard_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) employee.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Form");
        stage.centerOnScreen();
    }


    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String emp_id = txtEmpId.getText();
        String user_id = String.valueOf(cmbUserid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String role = txtRole.getText();
        String dob = String.valueOf(DtpDob.getValue());

        boolean isEmployeeValidated = validateEmloyee();
        if (!isEmployeeValidated){return;}

        var dto = new EmployeeDto(emp_id,user_id,name,address,role,dob);
        try {

            boolean isSaved = employeeBO.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtEmpId.setText("");
        cmbUserid.setValue("");
        txtName.setText("");
        txtAddress.setText("");
        txtRole.setText("");
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) {
        try {
            URL resource = SalaryFormController.class.getResource("/view/salary_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Salary Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnAttendenceOnAction(ActionEvent actionEvent) {
        try {
            URL resource = AttendanceFormController.class.getResource("/view/attendance_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Attendence Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateEmloyee() {
        String name = txtName.getText();
        boolean isNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(name).matches();
        if (!isNameValidated) {
            txtName.requestFocus();
        }

        String Address = txtAddress.getText();
        boolean isAddressValidated = Pattern.compile("^[A-z]{1,20}$").matcher(Address).matches();
        if (!isAddressValidated) {
            txtAddress.requestFocus();
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

    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtEmpId.setText(employeeBO.generateEmployee());
    }
}


