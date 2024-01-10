package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.SupplierOrdersDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierOrderBO extends SuperBO {
    List<SupplierOrdersDto> loadAllSupplierOrders() throws SQLException, ClassNotFoundException;
    boolean deleteSupplierOrders(String id) throws SQLException, ClassNotFoundException;
    SupplierOrdersDto searchSupplierOrders(String id) throws SQLException, ClassNotFoundException ;
    boolean saveSupplierOrders(SupplierOrdersDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateSupplierOrders(SupplierOrdersDto dto) throws SQLException, ClassNotFoundException;
    String generateSupOrder_id() throws SQLException,ClassNotFoundException;
}
