package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.SalaryBO;
import lk.ijse.blood.dao.Custom.SalaryDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.SalaryDto;
import lk.ijse.blood.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    @Override
    public List<SalaryDto> loadAllSalary() throws SQLException, ClassNotFoundException {
        List<Salary>salaryList =salaryDAO.loadAll();
        List<SalaryDto>salaryDtos =new ArrayList<>();

        for (Salary salary :salaryList){
            salaryDtos.add(new SalaryDto(
                    salary.getSalary_id(),
                    salary.getEmployee_id(),
                    salary.getAmount(),
                    salary.getMonth(),
                    salary.getYear()));

        }
        return salaryDtos;
    }

    @Override
    public boolean deleteSalary(String id) throws SQLException, ClassNotFoundException {
        return salaryDAO.delete(id);
    }

    @Override
    public SalaryDto searchSalary(String id) throws SQLException, ClassNotFoundException {
        Salary salary = salaryDAO.search(id);
        if (salary != null) {
            return new SalaryDto(
                    salary.getSalary_id(),
                    salary.getEmployee_id(),
                    salary.getAmount(),
                    salary.getMonth(),
                    salary.getYear());
        }
        return null;
    }

    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return salaryDAO.save(new Salary(
                dto.getSalary_id(),
                dto.getEmployee_id(),
                dto.getAmount(),
                dto.getMonth(),
                dto.getYear()));
    }

    @Override
    public boolean updateSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return salaryDAO.update(new Salary(
                dto.getSalary_id(),
                dto.getEmployee_id(),
                dto.getAmount(),
                dto.getMonth(),
                dto.getYear()));
    }

    @Override
    public String generateSalary_id() throws SQLException, ClassNotFoundException {
        return salaryDAO.generateId();
    }
}
