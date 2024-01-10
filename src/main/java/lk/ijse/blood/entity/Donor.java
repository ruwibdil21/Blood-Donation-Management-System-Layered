package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Donor{
    private String d_id;
    private String firstName;
    private String lastName;
    private String dob;
    private String type;
    private int tel;
    private String lastDate;
}