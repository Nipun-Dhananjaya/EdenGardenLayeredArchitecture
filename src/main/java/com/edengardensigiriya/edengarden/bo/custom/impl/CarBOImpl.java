package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.CarBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.CarDAO;
import com.edengardensigiriya.edengarden.dto.CarDTO;
import com.edengardensigiriya.edengarden.entity.Car;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarBOImpl implements CarBO {
    CarDAO carDAO= (CarDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAR);
    @Override
    public List<CarDTO> getAllCars() throws SQLException, ClassNotFoundException {
        List<CarDTO> carList = new ArrayList<>();
        for (Car car: carDAO.getAll()) {
            carList.add(new CarDTO(
                    car.getRegNo(),
                    car.getBrand(),
                    car.getCarType(),
                    car.getColour(),
                    car.getStatus()
            ));
        }
        return carList;
    }

    @Override
    public List<CarDTO> searchCars(String carReg) throws SQLException, ClassNotFoundException {
        List<CarDTO> carList = new ArrayList<>();
        for (Car car: carDAO.search(carReg)) {
            carList.add(new CarDTO(
                    car.getRegNo(),
                    car.getBrand(),
                    car.getCarType(),
                    car.getColour(),
                    car.getStatus()
            ));
        }
        return carList;
    }

    @Override
    public boolean saveCars(CarDTO carDTO) throws SQLException, ClassNotFoundException {
        return carDAO.save(new Car(carDTO.getRegNo(), carDTO.getBrand(), carDTO.getCarType(), carDTO.getColour(), carDTO.getStatus()));
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return carDAO.newIdGenerate();
    }

    @Override
    public boolean updateCars(CarDTO carDTO) throws SQLException, ClassNotFoundException {
        return carDAO.update(new Car(carDTO.getRegNo(), carDTO.getBrand(), carDTO.getCarType(), carDTO.getColour(), carDTO.getStatus()));
    }

    @Override
    public boolean deleteCar(String carReg) throws SQLException, ClassNotFoundException {
        return carDAO.delete(carReg);
    }
}
