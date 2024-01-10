package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.AttendanceDto;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    List<AttendanceDto> loadAllAttendance() throws SQLException, ClassNotFoundException;
    boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException;
    AttendanceDto searchAttendance(String id) throws SQLException, ClassNotFoundException ;
    boolean saveAttendance(AttendanceDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateAttendance(AttendanceDto dto) throws SQLException, ClassNotFoundException;
    String generateAttendanceId() throws SQLException,ClassNotFoundException;
}
