package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    List<SalaryDto> loadAllSalary() throws SQLException, ClassNotFoundException;
    boolean deleteSalary(String id) throws SQLException, ClassNotFoundException;
    SalaryDto searchSalary(String id) throws SQLException, ClassNotFoundException ;
    boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateSalary(SalaryDto dto) throws SQLException, ClassNotFoundException;
    String generateSalary_id() throws SQLException,ClassNotFoundException;
}
