package com.edengardensigiriya.edengarden.dao.custom;

import com.edengardensigiriya.edengarden.dao.CrudDAO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.util.List;

public interface RentalDAO extends CrudDAO<Custom,String> {
    public String setNextIdValue(int number);
    public String searchCustomer(String custId);
    public String getPaymentId(String bookingId);
    public String getEmail(String id) throws SQLException;
    public String getBookingId();
    public boolean cancelRental(Custom entity) throws SQLException;
    void  updateStatus() throws SQLException;
    List<Custom> getAllBicycles() throws SQLException;
    List<Custom> getAllCars() throws SQLException;
    List<Custom> getVehicleId(String vehicle, String vehicleType);
}
