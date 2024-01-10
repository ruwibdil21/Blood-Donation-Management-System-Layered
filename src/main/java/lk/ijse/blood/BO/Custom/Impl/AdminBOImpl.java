package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.AdminBO;
import lk.ijse.blood.dao.Custom.AdminDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.UserDto;
import lk.ijse.blood.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminBOImpl implements AdminBO {
    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADMIN);
    @Override
    public List<UserDto> loadAllAdmin() throws SQLException, ClassNotFoundException {
        List<User> users = adminDAO.loadAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            userDtos.add(new UserDto(
                    user.getUser_id(),
                    user.getName(),
                    user.getPassword()));
        }
        return userDtos;
    }

    @Override
    public boolean deleteAdmin(String id) throws SQLException, ClassNotFoundException {
       return adminDAO.delete(id);
    }

    @Override
    public UserDto searchAdmin(String id) throws SQLException, ClassNotFoundException {
        User user = adminDAO.search(id);
        if (user != null) {
            return new UserDto(
                    user.getUser_id(),
                    user.getName(),
                    user.getPassword());
        }
        return null;
    }

    @Override
    public boolean saveAdmin(UserDto dto) throws SQLException, ClassNotFoundException {
        return adminDAO.save(new User(
                dto.getUser_id(),
                dto.getName(),
                dto.getPassword()));
    }

    @Override
    public boolean updateAdmin(UserDto dto) throws SQLException, ClassNotFoundException {
        return adminDAO.update(new User(
                dto.getUser_id(),
                dto.getName(),
                dto.getPassword()));
    }

    @Override
    public boolean loginAdmin(String id, String password) throws SQLException, ClassNotFoundException {
        return adminDAO.login(id,password);
    }

    @Override
    public String generateAdminId() throws SQLException, ClassNotFoundException {
        return adminDAO.generateId();
    }
}
