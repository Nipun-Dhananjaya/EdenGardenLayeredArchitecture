package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.TransportDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportDAOImpl implements TransportDAO {
    @Override
    public List<Custom> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT transport.trans_id,customer.cust_id,customer.cust_name," +
                "transport.booked_date_time,transport.destination,payment.paid_amount,transport.trans_status FROM transport INNER JOIN customer" +
                " ON customer.cust_id=transport.customer_id INNER JOIN payment ON payment.pay_id=transport.payment_id ORDER BY transport.trans_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Custom entity) throws SQLException, ClassNotFoundException {
        try {
            boolean isAddedPayment=CrudUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?);",entity.getPaymentId(),Double.parseDouble(entity.getTransCost()),LocalDateTime.now(),"Transport","Paid");
            boolean isAddedTransport=CrudUtil.execute("INSERT INTO transport VALUES(?,?,?,?,?,?);",entity.getTransId(),LocalDateTime.parse(entity.getTransDateTime()),entity.getDestination(),entity.getPaymentId(),entity.getCustId(),"Active");
            if (isAddedTransport && isAddedPayment){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Custom entity) throws SQLException, ClassNotFoundException {
        boolean isAffectedToPayment = CrudUtil.execute("UPDATE payment SET paid_amount=? WHERE pay_id=?;",  Double.parseDouble(entity.getTransCost()),entity.getPaymentId());
        boolean isAffectedToTransport = CrudUtil.execute("UPDATE transport SET booked_date_time=?,destination=? WHERE trans_id=?;", LocalDateTime.parse(entity.getTransDateTime()), entity.getDestination(),entity.getTransId());

        return (isAffectedToTransport & isAffectedToPayment) ? true:false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Custom> search(String transId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT transport.trans_id,transport.customer_id,customer.cust_name,transport.booked_date_time,transport.destination,payment.paid_amount FROM transport INNER JOIN payment ON payment.pay_id=transport.payment_id INNER JOIN customer ON customer.cust_id=transport.customer_id WHERE transport.trans_id=?;",transId);

        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    'A'
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="T-00000";
        try {
            result= CrudUtil.execute("SELECT trans_id FROM transport ORDER BY trans_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "T-"+num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "T-00000";
        }
    }

    @Override
    public String setNextIdValue(int number) {
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

    @Override
    public String searchCustomer(String custId) {
        String name;
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT cust_name FROM customer WHERE cust_id=?",custId);
            if (resultSet.next()){
                name= resultSet.getString(1);
                return name;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getPaymentId(String transId) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT payment_id FROM transport WHERE trans_id=?",transId);
            String paymentId=null;
            if(resultSet.next()){
                paymentId=resultSet.getString(1);
            }
            return paymentId;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getEmail(String id) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT cust_email FROM customer WHERE cust_id=?;",id);
            String tempIds="";
            while (resultSet.next()){
                tempIds=resultSet.getString(1);
            }
            DBConnection.getInstance().getConnection().commit();
            return tempIds;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return "";
        }
    }

    @Override
    public String getBookingId() {
        ResultSet result=null;
        String id="";
        try {
            result= CrudUtil.execute("SELECT trans_id FROM transport ORDER BY trans_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }

    @Override
    public boolean cancelTransport(Custom entity) throws SQLException {
        boolean isAffectedToPayment = CrudUtil.execute("UPDATE payment SET status=? WHERE pay_id=?;", entity.getTransStatus(),entity.getPaymentId());
        boolean isAffectedToTransport = CrudUtil.execute("UPDATE transport SET trans_status=? WHERE trans_id=? ;",entity.getTransStatus(), entity.getTransId());

        return (isAffectedToTransport & isAffectedToPayment) ? true:false;
    }

    @Override
    public void updateStatus() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT trans_id FROM transport WHERE booked_date_time<? AND trans_status!=?;", LocalDateTime.now(),"Cancelled");
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE transport SET trans_status=? WHERE trans_id=?;","End",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
