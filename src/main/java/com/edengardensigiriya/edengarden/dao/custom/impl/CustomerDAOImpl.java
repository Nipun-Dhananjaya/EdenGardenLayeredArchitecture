package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.CustomerDAO;
import com.edengardensigiriya.edengarden.entity.Customer;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM customer ORDER BY cust_id;");
        List<Customer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?);", dto.getCustId(), dto.getCustName(), dto.getCustNic(), dto.getCustEmail(), dto.getCustAddress(), dto.getCustContact(), dto.getCustGender());
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Customer> search(String idTxt) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM customer WHERE cust_id=?;",idTxt);
        List<Customer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="C-00000";
        try {
            result= CrudUtil.execute("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "C-"+num;
        } catch (SQLException e) {
            return "C-00000";
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
    public String getCustId() {
        ResultSet result=null;
        String id="";
        try {
            result= CrudUtil.execute("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }
}
