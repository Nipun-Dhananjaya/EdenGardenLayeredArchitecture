package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.RentalBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.dao.custom.RentalDAO;
import com.edengardensigiriya.edengarden.dto.BookingDTO;
import com.edengardensigiriya.edengarden.dto.BookingUIDTO;
import com.edengardensigiriya.edengarden.dto.RentalDTO;
import com.edengardensigiriya.edengarden.dto.RentalUIDTO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalBOImpl implements RentalBO {
    RentalDAO rentalDAO= (RentalDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RENTAL);
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);


    @Override
    public List<RentalDTO> getAllCarRentals() throws SQLException, ClassNotFoundException {
        List<RentalDTO> rentalList = new ArrayList<>();

        for (Custom rental : rentalDAO.getAllCars()) {
            rentalList.add(new RentalDTO(
                    rental.getRentId(),
                    rental.getCustId(),
                    rental.getCustName(),
                    rental.getVehicleType(),
                    rental.getVehicleId(),
                    rental.getRentFrom(),
                    rental.getRentDuration(),
                    rental.getRentCost(),
                    rental.getRentStatus()
            ));
        }
        return rentalList;
    }

    @Override
    public List<RentalDTO> getAllBicycleRentals() throws SQLException, ClassNotFoundException {
        List<RentalDTO> rentalList = new ArrayList<>();

        for (Custom rental : rentalDAO.getAllBicycles()) {
            rentalList.add(new RentalDTO(
                    rental.getRentId(),
                    rental.getCustId(),
                    rental.getCustName(),
                    rental.getVehicleType(),
                    rental.getVehicleId(),
                    rental.getRentFrom(),
                    rental.getRentDuration(),
                    rental.getRentCost(),
                    rental.getRentStatus()
            ));
        }
        return rentalList;
    }

    @Override
    public List<RentalUIDTO> searchRentals(String RentalId) throws SQLException, ClassNotFoundException {
        List<RentalUIDTO> rentList = new ArrayList<>();

        for (Custom rental : rentalDAO.search(RentalId)) {
            rentList.add(new RentalUIDTO(
                    rental.getRentId(),
                    rental.getCustId(),
                    rental.getCustName(),
                    rental.getVehicle(),
                    rental.getVehicleType(),
                    rental.getVehicleId(),
                    rental.getRentFrom(),
                    rental.getRentDuration(),
                    rental.getRentCost()
            ));
        }
        return rentList;
    }

    @Override
    public boolean saveRentals(RentalDTO rentalDTO) throws SQLException, ClassNotFoundException {
        return rentalDAO.save(new Custom(rentalDTO.getRentId(), rentalDTO.getCustId(), rentalDTO.getRentFrom(), rentalDTO.getRentDuration(), rentalDTO.getPaymentId(), rentalDTO.getRentCost(),
                rentalDTO.getVehicle(),rentalDTO.getVehicleType(),
                rentalDTO.getVehicleId(),0,true));
    }

    @Override
    public boolean updateRentals(RentalDTO rentalDTO) throws SQLException, ClassNotFoundException {
        return rentalDAO.update(new Custom(rentalDTO.getRentId(), rentalDTO.getCustId(), rentalDTO.getRentFrom(), rentalDTO.getRentDuration(), rentalDTO.getPaymentId(),
                rentalDTO.getVehicle(),rentalDTO.getVehicleType(),
                rentalDTO.getVehicleId(), rentalDTO.getRentCost(),true));
    }

    @Override
    public void updateStatus() {

    }

    @Override
    public String searchCustomer(String custId) {
        return rentalDAO.searchCustomer(custId);
    }

    @Override
    public String newPayIdGenerate() throws SQLException, ClassNotFoundException {
        return paymentDAO.newIdGenerate();
    }

    @Override
    public String newRentIdGenerate() throws SQLException, ClassNotFoundException {
        return rentalDAO.newIdGenerate();
    }

    @Override
    public String getPaymentId(String RentalId) {
        return null;
    }

    @Override
    public boolean cancelRentals(RentalDTO rentalDTO) throws SQLException {
        return rentalDAO.cancelRental(new Custom(rentalDTO.getRentId(), rentalDTO.getCustId(),rentalDTO.getVehicle(),
                rentalDTO.getVehicleId(), rentalDTO.getPaymentId(),rentalDTO.getRentStatus(),(float) 10.5));
    }

    @Override
    public String getEmail(String custId) throws SQLException {
        return rentalDAO.getEmail(custId);
    }

    @Override
    public String getRentalId() {
        return rentalDAO.getBookingId();
    }

    @Override
    public List<RentalDTO> loadAllAvailableVehicles(String vehicle,String vehicleType) throws SQLException, ClassNotFoundException {
        List<RentalDTO> resultSet=new ArrayList<>();
        for (Custom custom:rentalDAO.getVehicleId(vehicle,vehicleType)) {
            resultSet.add(new RentalDTO(
                    custom.getVehicleId()
            ));
        }
        return resultSet;
    }
}
