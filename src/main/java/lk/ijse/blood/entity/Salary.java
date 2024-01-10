package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Salary {
    private String Salary_id;
    private String Employee_id;
    private Integer Amount;
    private String Month;
    private String Year;

}
