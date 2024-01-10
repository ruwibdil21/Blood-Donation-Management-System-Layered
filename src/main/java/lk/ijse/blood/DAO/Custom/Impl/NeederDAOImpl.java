package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.NeederDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.Needer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederDAOImpl implements NeederDAO {

    @Override
    public List<Needer> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM needer ");
        List<Needer> neederList= new ArrayList<>();

        while (resultSet.next()) {
            Needer neederDto = new Needer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
            neederList.add(neederDto);
        }

        return neederList;    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM needer WHERE Needer_id = ?",id);
    }

    @Override
    public Needer search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Needer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into needer values (?,?,?,?,?,?)",dto.getNeederId(),dto.getUserId(),dto.getName(),dto.getAddress(),dto.getContact(),dto.getEmail());
    }

    @Override
    public boolean update(Needer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE needer SET User_id = ?, Name = ?, Address = ?, Contact = ?, Email = ? WHERE Needer_id = ?",dto.getUserId(),dto.getName(),dto.getAddress(),dto.getContact(),dto.getEmail(),dto.getNeederId());
    }

    @Override
    public boolean add(Needer dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
