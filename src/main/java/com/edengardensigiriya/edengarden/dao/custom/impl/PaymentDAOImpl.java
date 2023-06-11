package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.entity.Payment;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public List<Custom> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Custom entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Custom entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Custom> search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="P-00000";
        try {
            result= CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "P-"+num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "P-00000";
        }
    }

    @Override
    public List<Custom> getAllRentalPay() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT payment.pay_id,rental.customer_id,payment.pay_made_date, payment.reason,payment.paid_amount,payment.status FROM payment INNER JOIN rental ON payment.pay_id=rental.payment_id ORDER BY payment.pay_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    @Override
    public List<Custom> getAllBookingPay() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT payment.pay_id,booking.customer_id,payment.pay_made_date, payment.reason,payment.paid_amount,payment.status FROM payment INNER JOIN booking ON payment.pay_id=booking.payment_id ORDER BY payment.pay_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    @Override
    public List<Custom> getAllTransportPay() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT payment.pay_id,transport.customer_id,payment.pay_made_date, payment.reason,payment.paid_amount,payment.status FROM payment INNER JOIN transport ON payment.pay_id=transport.payment_id ORDER BY payment.pay_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    @Override
    public String setNextIdValue(int number) throws SQLException {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<stringLength){
            int difference=stringLength-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }
}
