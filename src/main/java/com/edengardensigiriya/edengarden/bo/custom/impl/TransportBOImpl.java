package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.TransportBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.dao.custom.TransportDAO;
import com.edengardensigiriya.edengarden.dto.BookingDTO;
import com.edengardensigiriya.edengarden.dto.BookingUIDTO;
import com.edengardensigiriya.edengarden.dto.TransportDTO;
import com.edengardensigiriya.edengarden.dto.TransportUIDTO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportBOImpl implements TransportBO {
    TransportDAO transportDAO = (TransportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSPORT);
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public List<TransportDTO> getAllTransports() throws SQLException, ClassNotFoundException {
        List<TransportDTO> transportList = new ArrayList<>();

        for (Custom transport : transportDAO.getAll()) {
            transportList.add(new TransportDTO(
                    transport.getTransId(),
                    transport.getCustId(),
                    transport.getCustName(),
                    transport.getTransDateTime(),
                    transport.getDestination(),
                    transport.getTransCost(),
                    transport.getTransStatus()
            ));
        }
        return transportList;
    }

    @Override
    public List<TransportUIDTO> searchTransports(String bookingId) throws SQLException, ClassNotFoundException {
        List<TransportUIDTO> transportList = new ArrayList<>();
        for (Custom transport : transportDAO.search(bookingId)) {
            transportList.add(new TransportUIDTO(
                    transport.getTransId(),
                    transport.getCustId(),
                    transport.getCustName(),
                    transport.getTransDateTime(),
                    transport.getDestination(),
                    transport.getTransCost()
            ));
        }
        return transportList;
    }

    @Override
    public boolean saveTransports(TransportDTO transportDTO) throws SQLException, ClassNotFoundException {
        return transportDAO.save(new Custom(transportDTO.getTransId(), transportDTO.getCustId(), transportDTO.getDateTime(), transportDTO.getDestination(),
                transportDTO.getPaymentId(), transportDTO.getCost(),true));
    }

    @Override
    public boolean updateTransports(TransportDTO transportDTO) throws SQLException, ClassNotFoundException {
        return transportDAO.update(new Custom(transportDTO.getTransId(), transportDTO.getCustId(), transportDTO.getDateTime(), transportDTO.getPaymentId(),
                transportDTO.getDestination(), transportDTO.getCost(),0));
    }

    @Override
    public void updateStatus() throws SQLException {
        transportDAO.updateStatus();
    }

    @Override
    public String searchCustomer(String custId) {
        return transportDAO.searchCustomer(custId);
    }

    @Override
    public String newPayIdGenerate() throws SQLException, ClassNotFoundException {
        return paymentDAO.newIdGenerate();
    }

    @Override
    public String newTransIdGenerate() throws SQLException, ClassNotFoundException {
        return transportDAO.newIdGenerate();
    }

    @Override
    public String getPaymentId(String bookingId) {
        return transportDAO.getPaymentId(bookingId);
    }

    @Override
    public boolean cancelTransports(TransportDTO transportDTO) throws SQLException {
        return transportDAO.cancelTransport(new Custom(transportDTO.getTransId(), transportDTO.getPaymentId(), transportDTO.getStatus()));
    }

    @Override
    public String getEmail(String custId) throws SQLException {
        return transportDAO.getEmail(custId);
    }

    @Override
    public String getBookingId() {
        return transportDAO.getBookingId();
    }
}
