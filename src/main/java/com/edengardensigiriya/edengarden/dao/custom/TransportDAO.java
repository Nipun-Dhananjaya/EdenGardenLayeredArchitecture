package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;

public interface TransportDAO extends CrudDAO<Custom,String> {
    public String setNextIdValue(int number);
    public String searchCustomer(String custId);
    public String getPaymentId(String bookingId);
    public String getBookingId();
    public boolean cancelTransport(Custom entity) throws SQLException;
    void  updateStatus() throws SQLException;
}
