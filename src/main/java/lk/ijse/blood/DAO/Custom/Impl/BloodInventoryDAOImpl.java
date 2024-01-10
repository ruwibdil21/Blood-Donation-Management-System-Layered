package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.BloodInventoryDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.BloodInventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodInventoryDAOImpl implements BloodInventoryDAO {
    @Override
    public List<BloodInventory> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM blood_inventory ");
        List<BloodInventory> bloodInventoryList = new ArrayList<>();

        while (resultSet.next()) {
            BloodInventory bloodInventoryDto = new BloodInventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
            bloodInventoryList.add(bloodInventoryDto);
        }

        return bloodInventoryList;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM blood_inventory WHERE  BloodBag_id = ?",id);
    }

    @Override
    public BloodInventory search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM blood_inventory ");
        List<BloodInventory> bloodInventoryList = new ArrayList<>();

        if (resultSet.next()) {
           return new BloodInventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }

        return  null;
    }

    @Override
    public boolean save(BloodInventory dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into blood_inventory values (?,?,?,?,?)",dto.getBloodBagId(),dto.getDonation_id(),dto.getDonation_date(),dto.getEx_date(),dto.getBlood_type());
    }

    @Override
    public boolean update(BloodInventory dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE blood_inventory SET Donation_id = ?, Donation_date= ?,Ex_date = ?,Blood_type = ? ,WHERE  BloodBag_id = ?",dto.getDonation_id(),dto.getDonation_date(),dto.getEx_date(),dto.getBlood_type(),dto.getBloodBagId());
    }

    @Override
    public boolean add(BloodInventory dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT BloodBag_id FROM blood_inventory ORDER BY BloodBag_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("BloodBag_id");
            String numericPart = id.replaceAll("\\D", "");
            int newInventoryId = Integer.parseInt(numericPart) + 1;
            return String.format("B%03d", newInventoryId);
        } else {
            return "B001";
        }
    }
}
