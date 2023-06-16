package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.BookingDTO;
import com.edengardensigiriya.edengarden.dto.BookingUIDTO;
import com.edengardensigiriya.edengarden.dto.TransportDTO;
import com.edengardensigiriya.edengarden.dto.TransportUIDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransportBO extends SuperBO {
    List<TransportDTO> getAllTransports() throws SQLException, ClassNotFoundException;
    List<TransportUIDTO> searchTransports(String bookingId) throws SQLException, ClassNotFoundException;
    boolean saveTransports(TransportDTO transportDTO) throws SQLException, ClassNotFoundException;

    boolean updateTransports(TransportDTO transportDTO) throws SQLException, ClassNotFoundException;

    void updateStatus() throws SQLException;

    String searchCustomer(String custId);

    String newPayIdGenerate() throws SQLException, ClassNotFoundException;

    String newTransIdGenerate() throws SQLException, ClassNotFoundException;

    String getPaymentId(String bookingId);

    boolean cancelTransports(TransportDTO transportDTO) throws SQLException;

    String getBookingId();
}
