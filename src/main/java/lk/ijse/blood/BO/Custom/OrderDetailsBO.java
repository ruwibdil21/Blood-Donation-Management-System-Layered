package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.OrderDetailsDto;
import lk.ijse.blood.entity.Inventory;
import lk.ijse.blood.entity.OrderDetails;
import lk.ijse.blood.entity.SupplierOrders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBO extends SuperBO {
    List<OrderDetailsDto> loadAllOrderDetails() throws SQLException, ClassNotFoundException;
    boolean deleteOrderDetails(String id) throws SQLException, ClassNotFoundException;
    OrderDetailsDto searchOrderDetails(String id) throws SQLException, ClassNotFoundException ;
    boolean saveOrderDetails(OrderDetailsDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateOrderDetails(OrderDetailsDto dto) throws SQLException, ClassNotFoundException;
    String generateOrderDetails() throws SQLException,ClassNotFoundException;
    boolean placeOrderDetails(SupplierOrders supplierOrdersDto, Inventory inventoryDto, OrderDetails orderDetailsDto) throws SQLException, ClassNotFoundException;
}
