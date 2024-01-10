package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.RequestDetailsDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDetailsDAOImpl implements RequestDetailsDAO {

    @Override
    public List<RequestDetails> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM request_Details");
        List<RequestDetails> requestDetailsList = new ArrayList<>();

        while (resultSet.next()) {
           RequestDetails requestDetailsDto = new RequestDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
           requestDetailsList.add(requestDetailsDto);
        }
        return requestDetailsList;    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM request_details WHERE NeeReqId = ?",id);
    }

    @Override
    public RequestDetails search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM neeReq_id WHERE NeeReqId = ?",id);

        if (resultSet.next()){
            return new RequestDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));


        }
        return null;    }

    @Override
    public boolean save(RequestDetails dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into request_details values (?,?,?)",dto.getNeeReq_id(),dto.getBloodBagId(),dto.getDescription());
    }

    @Override
    public boolean update(RequestDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(RequestDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT NeeReq_id FROM request_details ORDER BY NeeReq_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("NeeReq_id");
            String numericPart = id.replaceAll("\\D", "");
            int newNeederId = Integer.parseInt(numericPart) + 1;
            return String.format("N%03d", newNeederId);
        } else {
            return "N001";
        }
    }
}
