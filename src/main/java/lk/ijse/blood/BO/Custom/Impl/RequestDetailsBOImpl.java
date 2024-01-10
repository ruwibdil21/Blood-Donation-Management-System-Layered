package lk.ijse.blood.bo.Custom.Impl;

import lk.ijse.blood.bo.Custom.RequestDetailsBO;
import lk.ijse.blood.dao.Custom.RequestDetailsDAO;
import lk.ijse.blood.dao.DAOFactory;
import lk.ijse.blood.dto.RequestDetailsDto;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDetailsBOImpl implements RequestDetailsBO {

    RequestDetailsDAO requestDetailsDAO = (RequestDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REQUESTDETAILS);
    @Override
    public List<RequestDetailsDto> loadAllRequestDetails() throws SQLException, ClassNotFoundException {
        List<RequestDetails>requestDetailsList = requestDetailsDAO.loadAll();
        List<RequestDetailsDto>requestDetailsDtos = new ArrayList<>();

        for (RequestDetails requestDetails : requestDetailsList){
            requestDetailsDtos.add(new RequestDetailsDto(
                    requestDetails.getNeeReq_id(),
                    requestDetails.getBloodBagId(),
                    requestDetails.getDescription()));
        }
        return requestDetailsDtos;
    }

    @Override
    public boolean deleteRequestDetails(String id) throws SQLException, ClassNotFoundException {
        return requestDetailsDAO.delete(id);
    }

    @Override
    public RequestDetailsDto searchRequestDetails(String id) throws SQLException, ClassNotFoundException {
        RequestDetails requestDetails =requestDetailsDAO.search(id);
        if (requestDetails != null) {
            return new RequestDetailsDto(
                    requestDetails.getNeeReq_id(),
                    requestDetails.getBloodBagId(),
                    requestDetails.getDescription());
        }
        return null;
    }

    @Override
    public boolean saveRequestDetails(RequestDetailsDto dto) throws SQLException, ClassNotFoundException {
        return requestDetailsDAO.save(new RequestDetails(
                dto.getNeeReq_id(),
                dto.getBloodBagId(),
                dto.getDescription()));
    }

    @Override
    public boolean updateRequestDetails(RequestDetailsDto dto) throws SQLException, ClassNotFoundException {
        return requestDetailsDAO.update(new RequestDetails(
                dto.getNeeReq_id(),
                dto.getBloodBagId(),
                dto.getDescription()));
    }

    @Override
    public String generateRequestDetails() throws SQLException, ClassNotFoundException {
        return requestDetailsDAO.generateId();
    }
}
