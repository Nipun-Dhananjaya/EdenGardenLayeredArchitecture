package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.EmployerDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.EmployerDTO;
import com.edengardensigiriya.edengarden.entity.Employer;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployerDAOImpl implements EmployerDAO {
    @Override
    public List<Employer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM employer ORDER BY emp_id;");
        List<Employer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Employer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Employer dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected = CrudUtil.execute("INSERT INTO employer VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", dto.getEmpId(), dto.getEmpName(),
                    dto.getNic(), dto.getAddress(), dto.getEmail(), dto.getContact(), dto.getDob(), dto.getGender(), dto.getJobRole(), dto.getMonthlySalary(), dto.getEnteredDate(), dto.getResignedDate());
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Employer dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE employer SET emp_name=?,emp_nic=?,emp_address=?,emp_email=?,emp_contact=?,emp_dob=?,gender=?,job_role=?,monthly_salary=?,entered_date=?,service_end_date=? WHERE emp_id=?;", dto.getEmpName(),
                    dto.getNic(), dto.getAddress(), dto.getEmail(), dto.getContact(), dto.getDob(), dto.getGender(), dto.getJobRole(), dto.getMonthlySalary(), dto.getEnteredDate(), dto.getResignedDate(),dto.getEmpId());
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Employer> search(String empId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM employer WHERE emp_id=?;",empId);
        List<Employer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Employer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="E-00000";
        try {
            result= CrudUtil.execute("SELECT emp_id FROM employer ORDER BY emp_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "E-"+num;
        } catch (SQLException e) {
            return "E-00000";
        }
    }

    @Override
    public boolean addContact(String tele) {
        if (!contact.contains(tele)){
            contact.add(tele);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String setNextIdValue(int number) {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<stringLength){
            int difference=stringLength-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }

    @Override
    public String getEmpId() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT emp_id FROM employer ORDER BY emp_id DESC LIMIT 1;");
            String tempIds="";
            while (resultSet.next()){
                tempIds=resultSet.getString(1);
            }
            DBConnection.getInstance().getConnection().commit();
            return tempIds;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return "";
        }
    }
}
