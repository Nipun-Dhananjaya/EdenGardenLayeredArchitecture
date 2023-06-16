package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.BookingDTO;
import com.edengardensigiriya.edengarden.dto.BookingUIDTO;
import com.edengardensigiriya.edengarden.dto.CarDTO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingBO extends SuperBO {
    List<BookingDTO> getAllBookings() throws SQLException, ClassNotFoundException;
    List<BookingUIDTO> searchBookings(String bookingId) throws SQLException, ClassNotFoundException;
    boolean saveBookings(BookingDTO bookingDTO) throws SQLException, ClassNotFoundException;

    boolean updateBookings(BookingDTO bookingDTO) throws SQLException, ClassNotFoundException;

    void updateStatus() throws SQLException;

    String searchCustomer(String custId);

    String newPayIdGenerate() throws SQLException, ClassNotFoundException;

    String newBookIdGenerate() throws SQLException, ClassNotFoundException;

    String getPaymentId(String bookingId);

    boolean cancelBookings(BookingDTO bookingDTO) throws SQLException;

    LocalDateTime getPaidDateTime(String bookingId);

    void setRoomNumbers(String room);

    String setSleepCount(String roomNo);

    String getBookingId();
}
