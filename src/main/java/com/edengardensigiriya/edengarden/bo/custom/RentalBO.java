package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.RentalDTO;
import com.edengardensigiriya.edengarden.dto.RentalUIDTO;

import java.sql.SQLException;
import java.util.List;

public interface RentalBO extends SuperBO {
    List<RentalDTO> getAllCarRentals() throws SQLException, ClassNotFoundException;
    List<RentalDTO> getAllBicycleRentals() throws SQLException, ClassNotFoundException;
    List<RentalUIDTO> searchRentals(String RentalId) throws SQLException, ClassNotFoundException;
    boolean saveRentals(RentalDTO RentalDTO) throws SQLException, ClassNotFoundException;

    boolean updateRentals(RentalDTO RentalDTO) throws SQLException, ClassNotFoundException;

    void updateStatus();

    String searchCustomer(String custId);

    String newPayIdGenerate() throws SQLException, ClassNotFoundException;

    String newRentIdGenerate() throws SQLException, ClassNotFoundException;

    String getPaymentId(String RentalId);

    boolean cancelRentals(RentalDTO RentalDTO) throws SQLException;

    String getEmail(String custId) throws SQLException;

    String getRentalId();
    List<RentalDTO> loadAllAvailableVehicles(String vehicle,String vehicleType) throws SQLException, ClassNotFoundException;

}
