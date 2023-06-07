package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.SuperDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public interface QueryDAO extends SuperDAO {
    void setArrayList() throws SQLException;
    String getSleepCount(String price) throws SQLException;
    boolean changePakage(String packageOldPrice,String packageNewPrice) throws SQLException;
    Integer getBookingCount() throws SQLException;
    Integer getCarRentCount() throws SQLException;
    Integer getBicycleCount() throws SQLException;
    Integer getTransCount() throws SQLException;
    Integer getMonthBookingCount() throws SQLException;
    Integer getMonthCarRentCount() throws SQLException;
    Integer getMonthBicycleRentCount() throws SQLException;
    Integer getMonthTransCount() throws SQLException;
}
