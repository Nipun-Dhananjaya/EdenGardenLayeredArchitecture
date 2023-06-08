package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.BicycleBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.BicycleDAO;
import com.edengardensigiriya.edengarden.dto.BicycleDTO;
import com.edengardensigiriya.edengarden.entity.Bicycle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BicycleBOImpl implements BicycleBO {
    BicycleDAO bicycleDAO= (BicycleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BICYCLE);

    @Override
    public List<BicycleDTO> getAllBicycles() throws SQLException, ClassNotFoundException {
        List<BicycleDTO> bicycleList = new ArrayList<>();
        for (Bicycle bicycle:bicycleDAO.getAll()) {
            bicycleList.add(new BicycleDTO(
                    bicycle.getBicycleNo(),
                    bicycle.getBrand(),
                    bicycle.getBicycleType(),
                    bicycle.getColour(),
                    bicycle.getStatus()

            ));
        }
        return bicycleList;
    }

    @Override
    public List<BicycleDTO> searchBicycles(String bicycleId) throws SQLException, ClassNotFoundException {
        List<BicycleDTO> bicycleList = new ArrayList<>();
        for (Bicycle bicycle:bicycleDAO.search(bicycleId)) {
            bicycleList.add(new BicycleDTO(
                    bicycle.getBicycleNo(),
                    bicycle.getBrand(),
                    bicycle.getBicycleType(),
                    bicycle.getColour(),
                    bicycle.getStatus()

            ));
        }
        return bicycleList;
    }

    @Override
    public boolean saveBicycles(BicycleDTO bicycle) throws SQLException, ClassNotFoundException {
        return bicycleDAO.save(new Bicycle(bicycle.getBicycleNo(), bicycle.getBrand(), bicycle.getBicycleType(), bicycle.getColour(), bicycle.getStatus()));
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return bicycleDAO.newIdGenerate();
    }

    @Override
    public boolean updateBicycles(BicycleDTO bicycle) throws SQLException, ClassNotFoundException {
        return bicycleDAO.update(new Bicycle(bicycle.getBicycleNo(), bicycle.getBrand(), bicycle.getBicycleType(), bicycle.getColour(), bicycle.getStatus()));
    }

    @Override
    public boolean deleteBicycle(String bicycleId) throws SQLException, ClassNotFoundException {
        return bicycleDAO.delete(bicycleId);
    }
}