package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.NeederDto;

import java.sql.SQLException;
import java.util.List;

public interface NeederBO extends SuperBO {

    List<NeederDto> loadAllNeeder() throws SQLException, ClassNotFoundException;
    boolean deleteNeeder(String id) throws SQLException, ClassNotFoundException;
    NeederDto searchNeeder(String id) throws SQLException, ClassNotFoundException ;
    boolean saveNeeder(NeederDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateNeeder(NeederDto dto) throws SQLException, ClassNotFoundException;
    String generateNeeder() throws SQLException,ClassNotFoundException;

}
