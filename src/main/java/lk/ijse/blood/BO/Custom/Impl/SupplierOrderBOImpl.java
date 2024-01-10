package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.SupplierOrderBO;
import lk.ijse.blood.dao.Custom.SupplierOrderDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.SupplierOrdersDto;
import lk.ijse.blood.entity.SupplierOrders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderBOImpl implements SupplierOrderBO {
    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIERORDER);
    @Override
    public List<SupplierOrdersDto> loadAllSupplierOrders() throws SQLException, ClassNotFoundException {
        List<SupplierOrders> supplierOrdersList = supplierOrderDAO.loadAll();
        List<SupplierOrdersDto> supplierOrdersDtos =new ArrayList<>();

        for (SupplierOrders supplierOrders : supplierOrdersList){
            supplierOrdersDtos.add(new SupplierOrdersDto
                    (supplierOrders.getSupOrder_id(),
                            supplierOrders.getSupplier_id(),
                            supplierOrders.getDate(),
                            supplierOrders.getAmount()));

        }
        return supplierOrdersDtos;
    }

    @Override
    public boolean deleteSupplierOrders(String id) throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.delete(id);
    }

    @Override
    public SupplierOrdersDto searchSupplierOrders(String id) throws SQLException, ClassNotFoundException {
       SupplierOrders supplierOrders =supplierOrderDAO.search(id);
       if (supplierOrders != null) {
           return new SupplierOrdersDto(
                   supplierOrders.getSupOrder_id(),
                   supplierOrders.getSupplier_id(),
                   supplierOrders.getDate(),
                   supplierOrders.getAmount());
       }
       return null;
    }

    @Override
    public boolean saveSupplierOrders(SupplierOrdersDto dto) throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.save(new SupplierOrders(
                dto.getSupOrder_id(),
                dto.getSupplier_id(),
                dto.getDate(),
                dto.getAmount()));
    }

    @Override
    public boolean updateSupplierOrders(SupplierOrdersDto dto) throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.update(new SupplierOrders(
                dto.getSupOrder_id(),
                dto.getSupplier_id(),
                dto.getDate(),
                dto.getAmount()));
    }

    @Override
    public String generateSupOrder_id() throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.generateId();
    }
}
