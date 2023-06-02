package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.SupplierDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.entity.Supplier;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM supplier ORDER BY supp_id;");
        List<Supplier> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Supplier dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?,?,?,?);", dto.getSuppId(),
                    dto.getSuppName(), dto.getSuppAddress(), dto.getSuppEmail(), dto.getSuppContact(),dto.getItemType(), LocalDate.parse(dto.getContactStartDate()),LocalDate.parse(dto.getContactEndDate()));
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
    public boolean update(Supplier dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE supplier SET supp_name=?,supp_address=?,supp_email=?,supp_contact=?,supp_items=?,contract_start_date=?,contract_end_date=? WHERE supp_id=?;", dto.getSuppName(), dto.getSuppAddress(), dto.getSuppEmail(), dto.getSuppContact(),dto.getItemType(), LocalDate.parse(dto.getContactStartDate()),LocalDate.parse(dto.getContactEndDate()), dto.getSuppId());
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
    public boolean delete(String suppId) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("DELETE FROM supplier WHERE supp_id=?;", suppId);
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
    public List<Supplier> search(String suppId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM supplier WHERE supp_id=?;",suppId);
        List<Supplier> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="S-00000";
        try {
            result= CrudUtil.execute("SELECT supp_id FROM supplier ORDER BY supp_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "S-"+num;
        } catch (SQLException e) {
            return "S-00000";
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
    public String getSuppId() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT supp_id FROM supplier ORDER BY supp_id DESC LIMIT 1");
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
