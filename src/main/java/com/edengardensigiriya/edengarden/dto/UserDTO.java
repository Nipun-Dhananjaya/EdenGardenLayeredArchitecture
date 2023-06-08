package com.edengardensigiriya.edengarden.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String userId;
    private String empId;
    private String userName;
    private String password;
    private String jobRoll;
    private String oldUserName;
    private String oldPwd;
    private String confirmation;

    //Change Pwd
    public UserDTO(String oldUserName, String oldPwd, String userName, String password, String confirmation, String jobRoll){
        this.oldUserName=oldUserName;
        this.oldPwd=oldPwd;
        this.userName=userName;
        this.password=password;
        this.confirmation=confirmation;
        this.jobRoll=jobRoll;
    }
    //Change User
    public UserDTO(String oldUserName, String oldPwd, String empId,String userName, String password, String jobRoll,int a){
        this.oldUserName=oldUserName;
        this.oldPwd=oldPwd;
        this.empId=empId;
        this.userName=userName;
        this.password=password;
        this.jobRoll=jobRoll;
    }
    //Is Correct
    public UserDTO(String userName, String password, String jobRoll){
        this.userName=userName;
        this.password=password;
        this.jobRoll=jobRoll;
    }
    public UserDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
