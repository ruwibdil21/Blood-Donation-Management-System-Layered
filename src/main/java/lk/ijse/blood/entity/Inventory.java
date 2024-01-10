package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Inventory{
    private String medical_id;
    private String date;
    private String bloodType;
}
