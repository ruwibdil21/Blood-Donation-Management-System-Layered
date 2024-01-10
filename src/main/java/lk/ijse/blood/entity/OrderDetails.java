package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class OrderDetails {
    private String Order_id;
    private String Med_id;
    private String Description;

}

