package lk.ijse.blood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Employee {
    private String Emp_id;
    private String user_id;
    private String Name;
    private String Address;
    private String Role;
    private String DOB;


}

