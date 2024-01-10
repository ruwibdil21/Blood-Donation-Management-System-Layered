package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.NeederRequestBO;
import lk.ijse.blood.dao.Custom.NeederRequestDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.NeederRequestDto;
import lk.ijse.blood.entity.BloodInventory;
import lk.ijse.blood.entity.NeederRequest;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederRequestBOImpl implements NeederRequestBO {

    NeederRequestDAO neederRequestDAO = (NeederRequestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.NEEDERREQUEST);
    @Override
    public List<NeederRequestDto> loadAllNeederRequest() throws SQLException, ClassNotFoundException {
        List<NeederRequest>neederRequestList = neederRequestDAO.loadAll();
        List<NeederRequestDto>neederRequestDtos = new ArrayList<>();

        for (NeederRequest neederRequest:neederRequestList){
            neederRequestDtos.add(new NeederRequestDto(
                    neederRequest.getNeeReq_id(),
                    neederRequest.getNeederId(),
                    neederRequest.getDate(),
                    neederRequest.getAmount()));
        }
        return neederRequestDtos;
    }

    @Override
    public boolean deleteNeederRequest(String id) throws SQLException, ClassNotFoundException {
        return neederRequestDAO.delete(id);
    }

    @Override
    public NeederRequestDto searchNeederRequest(String id) throws SQLException, ClassNotFoundException {
        NeederRequest neederRequest =neederRequestDAO.search(id);
        if (neederRequest != null) {
            return new NeederRequestDto(
                    neederRequest.getNeeReq_id(),
                    neederRequest.getNeederId(),
                    neederRequest.getDate(),
                    neederRequest.getAmount());
        }
        return null;
    }

    @Override
    public boolean saveNeederRequest(NeederRequestDto dto) throws SQLException, ClassNotFoundException {
        return neederRequestDAO.save(new NeederRequest(
                dto.getNeeReq_id(),
                dto.getNeederId(),
                dto.getDate(),
                dto.getAmount()));
    }

    @Override
    public boolean updateNeederRequwst(NeederRequestDto dto) throws SQLException, ClassNotFoundException {
        return neederRequestDAO.update(new NeederRequest(
                dto.getNeeReq_id(),
                dto.getNeederId(),
                dto.getDate(),
                dto.getAmount()));
    }

    @Override
    public String generateNeedrRequwst() throws SQLException, ClassNotFoundException {
        return neederRequestDAO.generateId();
    }

    @Override
    public boolean placeNeederRequest(NeederRequest neederRequestDto, BloodInventory bagdto, RequestDetails requestDetailsDto) throws SQLException, ClassNotFoundException {
        return neederRequestDAO.placeNeederRequest(neederRequestDto,bagdto,requestDetailsDto);
    }
}
