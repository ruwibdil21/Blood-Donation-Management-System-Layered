package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.AttendanceBO;
import lk.ijse.blood.dao.Custom.AttendanceDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.AttendanceDto;
import lk.ijse.blood.entity.Attendance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public List<AttendanceDto> loadAllAttendance() throws SQLException, ClassNotFoundException {
        List<Attendance> attendanceList = attendanceDAO.loadAll();
        List<AttendanceDto> attendanceDtos = new ArrayList<>();

        for (Attendance attendance : attendanceList){
            attendanceDtos.add(new AttendanceDto(
                    attendance.getAtt_id(),
                    attendance.getEmp_id(),
                    attendance.getDate(),
                    attendance.getStatus()));
        }
        return attendanceDtos;
    }

    @Override
    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException {
        return attendanceDAO.delete(id);
    }

    @Override
    public AttendanceDto searchAttendance(String id) throws SQLException, ClassNotFoundException {
        Attendance attendance = attendanceDAO.search(id);
        if (attendance != null) {
            return new AttendanceDto(attendance.getAtt_id(),
                    attendance.getEmp_id(),
                    attendance.getDate(),
                    attendance.getStatus());
        }
        return null;
    }

    @Override
    public boolean saveAttendance(AttendanceDto dto) throws SQLException, ClassNotFoundException {
        return attendanceDAO.save(new Attendance(
                dto.getAtt_id(),
                dto.getEmp_id(),
                dto.getDate(),
                dto.getStatus()));
    }

    @Override
    public boolean updateAttendance(AttendanceDto dto) throws SQLException, ClassNotFoundException {
        return attendanceDAO.update(new Attendance(
                dto.getAtt_id(),
                dto.getEmp_id(),
                dto.getDate(),
                dto.getStatus()));
    }

    @Override
    public String generateAttendanceId() throws SQLException, ClassNotFoundException {
        return attendanceDAO.generateId();
    }
}
