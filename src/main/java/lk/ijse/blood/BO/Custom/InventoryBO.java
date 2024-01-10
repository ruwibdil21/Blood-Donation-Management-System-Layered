package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.InventoryDto;

import java.sql.SQLException;
import java.util.List;

public interface InventoryBO extends SuperBO {
    List<InventoryDto> loadAllInventory() throws SQLException, ClassNotFoundException;
    boolean deleteInventory(String id) throws SQLException, ClassNotFoundException;
    InventoryDto searchInventory(String id) throws SQLException, ClassNotFoundException ;
    boolean saveInventory(InventoryDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateInventory(InventoryDto dto) throws SQLException, ClassNotFoundException;
    String generateInventory() throws SQLException,ClassNotFoundException;
}
