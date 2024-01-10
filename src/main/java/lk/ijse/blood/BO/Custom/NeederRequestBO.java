package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.NeederRequestDto;
import lk.ijse.blood.entity.BloodInventory;
import lk.ijse.blood.entity.NeederRequest;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.SQLException;
import java.util.List;

public interface NeederRequestBO extends SuperBO {
    List<NeederRequestDto> loadAllNeederRequest() throws SQLException, ClassNotFoundException;
    boolean deleteNeederRequest(String id) throws SQLException, ClassNotFoundException;
    NeederRequestDto searchNeederRequest(String id) throws SQLException, ClassNotFoundException ;
    boolean saveNeederRequest(NeederRequestDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateNeederRequwst(NeederRequestDto dto) throws SQLException, ClassNotFoundException;
    String generateNeedrRequwst() throws SQLException,ClassNotFoundException;
    boolean placeNeederRequest(NeederRequest neederRequestDto, BloodInventory bagdto, RequestDetails requestDetailsDto) throws SQLException, ClassNotFoundException;
}

