package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;

import java.sql.SQLException;

public interface HomeBO extends SuperBO {
    Integer getBookingCount() throws SQLException;
    Integer getCarRentCount() throws SQLException;
    Integer getBicycleCount() throws SQLException;
    Integer getTransCount() throws SQLException;
    Integer getMonthBookingCount() throws SQLException;
    Integer getMonthCarRentCount() throws SQLException;
    Integer getMonthBicycleRentCount() throws SQLException;
    Integer getMonthTransCount() throws SQLException;
}
