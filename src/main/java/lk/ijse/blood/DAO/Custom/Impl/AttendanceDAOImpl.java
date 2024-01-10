package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.AttendanceDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.Attendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public List<Attendance> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM attendance");
        List<Attendance> attendanceList= new ArrayList<>();

        while (resultSet.next()) {
            Attendance attendanceDto = new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            attendanceList.add(attendanceDto);
        }
        return attendanceList;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM attendance WHERE Att_id = ?",id);
    }


    @Override
    public Attendance search(String attId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM attendance WHERE Att_id = ?");
        if (resultSet.next()){
            return new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
        }
        return null;
    }

    @Override
    public boolean save(Attendance dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into attendance values (?,?,?,?)",dto.getAtt_id(),dto.getEmp_id(),dto.getDate(),dto.getStatus());
    }

    @Override
    public boolean update(Attendance dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE attendance SET Emp_id = ?, Date= ?, Status = ? WHERE Att_id = ?",dto.getEmp_id(),dto.getDate(),dto.getStatus(),dto.getAtt_id());
    }

    @Override
    public boolean add(Attendance dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Att_id FROM attendance ORDER BY Att_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("Att_id");
            String numericPart = id.replaceAll("\\D", "");
            int newAttendanceID = Integer.parseInt(numericPart) + 1;
            return String.format("Att%03d", newAttendanceID);
        } else {
            return "Att001";
        }
    }
}
