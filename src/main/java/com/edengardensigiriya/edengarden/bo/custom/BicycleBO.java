package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.BicycleDTO;
import com.edengardensigiriya.edengarden.entity.Bicycle;

import java.sql.SQLException;
import java.util.List;

public interface BicycleBO extends SuperBO {
    List<BicycleDTO> getAllBicycles() throws SQLException, ClassNotFoundException;
    List<BicycleDTO> searchBicycles(String bicycleId) throws SQLException, ClassNotFoundException;
    boolean saveBicycles(BicycleDTO bicycle) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean updateBicycles(BicycleDTO bicycle) throws SQLException, ClassNotFoundException;

    boolean deleteBicycle(String bicycleId) throws SQLException, ClassNotFoundException;
}
