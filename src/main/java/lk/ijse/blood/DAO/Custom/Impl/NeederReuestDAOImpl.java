package lk.ijse.blood.dao.Custom.Impl;

import lk.ijse.blood.dao.Custom.NeederRequestDAO;
import lk.ijse.blood.Util.SQLUtil;
import lk.ijse.blood.Util.TransactionUtil;
import lk.ijse.blood.entity.BloodInventory;
import lk.ijse.blood.entity.NeederRequest;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederReuestDAOImpl implements NeederRequestDAO {
    public  boolean placeNeederRequest(NeederRequest neederRequestDto, BloodInventory bagdto, RequestDetails requestDetailsDto) throws SQLException, ClassNotFoundException {
        try {
            TransactionUtil.startTransaction();
            boolean isNeederRequestSaved = new NeederReuestDAOImpl().save(neederRequestDto);
            if (isNeederRequestSaved) {
                boolean isBloodInventorySaved = new BloodInventoryDAOImpl().save(bagdto);
                if (isBloodInventorySaved) {
                    boolean isrequestDetailsSaved = new RequestDetailsDAOImpl().save(requestDetailsDto);
                    if (isrequestDetailsSaved) {
                        return true;
                    }
                }
                TransactionUtil.rollBack();
                return false;
            }TransactionUtil.rollBack();
            return false;
        }finally{
            TransactionUtil.endTransaction();
        }
    }


    @Override
    public List<NeederRequest> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM needer_Request");
        List<NeederRequest> neederREquestList = new ArrayList<>();

        while (resultSet.next()) {
            NeederRequest neederRequestDto = new NeederRequest(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            neederREquestList.add(neederRequestDto);
        }

        return neederREquestList;    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM needer_Request WHERE Request_id = ?",id);
    }

    @Override
    public NeederRequest search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute( "SELECT * FROM needer_Request WHERE request_id = ?",id);

        if (resultSet.next()) {
            return new NeederRequest(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

        }
        return null;
    }

    @Override
    public boolean save(NeederRequest dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "insert into needer_request values (?,?,?,?)",dto.getNeeReq_id(),dto.getNeederId(),dto.getDate(),dto.getAmount());
    }

    @Override
    public boolean update(NeederRequest dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE needer_Request SET Needer_id = ?, Date = ?,Amount = ?, WHERE Request_id = ?",dto.getNeeReq_id(),dto.getNeederId(),dto.getDate(),dto.getAmount());
    }

    @Override
    public boolean add(NeederRequest dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Request_id FROM needer_request ORDER BY Request_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("Request_id");
            String numericPart = id.replaceAll("\\D", "");
            int newRequestId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", newRequestId);
        } else {
            return "R001";
        }
    }
}
