package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.SupplierBO;
import lk.ijse.blood.dao.Custom.SupplierDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.SupplierDto;
import lk.ijse.blood.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public List<SupplierDto> loadAllSupplier() throws SQLException, ClassNotFoundException {
        List<Supplier> supplierList = supplierDAO.loadAll();
        List<SupplierDto> supplierDtos = new ArrayList<>();

        for (Supplier supplier :supplierList){
            supplierDtos.add(new SupplierDto(
                    supplier.getSup_id(),
                    supplier.getUser_id(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getTel()));
        }
        return supplierDtos;
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier =supplierDAO.search(id);
        if (supplier != null) {
            return new SupplierDto(
                    supplier.getSup_id(),
                    supplier.getUser_id(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getTel());
        }
        return null;
    }

    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(
                dto.getSup_id(),
                dto.getUser_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getTel()));
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(
                dto.getSup_id(),
                dto.getUser_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getTel()));
    }

    @Override
    public String generateSup_id() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateId();
    }
}
