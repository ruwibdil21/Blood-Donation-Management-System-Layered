package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierOrders {
    private String supOrder_id;
    private String supplier_id;
    private String date;
    private String amount;
}