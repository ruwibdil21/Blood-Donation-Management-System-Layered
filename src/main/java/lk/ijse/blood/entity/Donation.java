package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Donation {
    private String Do_id;
    private String D_id;
    private String date;
    private String Blood_type;
    private String Hemoglobin_level;
}