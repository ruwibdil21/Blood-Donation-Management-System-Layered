package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDetails {
    private String neeReq_id;
    private String bloodBagId;
    private String description;

}

