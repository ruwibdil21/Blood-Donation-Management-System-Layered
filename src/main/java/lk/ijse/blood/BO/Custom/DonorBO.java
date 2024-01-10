package lk.ijse.blood.bo.Custom;

import lk.ijse.blood.bo.SuperBO;
import lk.ijse.blood.dto.DonorDto;

import java.sql.SQLException;
import java.util.List;

public interface DonorBO extends SuperBO {
    List<DonorDto> loadAllDonor() throws SQLException, ClassNotFoundException;
    boolean deleteDonor(String id) throws SQLException, ClassNotFoundException;
    DonorDto searchDonor(String id) throws SQLException, ClassNotFoundException ;
    boolean saveDonor(DonorDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateDonor(DonorDto dto) throws SQLException, ClassNotFoundException;
    String generateDId() throws SQLException,ClassNotFoundException;
}
