package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.BicycleDAO;
import com.edengardensigiriya.edengarden.entity.Bicycle;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BicycleDAOImpl implements BicycleDAO {
    @Override
    public List<Bicycle> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM bicycle ORDER BY bicycle_id;");
        List<Bicycle> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Bicycle(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Bicycle dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAdded= CrudUtil.execute("INSERT INTO bicycle VALUES (?,?,?,?,?);",dto.getBicycleNo(),dto.getBrand(),dto.getBicycleType(),dto.getColour(),"Available");
            if (isAdded){
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
    public boolean update(Bicycle dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isUpdated= CrudUtil.execute("UPDATE bicycle SET brand=?,bicycle_type=?,colour=? WHERE bicycle_id=?;",dto.getBrand(),dto.getBicycleType(),dto.getColour(),dto.getBicycleNo());
            if (isUpdated){
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
    public boolean delete(String bicycleNo) throws SQLException, ClassNotFoundException {
        try {
            boolean isDeleted= CrudUtil.execute("DELETE FROM bicycle WHERE bicycle_id=?;",bicycleNo);
            if (isDeleted){
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
    public List<Bicycle> search(String bicycleNo) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM bicycle WHERE bicycle_id=?;",bicycleNo);
        List<Bicycle> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Bicycle(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="Bike-000";
        try {
            result= CrudUtil.execute("SELECT bicycle_id FROM bicycle ORDER BY bicycle_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "Bike-"+num;
        } catch (SQLException e) {
            return "Bike-000";
        }
    }

    @Override
    public String setNextIdValue(int number) {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<3){
            int difference=3-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }
}
