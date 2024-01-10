package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.BloodInventoryDto;

import java.sql.SQLException;
import java.util.List;

public interface BloodInventoryBO extends SuperBO {
    List<BloodInventoryDto> loadAllBloodInventoy() throws SQLException, ClassNotFoundException;
    boolean deleteBloodInventory(String id) throws SQLException, ClassNotFoundException;
    BloodInventoryDto searchBloodInventory(String id) throws SQLException, ClassNotFoundException ;
    boolean saveBloodInventory(BloodInventoryDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateBloodInventoy(BloodInventoryDto dto) throws SQLException, ClassNotFoundException;
    String generateBloodBagId() throws SQLException,ClassNotFoundException;
}
