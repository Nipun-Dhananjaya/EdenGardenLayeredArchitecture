package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.RentalDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalDAOImpl implements RentalDAO {
    @Override
    public List<Custom> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Custom entity) throws SQLException, ClassNotFoundException {
        boolean isAddedVehicleRental = false;
        boolean isAddedVehicle = false;
        try {
            System.out.println(entity.getRentCost());
            boolean isAddedPayment = CrudUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?);", entity.getPaymentId(), Double.parseDouble(entity.getRentCost()), LocalDateTime.now(), "Rental","Paid");
            boolean isAddedRental = CrudUtil.execute("INSERT INTO rental VALUES (?,?,?,?,?,?);", entity.getRentId(), entity.getRentFrom(), String.valueOf(entity.getRentDuration()), entity.getPaymentId(), entity.getCustId(),"Active");
            if (entity.getVehicle().equals("Car")) {
                isAddedVehicleRental = CrudUtil.execute("INSERT INTO car_rental VALUES (?,?,?);", entity.getVehicleId(), entity.getRentId(), LocalDateTime.now());
                isAddedVehicle = CrudUtil.execute("UPDATE car SET status=? WHERE car_reg_num=?;", "Booked",entity.getVehicleId());
            } else {
                isAddedVehicleRental = CrudUtil.execute("INSERT INTO bicycle_rental VALUES (?,?,?);", entity.getVehicleId(), entity.getRentId(), LocalDateTime.now());
                isAddedVehicle = CrudUtil.execute("UPDATE bicycle SET status=? WHERE bicycle_id=?;", "Booked",entity.getVehicleId());
            }
            if (isAddedPayment & isAddedRental & isAddedVehicleRental & isAddedVehicle) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Custom entity) throws SQLException, ClassNotFoundException {
        try {
            boolean isAddedPayment = CrudUtil.execute("UPDATE payment SET paid_amount=? WHERE pay_id=?;", Double.parseDouble(entity.getRentCost()),entity.getPaymentId());
            boolean isAddedRental = CrudUtil.execute("UPDATE rental SET rental_takeover_date_time=?,rented_duration=? WHERE rental_id=?;", entity.getRentFrom(), String.valueOf(entity.getRentDuration()), entity.getRentId());
            System.out.println("payId:"+entity.getPaymentId());
            System.out.println("payCost:"+entity.getRentCost());
            System.out.println("pay:"+isAddedPayment);
            System.out.println("rent:"+isAddedRental);
            if (isAddedPayment & isAddedRental ) {
                System.out.println("updateAffected");
                return true;
            } else {
                System.out.println("updateNotAffected");
                return false;
            }
        } catch (SQLException e) {
            DBConnection.getInstance().getConnection().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Custom> search(String rentId) throws SQLException, ClassNotFoundException {
        String vehicle="";
        ResultSet vehi=CrudUtil.execute("SELECT * FROM car_rental WHERE rent_id=?;",rentId);
        ResultSet resultSet=null;
        if (vehi.next()){
            vehicle="Car";
        }else{
            vehicle="Bicycle";
        }
        if (vehicle.equals("Car")){
            resultSet=CrudUtil.execute("SELECT rental.rental_id,rental.customer_id,customer.cust_name,car.car_type,car_rental.car_reg_num,rental.rental_takeover_date_time,rental.rented_duration,payment.paid_amount FROM rental INNER JOIN car_rental ON car_rental.rent_id=rental.rental_id INNER JOIN payment ON payment.pay_id=rental.payment_id INNER JOIN customer ON customer.cust_id=rental.customer_id INNER JOIN car ON car.car_reg_num=car_rental.car_reg_num WHERE rental.rental_id=?;",rentId);
        }else{
            resultSet=CrudUtil.execute("SELECT rental.rental_id,rental.customer_id,customer.cust_name,bicycle.bicycle_type,bicycle_rental.bicycle_id,rental.rental_takeover_date_time,rental.rented_duration,payment.paid_amount FROM rental INNER JOIN bicycle_rental ON bicycle_rental.rent_id=rental.rental_id INNER JOIN payment ON payment.pay_id=rental.payment_id INNER JOIN customer ON customer.cust_id=rental.customer_id INNER JOIN bicycle ON bicycle.bicycle_id=bicycle_rental.bicycle_id WHERE rental.rental_id=?;",rentId);
        }
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    vehicle,
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    'A'
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result = null;
        String[] idParts;
        String id = "R-00000";
        try {
            result = CrudUtil.execute("SELECT rental_id FROM rental ORDER BY rental_id DESC LIMIT 1");
            if (result.next()) {
                id = result.getString(1);
            }
            idParts = id.split("-");
            int number = Integer.parseInt(idParts[1]);
            String num = setNextIdValue(++number);
            return "R-" + num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "R-00000";
        }
    }

    @Override
    public String setNextIdValue(int number) {
        String returnVal = "";
        int length = String.valueOf(number).length();
        if (length < stringLength) {
            int difference = stringLength - length;
            for (int i = 0; i < difference; i++) {
                returnVal += "0";
            }
            returnVal += String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }

    @Override
    public String searchCustomer(String custId) {
        String name;
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT cust_name FROM customer WHERE cust_id=?", custId);
            if (resultSet.next()) {
                name = resultSet.getString(1);
                return name;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getPaymentId(String bookingId) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT payment_id FROM rental WHERE rental_id=?",bookingId);
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
    public String getBookingId() {
        ResultSet result=null;
        String id="";
        try {
            result= CrudUtil.execute("SELECT rental_id FROM rental ORDER BY rental_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }

    @Override
    public boolean cancelRental(Custom entity) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAddedVehicle =false;
            boolean isAddedPayment = CrudUtil.execute("UPDATE payment SET status=? WHERE pay_id=?;", entity.getRentStatus(),entity.getPaymentId());
            boolean isAddedRental = CrudUtil.execute("UPDATE rental SET rent_status=? WHERE rental_id=?;", entity.getRentStatus(), entity.getRentId());
            if (entity.getVehicle().equals("Bicycle")) {
                isAddedVehicle = CrudUtil.execute("UPDATE bicycle SET status=? WHERE bicycle_id=?;", "Available", entity.getVehicleId());
            }else{
                isAddedVehicle = CrudUtil.execute("UPDATE car SET status=? WHERE car_reg_num=?;", "Available",entity.getVehicleId());
            }
            DBConnection.getInstance().getConnection().commit();
            if (isAddedPayment & isAddedRental & isAddedVehicle) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateStatus() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT rental.rental_id FROM rental INNER JOIN car_rental ON car_rental.rent_id=rental.rental_id WHERE rental.rental_takeover_date_time<? AND rental.rent_status!=?;", LocalDateTime.now(),"Cancelled");
            //ResultSet result=CrudUtil.execute("SELECT car_rental.car_reg_num FROM car_rental INNER JOIN rental ON car_rental.rent_id=rental.rental_id WHERE rental.rental_takeover_date_time<? AND rental.rent_status!=?;",LocalDateTime.now(),"Cancelled");
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            boolean isUpdated=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE rental SET rent_status=? WHERE rental_id=?;","End",tempIds.get(i));
                //isUpdated=CrudUtil.execute("UPDATE car SET car.status=? INNER JOIN car_rental ON car_rental.car_reg_num=car.car_reg_num INNER JOIN rental ON rental.rental_id=car_reg_num.rent_id  WHERE rental.rental_id=?;","End",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT rental.rental_id FROM rental INNER JOIN bicycle_rental ON bicycle_rental.rent_id=rental.rental_id WHERE rental.rental_takeover_date_time<? AND rental.rent_status!=?;",LocalDateTime.now(),"Cancelled");
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE rental SET rent_status=? WHERE rental_id=?;","End",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT car_rental.car_reg_num FROM car_rental INNER JOIN rental ON car_rental.rent_id=rental.rental_id WHERE rental.rental_takeover_date_time<?;",LocalDateTime.now());
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE car SET status=? WHERE car_reg_num=?;","Available",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet=CrudUtil.execute("SELECT bicycle_rental.bicycle_id FROM bicycle_rental INNER JOIN rental ON bicycle_rental.rent_id=rental.rental_id WHERE rental.rental_takeover_date_time<?;",LocalDateTime.now());
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE bicycle SET status=? WHERE bicycle_id=?;","Available",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    @Override
    public List<Custom> getAllBicycles() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT rental.rental_id,rental.customer_id,customer.cust_name,bicycle.bicycle_type, bicycle_rental.bicycle_id,rental.rental_takeover_date_time,rental.rented_duration,payment.paid_amount,rental.rent_status FROM rental INNER JOIN bicycle_rental ON rental.rental_id=bicycle_rental.rent_id INNER JOIN customer ON customer.cust_id=rental.customer_id INNER JOIN payment ON payment.pay_id=rental.payment_id INNER JOIN bicycle ON bicycle.bicycle_id=bicycle_rental.bicycle_id ORDER BY rental.rental_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    (float) 10.55
            ));
        }
        return data;
    }

    @Override
    public List<Custom> getAllCars() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT rental.rental_id,rental.customer_id,customer.cust_name,car.car_type, car_rental.car_reg_num,rental.rental_takeover_date_time,rental.rented_duration,payment.paid_amount,rental.rent_status FROM rental INNER JOIN car_rental ON rental.rental_id=car_rental.rent_id INNER JOIN customer ON customer.cust_id=rental.customer_id INNER JOIN payment ON payment.pay_id=rental.payment_id INNER JOIN car ON car.car_reg_num=car_rental.car_reg_num ORDER BY rental.rental_id;");
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    (float) 10.55
            ));
        }
        return data;
    }

    @Override
    public List<Custom> getVehicleId(String vehicle, String vehicleType) {
        ResultSet resultSet = null;
        List<Custom> vehicleIds=new ArrayList<>();
        try {
            if (vehicle.equals("Car")) {
                resultSet = CrudUtil.execute("SELECT car_reg_num FROM car WHERE car_type=? AND status!=?;", vehicleType,"Booked");
            }else{
                resultSet = CrudUtil.execute("SELECT bicycle_id FROM bicycle WHERE bicycle_type=? AND status!=?;", vehicleType,"Booked");
            }
            while(resultSet.next()){
                vehicleIds.add(new Custom(resultSet.getString(1)));
            }
            return vehicleIds;
        } catch (SQLException e) {
            return vehicleIds;
        }
    }
}
