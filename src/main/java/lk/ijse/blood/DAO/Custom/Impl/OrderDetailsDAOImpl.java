package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.OrderDetailsDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.Util.TransactionUtil;
import lk.ijse.blood.entity.Inventory;
import lk.ijse.blood.entity.OrderDetails;
import lk.ijse.blood.entity.SupplierOrders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    public  boolean placeOrderDetails(SupplierOrders supplierOrdersDto, Inventory inventoryDto, OrderDetails orderDetailsDto) throws SQLException, ClassNotFoundException {
        try {
            TransactionUtil.startTransaction();
            boolean isSupplierOrdersSaved = new SupplierOrderDAOImpl().save(supplierOrdersDto);
            if (isSupplierOrdersSaved) {
                boolean isInventorySaved = new InventoryDAOImpl().save(inventoryDto);
                if (isInventorySaved) {
                    boolean isOrderDetailsSaved = new OrderDetailsDAOImpl().save(orderDetailsDto);
                    if (isOrderDetailsSaved) {
                        return true;
                    }
                }
                TransactionUtil.rollBack();
                return false;
            }
            TransactionUtil.rollBack();
            return false;
        } finally {
            TransactionUtil.endTransaction();
        }
    }

    @Override
    public List<OrderDetails> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into order_details values (?,?,?)", dto.getOrder_id(), dto.getMed_id(), dto.getDescription());
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Med_id FROM medical_inventory ORDER BY Med_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("Med_id");
            String numericPart = id.replaceAll("\\D", "");
            int newMedInId = Integer.parseInt(numericPart) + 1;
            return String.format("Med_In%03d", newMedInId);
        } else {
            return "Med_In001";
        }
    }
}
