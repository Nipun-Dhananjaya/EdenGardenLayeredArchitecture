package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.ItemDAO;
import com.edengardensigiriya.edengarden.entity.Item;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM item ORDER BY item_code;");
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("INSERT INTO item VALUES(?,?);", dto.getItemCode(),dto.getItemDescription());
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
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE item SET description=? WHERE item_code=?;", dto.getItemDescription(), dto.getItemCode());
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
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        try {
            boolean isAffected =CrudUtil.execute("DELETE FROM item WHERE item_code=?;", itemCode);
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
    public List<Item> search(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM item WHERE item_code=?;",itemCode);
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="Item-00000";
        try {
            result= CrudUtil.execute("SELECT item_code FROM item ORDER BY item_code DESC LIMIT 1;");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "Item-"+num;
        } catch (SQLException e) {
            return "Item-00000";
        }
    }

    @Override
    public List<Item> getAllDescription() throws SQLException {
        ResultSet resultSet= CrudUtil.execute("SELECT description FROM item;");
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1)
            ));
        }
        return data;
    }

    @Override
    public String setNextIdValue(int number) throws SQLException {
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
}
