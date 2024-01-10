package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface AdminBO extends SuperBO {
    List<UserDto> loadAllAdmin() throws SQLException, ClassNotFoundException;
    boolean deleteAdmin(String id) throws SQLException, ClassNotFoundException;
    UserDto searchAdmin(String id) throws SQLException, ClassNotFoundException ;
    boolean saveAdmin(UserDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateAdmin(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean loginAdmin(String id, String password) throws SQLException, ClassNotFoundException;
    String generateAdminId() throws SQLException,ClassNotFoundException;
}
