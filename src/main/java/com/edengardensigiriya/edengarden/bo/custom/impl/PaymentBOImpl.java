package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.PaymentBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.dto.PaymentDTO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public List<PaymentDTO> getAllTransportPayments() throws SQLException {
        List<PaymentDTO> paymentList =new ArrayList<>();
        for (Custom payment : paymentDAO.getAllTransportPay()) {
            paymentList.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getCustId(),
                    payment.getPaidDateTime(),
                    payment.getPaidReason(),
                    payment.getPayment(),
                    payment.getPaymentStatus()
            ));
        }
        return paymentList;
    }

    @Override
    public List<PaymentDTO> getAllBookingPayments() throws SQLException {
        List<PaymentDTO> paymentList = new ArrayList<>();

        for (Custom payment : paymentDAO.getAllBookingPay()) {
            paymentList.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getCustId(),
                    payment.getPaidDateTime(),
                    payment.getPaidReason(),
                    payment.getPayment(),
                    payment.getPaymentStatus()
            ));
        }
        return paymentList;
    }

    @Override
    public List<PaymentDTO> getAllRentalPayments() throws SQLException {
        List<PaymentDTO> paymentList = new ArrayList<>();

        for (Custom payment : paymentDAO.getAllRentalPay()) {
            paymentList.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getCustId(),
                    payment.getPaidDateTime(),
                    payment.getPaidReason(),
                    payment.getPayment(),
                    payment.getPaymentStatus()
            ));
        }
        return paymentList;
    }
}
