package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.BloodInventoryBO;
import lk.ijse.blood.dao.Custom.BloodInventoryDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.BloodInventoryDto;
import lk.ijse.blood.entity.BloodInventory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodInventoryBOImpl implements BloodInventoryBO {
    BloodInventoryDAO bloodInventoryDAO = (BloodInventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BLOODINVENTORY);
    @Override
    public List<BloodInventoryDto> loadAllBloodInventoy() throws SQLException, ClassNotFoundException {
        List<BloodInventory> bloodInventoryList = bloodInventoryDAO.loadAll();
        List<BloodInventoryDto> bloodInventoryDtos = new ArrayList<>();

        for (BloodInventory bloodInventory : bloodInventoryList) {
            bloodInventoryDtos.add(new BloodInventoryDto(
                    bloodInventory.getBloodBagId(),
                    bloodInventory.getDonation_id(),
                    bloodInventory.getDonation_date(),
                    bloodInventory.getEx_date(),
                    bloodInventory.getBlood_type()));
        }
        return bloodInventoryDtos;
    }

    @Override
    public boolean deleteBloodInventory(String id) throws SQLException, ClassNotFoundException {
        return bloodInventoryDAO.delete(id);
    }

    @Override
    public BloodInventoryDto searchBloodInventory(String id) throws SQLException, ClassNotFoundException {
        BloodInventory bloodInventory = bloodInventoryDAO.search(id);
        if (bloodInventory != null) {
            return new BloodInventoryDto(
                    bloodInventory.getBloodBagId(),
                    bloodInventory.getDonation_id(),
                    bloodInventory.getDonation_date(),
                    bloodInventory.getEx_date(),
                    bloodInventory.getBlood_type());
        }
        return null;
    }

    @Override
    public boolean saveBloodInventory(BloodInventoryDto dto) throws SQLException, ClassNotFoundException {
        return bloodInventoryDAO.save(new BloodInventory(
                dto.getBloodBagId(),
                dto.getDonation_id(),
                dto.getDonation_date(),
                dto.getEx_date(),
                dto.getBlood_type()));
    }

    @Override
    public boolean updateBloodInventoy(BloodInventoryDto dto) throws SQLException, ClassNotFoundException {
        return bloodInventoryDAO.update(new BloodInventory(
                dto.getBloodBagId(),
                dto.getDonation_id(),
                dto.getDonation_date(),
                dto.getEx_date(),
                dto.getBlood_type()));
    }

    @Override
    public String generateBloodBagId() throws SQLException, ClassNotFoundException {
        return bloodInventoryDAO.generateId();
    }
}
