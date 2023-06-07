package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.controller.ChangePacksFormController;
import com.edengardensigiriya.edengarden.dao.custom.QueryDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public void setArrayList() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet = CrudUtil.execute("SELECT cost_per_day FROM room;");
            ArrayList<String> tempList=new ArrayList<>();
            while (resultSet.next()) {
                tempList.add(resultSet.getString(1));
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (!(ChangePacksFormController.roomPrices.contains(tempList.get(i)))){
                    ChangePacksFormController.roomPrices.add(tempList.get(i));
                }else{
                    continue;
                }
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    @Override
    public String getSleepCount(String price) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet = CrudUtil.execute("SELECT bed_count FROM room WHERE cost_per_day=?;",price);
            ArrayList<String> tempList=new ArrayList<>();
            while (resultSet.next()) {
                tempList.add(resultSet.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            if(!tempList.isEmpty()){
                return tempList.get(0);
            }else{
                return "No Rooms Available";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return "No Rooms Available";
        }
    }

    @Override
    public boolean changePakage(String packageOldPrice, String packageNewPrice) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT room_No FROM room WHERE cost_per_day=?;",packageOldPrice);
        ArrayList<String> tempList=new ArrayList<>();
        boolean isUpdated=false;
        while (resultSet.next()) {
            tempList.add(resultSet.getString(1));
        }
        for (int i = 0; i < tempList.size(); i++) {
            isUpdated=CrudUtil.execute("UPDATE room SET cost_per_day=? WHERE room_No=?;",packageNewPrice,tempList.get(i));
        }
        if (isUpdated){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Integer getBookingCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT booking_id) FROM room_booking WHERE DATE(booking_made_date_time)=?;", LocalDate.now());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getCarRentCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT rent_id) FROM car_rental WHERE DATE(rental_made_date_time)=?;", LocalDate.now());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getBicycleCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT rent_id) FROM bicycle_rental WHERE DATE(rental_made_date_time)=?;",LocalDate.now());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getTransCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT pay_id) FROM payment WHERE DATE(pay_made_date)=? AND reason=?;", LocalDate.now(),"Transport");
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getMonthBookingCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT booking_id) FROM room_booking WHERE MONTH(booking_made_date_time)=?;", LocalDate.now().getMonthValue());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getMonthCarRentCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT rent_id) FROM car_rental WHERE MONTH(rental_made_date_time)=?;", LocalDate.now().getMonthValue());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getMonthBicycleRentCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT rent_id) FROM bicycle_rental WHERE MONTH(rental_made_date_time)=?;",LocalDate.now().getMonthValue());
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }

    @Override
    public Integer getMonthTransCount() throws SQLException {
        ResultSet result=null;
        int count=0;
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            result= CrudUtil.execute("SELECT COUNT(DISTINCT pay_id) FROM payment WHERE MONTH(pay_made_date)=? AND reason=?;", LocalDate.now().getMonthValue(),"Transport");
            if(result.next()) {
                count= Integer.parseInt(result.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            System.out.println(count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return count;
        }
    }
}
