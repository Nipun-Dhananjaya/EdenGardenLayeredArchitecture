package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employer {
    private String empId;
    private String empName;
    private String nic;
    private String address;
    private String email;
    private String Contact;
    private String Dob;
    private String gender;
    private String jobRole;
    private String monthlySalary;
    private String enteredDate;
    private String resignedDate;
}
