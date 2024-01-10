package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.DonorDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.Donor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonorDAOImpl implements DonorDAO {
    @Override
    public List<Donor> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM donor");
        List<Donor> donorList = new ArrayList<>();

        while (resultSet.next()) {
            Donor donorDto = new Donor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7));
            donorList.add(donorDto);
        }
        return donorList;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM donor WHERE D_id = ?",id);
    }

    @Override
    public Donor search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM donor WHERE D_id = ?",id);

        if (resultSet.next()){
            return new Donor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7));

        }
        return null;
    }

    @Override
    public boolean save(Donor dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into donor values (?,?,?,?,?,?,?)",dto.getD_id(),dto.getFirstName(),dto.getLastName(),dto.getDob(),dto.getType(),dto.getTel(),dto.getLastDate());
    }

    @Override
    public boolean update(Donor dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE donor SET F_name = ?, L_name = ?, DOB = ?, Tel = ?, Blood_type = ?, L_donate_date = ? WHERE D_id = ?",dto.getFirstName(),dto.getLastName(),dto.getDob(),dto.getType(),dto.getTel(),dto.getLastDate(),dto.getD_id());
    }

    @Override
    public boolean add(Donor dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT D_id FROM donor ORDER BY D_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("D_id");
            String numericPart = id.replaceAll("\\D", "");
            int newDonorId = Integer.parseInt(numericPart) + 1;
            return String.format("D%03d", newDonorId);
        } else {
            return "D001";
        }
    }
}
