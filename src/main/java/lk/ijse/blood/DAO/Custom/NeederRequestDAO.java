package lk.ijse.blood.dao.Custom;

import lk.ijse.blood.dao.CrudDAO;
import lk.ijse.blood.entity.BloodInventory;
import lk.ijse.blood.entity.NeederRequest;
import lk.ijse.blood.entity.RequestDetails;

import java.sql.SQLException;

public interface NeederRequestDAO extends CrudDAO<NeederRequest> {
//      boolean placeNeederRequest(NeederRequestDto neederRequestDto, BloodInventoryDto bagdto, RequestDetailsDto requestDetailsDto) throws SQLException, ClassNotFoundException ;
//
//      boolean updateNeederRequest(NeederRequestDto dto) throws SQLException, ClassNotFoundException ;
//      boolean saveNeederRequest(NeederRequestDto dto) throws SQLException, ClassNotFoundException ;
//      NeederRequestDto searchNeeReq(String txtNeeReq) throws SQLException, ClassNotFoundException ;
//      boolean deleteNeederRequest(String neeReq) throws SQLException, ClassNotFoundException ;
//
//      List<NeederRequestDto> loadAllNeederRequests() throws SQLException, ClassNotFoundException ;

  boolean placeNeederRequest(NeederRequest neederRequestDto, BloodInventory bagdto, RequestDetails requestDetailsDto) throws SQLException, ClassNotFoundException;
  }
