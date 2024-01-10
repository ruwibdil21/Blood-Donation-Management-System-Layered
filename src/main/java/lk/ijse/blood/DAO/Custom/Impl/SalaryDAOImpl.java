package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.SalaryDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public List<Salary> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary");
        List<Salary> salaryList= new ArrayList<>();

        while (resultSet.next()) {
           Salary salaryDto = new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
           salaryList.add(salaryDto);
        }

        return salaryList;    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM salary WHERE Salary_id = ?",id);
    }

    @Override
    public Salary search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary WHERE Salary_id = ?",id);

        if (resultSet.next()){
            return new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    resultSet.getString(5));


        }
        return null;    }

    @Override
    public boolean save(Salary dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into Salary values (?,?,?,?,?)", dto.getSalary_id(),dto.getEmployee_id(),dto.getAmount(), dto.getMonth(),dto.getYear());
    }

    @Override
    public boolean update(Salary dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE salary SET Emp_id = ?, Amount = ?,Month = ?,Year= ?  WHERE Salary_id = ?", dto.getEmployee_id(),dto.getAmount(),dto.getMonth(), dto.getYear(),dto.getSalary_id());
    }

    @Override
    public boolean add(Salary dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
