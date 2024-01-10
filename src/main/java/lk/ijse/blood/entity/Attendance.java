package lk.ijse.blood.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attendance {
    private String Att_id;
    private String Emp_id;
    private String Date;
    private String Status;

}
