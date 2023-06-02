package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.RoomDAO;
import com.edengardensigiriya.edengarden.entity.Room;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT room_No,room_type,bed_count,cost_per_day,status FROM room ORDER BY room_No;");
        List<Room> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Room(
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
    public boolean save(Room dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAdded=CrudUtil.execute("INSERT INTO room VALUES (?,?,?,?,?);",dto.getRoomNo(),dto.getRoomType(),Integer.parseInt(dto.getSleepCount()),Double.parseDouble(dto.getCostPerDay()),"Available");
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
    public boolean update(Room dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isUpdated=CrudUtil.execute("UPDATE room SET room_type=?,bed_count=?,cost_per_day=? WHERE room_No=?;",dto.getRoomType(),Integer.parseInt(dto.getSleepCount()),Double.parseDouble(dto.getCostPerDay()),dto.getRoomNo());
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
    public boolean delete(String roomNo) throws SQLException, ClassNotFoundException {
        try {
            boolean isRemoved=CrudUtil.execute("DELETE FROM room WHERE room_No=?;",roomNo);
            if (isRemoved){
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
    public List<Room> search(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM room;");
        List<Room> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String id="0010";
        try {
            result= CrudUtil.execute("SELECT room_no FROM room ORDER BY room_no DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            int number=Integer.parseInt(id);
            String num=setNextIdValue(++number);
            return num;
        } catch (SQLException e) {
            return "0010";
        }
    }

    @Override
    public String setNextIdValue(int number) {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<4){
            int difference=4-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }
}
