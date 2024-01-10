package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.bo.Custom.Impl.RequestDetailsBOImpl;
import lk.ijse.blood.bo.Custom.RequestDetailsBO;
import lk.ijse.blood.dto.RequestDetailsDto;
import lk.ijse.blood.dto.tm.RequestDetailsTm;

import java.sql.SQLException;
import java.util.List;

public class RequestDetailsFormController {
    @FXML
    private AnchorPane RequestDetails;

    @FXML
    private TableColumn<?, ?> colBloodBagId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colNeederId;

    @FXML
    private TableView<RequestDetailsTm> tblRequestDetails;

    RequestDetailsBO requestDetailsBO = new RequestDetailsBOImpl();

    public void initialize() {
        try {
            loadAllRequestDetails();
            setCellValueFactory();
        } catch (ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setCellValueFactory() {
        colNeederId.setCellValueFactory(new PropertyValueFactory<>("neeReq_id"));
        colBloodBagId.setCellValueFactory(new PropertyValueFactory<>("bloodBagId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

    }

    public void loadAllRequestDetails() throws ClassNotFoundException {

        ObservableList<RequestDetailsTm> obList = FXCollections.observableArrayList();

        try{
            List<RequestDetailsDto> dtoList = requestDetailsBO.loadAllRequestDetails();

            for(RequestDetailsDto dto : dtoList){
                obList.add(new RequestDetailsTm(
                        dto.getNeeReq_id(),
                        dto.getBloodBagId(),
                        dto.getDescription()

                ));
            }
            tblRequestDetails.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}


