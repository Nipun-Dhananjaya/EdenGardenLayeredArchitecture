package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.CarDAO;
import com.edengardensigiriya.edengarden.entity.Car;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    @Override
    public List<Car> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM car ORDER BY car_reg_num;");
        List<Car> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Car(
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
    public boolean save(Car dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isAdded= CrudUtil.execute("INSERT INTO car VALUES (?,?,?,?,?);",dto.getRegNo(),dto.getBrand(),dto.getCarType(),dto.getColour(),"Available");
            if (isAdded){
                return true;
            }else{
                System.out.println(123);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Car dto) throws SQLException, ClassNotFoundException {
        try {
            boolean isUpdated= CrudUtil.execute("UPDATE car SET brand=?,car_type=?,colour=? WHERE car_reg_num=?;",dto.getBrand(),dto.getCarType(),dto.getColour(),dto.getRegNo());
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
    public boolean delete(String regNo) throws SQLException, ClassNotFoundException {
        try {
            boolean isDeleted= CrudUtil.execute("DELETE FROM car WHERE car_reg_num=?;",regNo);
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
    public List<Car> search(String regNo) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM car WHERE car_reg_num=?;",regNo);
        List<Car> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Car(
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
        return null;
    }
}
