package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    List<SupplierDto> loadAllSupplier() throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException ;
    boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    String generateSup_id() throws SQLException,ClassNotFoundException;
}
