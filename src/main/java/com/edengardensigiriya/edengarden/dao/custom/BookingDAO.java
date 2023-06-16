package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface BookingDAO extends CrudDAO<Custom,String> {
    public static ArrayList<String> deluxeRoomNo = new ArrayList<>();
    public static ArrayList<String> standardRoomNo = new ArrayList<>();
    public String setNextIdValue(int number);
    public String searchCustomer(String custId);
    public void setRoomNumbers(String room_Type);
    public String setSleepCount(String roomNo);
    public String getPaymentId(String bookingId);
    public LocalDateTime getPaidDateTime(String bookingId);
    public String getBookingId();
    public boolean cancelBooking(Custom entity) throws SQLException;
    void  updateStatus() throws SQLException;
}
