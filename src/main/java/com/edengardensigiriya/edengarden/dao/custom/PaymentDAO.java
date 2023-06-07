package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Custom,String> {
    List<Custom> getAllRentalPay() throws SQLException;
    List<Custom> getAllBookingPay() throws SQLException;
    List<Custom> getAllTransportPay() throws SQLException;
    String setNextIdValue(int number) throws SQLException;
}
