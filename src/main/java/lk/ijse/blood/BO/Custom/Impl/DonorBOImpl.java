package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.DonorBO;
import lk.ijse.blood.dao.Custom.DonorDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.entity.Donor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonorBOImpl implements DonorBO {
    DonorDAO donorDAO = (DonorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DONOR);
    @Override
    public List<DonorDto> loadAllDonor() throws SQLException, ClassNotFoundException {
        List<Donor>donorList = donorDAO.loadAll();
        List<DonorDto> donorDtos =new ArrayList<>();

        for (Donor donor:donorList){
            donorDtos.add(new DonorDto(
                    donor.getD_id(),
                    donor.getFirstName(),
                    donor.getLastName(),
                    donor.getType(),
                    donor.getDob(),
                    donor.getTel(),
                    donor.getLastDate()));
        }
        return donorDtos;
    }

    @Override
    public boolean deleteDonor(String id) throws SQLException, ClassNotFoundException {
        return donorDAO.delete(id);
    }

    @Override
    public DonorDto searchDonor(String id) throws SQLException, ClassNotFoundException {
        Donor donor =donorDAO.search(id);
        if (donor != null) {
            return new DonorDto(
                    donor.getD_id(),
                    donor.getFirstName(),
                    donor.getLastName(),
                    donor.getType(),
                    donor.getDob(),
                    donor.getTel(),
                    donor.getLastDate());
        }
        return null;
    }

    @Override
    public boolean saveDonor(DonorDto dto) throws SQLException, ClassNotFoundException {
        return donorDAO.save(new Donor(
                dto.getD_id(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getType(),
                dto.getDob(),
                dto.getTel(),
                dto.getLastDate()));
    }

    @Override
    public boolean updateDonor(DonorDto dto) throws SQLException, ClassNotFoundException {
        return donorDAO.update(new Donor(
                dto.getD_id(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getType(),
                dto.getDob(),
                dto.getTel(),
                dto.getLastDate()));
    }

    @Override
    public String generateDId() throws SQLException, ClassNotFoundException {
        return donorDAO.generateId();
    }
}
