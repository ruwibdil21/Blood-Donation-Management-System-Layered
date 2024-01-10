package lk.ijse.blood.dao.Custom;

import lk.ijse.blood.dao.CrudDAO;
import lk.ijse.blood.entity.User;

import java.sql.SQLException;


public interface AdminDAO extends CrudDAO<User> {
    boolean login(String id, String password) throws SQLException, ClassNotFoundException;

}

