package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {
    private String sup_id;
    private String user_id;
    private String name;
    private String address;
    private String tel;
}