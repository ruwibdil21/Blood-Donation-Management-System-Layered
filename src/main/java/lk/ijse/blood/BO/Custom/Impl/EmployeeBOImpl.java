package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.EmployeeBO;
import lk.ijse.blood.dao.Custom.EmployeeDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.EmployeeDto;
import lk.ijse.blood.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DONATION);
    @Override
    public List<EmployeeDto> loadAllEmployee() throws SQLException, ClassNotFoundException {
        List<Employee> employeeList = employeeDAO.loadAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        for (Employee employee : employeeList) {
            employeeDtos.add(new EmployeeDto(
                    employee.getEmp_id(),
                    employee.getUser_id(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getRole(),
                    employee.getDOB()));
        }
        return employeeDtos;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        if (employee != null) {
            return new EmployeeDto(
                    employee.getEmp_id(),
                    employee.getUser_id(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getRole(),
                    employee.getDOB());
        }
        return null;
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(
                dto.getEmp_id(),
                dto.getUser_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getRole(),
                dto.getDOB()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                dto.getEmp_id(),
                dto.getUser_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getRole(),
                dto.getDOB()));

    }

    @Override
    public String generateEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateId();
    }
}
