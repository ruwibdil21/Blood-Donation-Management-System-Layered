package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.DonationDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.Donation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationDAOImpl implements DonationDAO {

    @Override
    public List<Donation> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM donation");
        List<Donation> donationList = new ArrayList<>();

        while (resultSet.next()) {
            Donation donationDto = new Donation(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
            donationList.add(donationDto);
        }
        return donationList;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM donation WHERE Donation_id = ?",id);
    }

    @Override
    public Donation search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM donation WHERE Donation_id = ?",id);

        if (resultSet.next()){
            return new Donation(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        return null;
    }

    @Override
    public boolean save(Donation dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into donation values (?,?,?,?,?)",dto.getDo_id(),dto.getD_id(),dto.getDate(),dto.getBlood_type(),dto.getHemoglobin_level());
    }

    @Override
    public boolean update(Donation dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(Donation dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT donation_id FROM donation ORDER BY donation_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("donation_id");
            String numericPart = id.replaceAll("\\D", "");
            int newCustomerId = Integer.parseInt(numericPart) + 1;
            return String.format("DT%03d", newCustomerId);
        } else {
            return "DT001";
        }
    }
}
