package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.UserDAO;
import com.edengardensigiriya.edengarden.entity.User;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<User> search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean changePwd(User entity) {
        ResultSet result=null;
        String userId=null;
        if (entity.getPassword().equals(entity.getConfirmation())) {
            try {
                result = CrudUtil.execute("SELECT user_id FROM user WHERE user_name=? AND password=? AND job_role=?;", entity.getOldUserName(), entity.getOldPwd(), entity.getJobRoll());
                if (result.next()) {
                    userId = result.getString(1);
                }
                boolean IsAffected=CrudUtil.execute("UPDATE user SET user_name=?, password=? WHERE user_id=?", entity.getUserName(), entity.getPassword(), userId);
                if(IsAffected){
                    return true;
                }
                else{
                    return false;
                }
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean changeUser(User entity) {
        String userId="";
        try {
            ResultSet result = CrudUtil.execute("SELECT user_id FROM user WHERE user_name=? AND password=? AND job_role=?;",entity.getOldUserName(), entity.getOldPwd(), entity.getJobRoll());
            while (result.next()) {
                System.out.println("Inside");
                userId = result.getString(1);
            }

            System.out.println(userId);
            //System.out.println("res:   "+result.getString(1));
            boolean IsAffected=false;
            if (CrudUtil.execute("DELETE FROM user WHERE user_id=?;",userId)){
                System.out.println("Deleted");
                IsAffected=CrudUtil.execute("INSERT INTO user VALUES (?,?,?,?,?);",userId,entity.getEmpId(), entity.getUserName(), entity.getPassword(), entity.getJobRoll());
            }
            if(IsAffected){
                return true;
            }
            else{
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean IsCorrect(User entity) {
        ResultSet result = null;
        User user;
        try {
            result = CrudUtil.execute("SELECT user_name,password FROM user WHERE user_name=? AND password=? AND job_role=?;", entity.getUserName(), entity.getPassword(), entity.getJobRoll());
            if (result.next()) {
                user = new User(result.getString(1), result.getString(2));
            }else {
                return false;
            }
            if (user.getUserName().equals(entity.getUserName()) & user.getPassword().equals(entity.getPassword())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
