package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.BicycleDTO;
import com.edengardensigiriya.edengarden.dto.CarDTO;

import java.sql.SQLException;
import java.util.List;

public interface CarBO extends SuperBO {
    List<CarDTO> getAllCars() throws SQLException, ClassNotFoundException;
    List<CarDTO> searchCars(String carReg) throws SQLException, ClassNotFoundException;
    boolean saveCars(CarDTO carDTO) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean updateCars(CarDTO carDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCar(String carReg) throws SQLException, ClassNotFoundException;
}
