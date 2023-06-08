package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    List<PaymentDTO> getAllTransportPayments() throws SQLException;
    List<PaymentDTO> getAllBookingPayments() throws SQLException;
    List<PaymentDTO> getAllRentalPayments() throws SQLException;
}
