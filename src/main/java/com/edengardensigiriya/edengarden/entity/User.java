package com.edengardensigiriya.edengarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private String userId;
    private String empId;
    private String userName;
    private String password;
    private String jobRoll;
    private String oldUserName;
    private String oldPwd;
    private String confirmation;

    //Change Pwd
    public User(String oldUserName, String oldPwd, String userName, String password, String confirmation, String jobRoll){
        this.oldUserName=oldUserName;
        this.oldPwd=oldPwd;
        this.userName=userName;
        this.password=password;
        this.confirmation=confirmation;
        this.jobRoll=jobRoll;
    }
    //Change User
    public User(String oldUserName, String oldPwd, String empId,String userName, String password, String jobRoll,int a){
        this.oldUserName=oldUserName;
        this.oldPwd=oldPwd;
        this.empId=empId;
        this.userName=userName;
        this.password=password;
        this.jobRoll=jobRoll;
    }
    //Is Correct
    public User(String userName, String password, String jobRoll){
        this.userName=userName;
        this.password=password;
        this.jobRoll=jobRoll;
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
