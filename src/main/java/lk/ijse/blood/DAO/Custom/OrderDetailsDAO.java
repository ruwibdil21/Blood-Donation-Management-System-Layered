package lk.ijse.blood.dao.Custom;

import lk.ijse.blood.dao.CrudDAO;
import lk.ijse.blood.dto.SupplierOrdersDto;
import lk.ijse.blood.entity.Inventory;
import lk.ijse.blood.entity.OrderDetails;
import lk.ijse.blood.entity.SupplierOrders;

import java.sql.SQLException;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails> {
    boolean placeOrderDetails(SupplierOrders supplierOrdersDto, Inventory inventoryDto, OrderDetails orderDetailsDto) throws SQLException, ClassNotFoundException ;
//
//     boolean saveOrderDetails(OrderDetailsDto dto) throws SQLException, ClassNotFoundException;

    }
