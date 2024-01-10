package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.RequestDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface RequestDetailsBO extends SuperBO {
    List<RequestDetailsDto> loadAllRequestDetails() throws SQLException, ClassNotFoundException;
    boolean deleteRequestDetails(String id) throws SQLException, ClassNotFoundException;
    RequestDetailsDto searchRequestDetails(String id) throws SQLException, ClassNotFoundException ;
    boolean saveRequestDetails(RequestDetailsDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateRequestDetails(RequestDetailsDto dto) throws SQLException, ClassNotFoundException;
    String generateRequestDetails() throws SQLException,ClassNotFoundException;

}
