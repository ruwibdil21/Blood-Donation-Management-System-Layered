package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.SupplierOrderDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.SupplierOrders;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderDAOImpl implements SupplierOrderDAO {

    @Override
    public List<SupplierOrders> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier_orders");
        List<SupplierOrders> SupplierOrdersList= new ArrayList<>();

        while (resultSet.next()) {
            SupplierOrders supplierOrdersDto = new SupplierOrders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            SupplierOrdersList.add(supplierOrdersDto);
        }

        return SupplierOrdersList;    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier_orders WHERE Supplier_id = ?",id);
    }

    @Override
    public SupplierOrders search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier_orders WHERE Supplier_id = ?",id);

        if (resultSet.next()){
            return new SupplierOrders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

        }
        return null;
    }

    @Override
    public boolean save(SupplierOrders dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into supplier_orders values (?,?,?,?)",dto.getSupOrder_id(),dto.getSupplier_id(),dto.getDate(),dto.getAmount());
    }

    @Override
    public boolean update(SupplierOrders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(SupplierOrders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT SupOrder_id FROM supplier_orders ORDER BY SupOrder_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("SupOrder_id");
            String numericPart = id.replaceAll("\\D", "");
            int newSupOrId = Integer.parseInt(numericPart) + 1;
            return String.format("Sup_Or%03d", newSupOrId);
        } else {
            return "Sup_Or001";
        }
    }
}
