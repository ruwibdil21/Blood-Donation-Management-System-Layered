package lk.ijse.blood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetailsTm {
    private String Order_id;
    private String Med_id;
    private String Description;
}
