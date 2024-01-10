package lk.ijse.blood.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDto {
    private String Att_id;
    private String Emp_id;
    private String Date;
    private String Status;

}
