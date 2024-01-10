package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NeederRequest {
    private String neeReq_id;
    private String neederId;
    private String date;
    private String amount;
}

