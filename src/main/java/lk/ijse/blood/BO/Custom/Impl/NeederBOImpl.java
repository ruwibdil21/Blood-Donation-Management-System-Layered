package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.NeederBO;
import lk.ijse.blood.dao.Custom.NeederDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.NeederDto;
import lk.ijse.blood.entity.Needer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederBOImpl implements NeederBO {
    NeederDAO neederDAO = (NeederDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.NEEDER);
    @Override
    public List<NeederDto> loadAllNeeder() throws SQLException, ClassNotFoundException {
        List<Needer>neederList = neederDAO.loadAll();
        List<NeederDto>neederDtos = new ArrayList<>();

        for (Needer needer : neederList){
            neederDtos.add(new NeederDto(
                    needer.getNeederId(),
                    needer.getUserId(),
                    needer.getName(),
                    needer.getAddress(),
                    needer.getContact(),
                    needer.getEmail()));
        }
        return neederDtos;
    }

    @Override
    public boolean deleteNeeder(String id) throws SQLException, ClassNotFoundException {
        return neederDAO.delete(id);
    }

    @Override
    public NeederDto searchNeeder(String id) throws SQLException, ClassNotFoundException {
        Needer needer = neederDAO.search(id);
        if (needer != null) {
            return new NeederDto(
                    needer.getNeederId(),
                    needer.getUserId(),
                    needer.getName(),
                    needer.getAddress(),
                    needer.getContact(),
                    needer.getEmail());
        }
        return null;
    }

    @Override
    public boolean saveNeeder(NeederDto dto) throws SQLException, ClassNotFoundException {
        return neederDAO.save(new Needer(
                dto.getNeederId(),
                dto.getUserId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getEmail()));
    }

    @Override
    public boolean updateNeeder(NeederDto dto) throws SQLException, ClassNotFoundException {
        return neederDAO.update(new Needer(
                dto.getNeederId(),
                dto.getUserId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getEmail()));
    }

    @Override
    public String generateNeeder() throws SQLException, ClassNotFoundException {
        return neederDAO.generateId();
    }
}
