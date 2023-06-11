package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.BookingDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    @Override
    public List<Custom> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT booking.booking_id,booking.customer_id,customer.cust_name,room_booking.room_No, " +
                "booking.booked_date_time,booking.booking_duration,payment.paid_amount,room_booking.booking_made_date_time," +
                "room_booking.availability FROM booking INNER JOIN room_booking ON booking.booking_id=room_booking.booking_id " +
                "INNER JOIN customer ON customer.cust_id=booking.customer_id INNER JOIN payment ON payment.pay_id=booking.payment_id ORDER BY booking.booking_id;");
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
                    resultSet.getString(9)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Custom dto) throws SQLException, ClassNotFoundException {
        boolean isAffectedToPayment = CrudUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?);", dto.getPaymentId(), Double.parseDouble(dto.getRoomBookingCost()), LocalDateTime.now(),"Room Booking","Paid");
        boolean isAffectedToBooking = CrudUtil.execute("INSERT INTO booking VALUES(?,?,?,?,?);", dto.getBookingId(), dto.getBookFrom(), String.valueOf(dto.getDuration()), dto.getPaymentId(),dto.getCustId());
        boolean isAffectedToRoomBooking = CrudUtil.execute("INSERT INTO room_booking VALUES(?,?,?,?);", dto.getRoomNo(), dto.getBookingId(), LocalDateTime.now(),dto.getRoomAvailability());
        boolean isAffectedToRoom = CrudUtil.execute("UPDATE room SET status=? WHERE room_No=?;","Booked" ,dto.getRoomNo());

        return (isAffectedToBooking &isAffectedToRoomBooking & isAffectedToPayment & isAffectedToRoom) ? true:false;
    }

    @Override
    public boolean update(Custom dto) throws SQLException, ClassNotFoundException {
        boolean isAffectedToPayment = CrudUtil.execute("UPDATE payment SET paid_amount=?,status=? WHERE pay_id=?;",  Double.parseDouble(dto.getRoomBookingCost()), dto.getPaymentStatus(),dto.getPaymentId());
        boolean isAffectedToBooking = CrudUtil.execute("UPDATE booking SET booked_date_time=?,booking_duration=? WHERE booking_id=?;", dto.getBookFrom(), String.valueOf(dto.getDuration()),dto.getBookingId());
        boolean isAffectedToRoomBooking = CrudUtil.execute("UPDATE room_booking SET availability=? WHERE room_No=? AND booking_id=?;",dto.getRoomAvailability(), dto.getRoomNo(), dto.getBookingId());

        return (isAffectedToBooking &isAffectedToRoomBooking & isAffectedToPayment) ? true:false;
    }

    @Override
    public boolean cancelBooking(Custom entity) throws SQLException {
        boolean isAffectedToPayment = CrudUtil.execute("UPDATE payment SET status=? WHERE pay_id=?;", entity.getPaymentStatus(),entity.getPaymentId());
        boolean isAffectedToRoomBooking = CrudUtil.execute("UPDATE room_booking SET availability=? WHERE room_No=? AND booking_id=?;",entity.getPaymentStatus(), entity.getRoomNo(), entity.getBookingId());
        boolean isAffectedToRoom = CrudUtil.execute("UPDATE room SET status=? WHERE room_No=?;","Available" ,entity.getRoomNo());

        System.out.println(isAffectedToPayment+""+isAffectedToRoomBooking+""+isAffectedToRoom);
        return (isAffectedToRoomBooking & isAffectedToPayment & isAffectedToRoom) ? true:false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Custom> search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT booking.booking_id,booking.customer_id,customer.cust_name,room.room_type,room_booking.room_No,room.bed_count,booking.booked_date_time,booking.booking_duration,payment.paid_amount FROM booking INNER JOIN room_booking ON room_booking.booking_id=booking.booking_id INNER JOIN customer ON customer.cust_id=booking.customer_id INNER JOIN payment ON payment.pay_id=booking.payment_id INNER JOIN room ON room.room_No=room_booking.room_No WHERE booking.booking_id=? AND room_booking.availability!=?;",id,"Cancelled");
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
                    0
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="B-00000";
        try {
            result= CrudUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "B-"+num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "B-00000";
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
    public void setRoomNumbers(String room_Type) {
        ResultSet roomNoresultSet= null;
        try {
            roomNoresultSet = CrudUtil.execute("SELECT room_No FROM room WHERE room_type=? AND status!=?;",room_Type,"Booked");
            if (room_Type.equals("Deluxe Room")){
                while(roomNoresultSet.next()){
                    if (!deluxeRoomNo.contains(roomNoresultSet.getString(1))){
                        deluxeRoomNo.add(roomNoresultSet.getString(1));
                    }
                }
            }
            else{
                while(roomNoresultSet.next()){
                    if (!standardRoomNo.contains(roomNoresultSet.getString(1))){
                        standardRoomNo.add(roomNoresultSet.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String setSleepCount(String roomNo) {
        ResultSet sleepResultSet= null;
        String sleepCount=null;
        try {
            sleepResultSet= CrudUtil.execute("SELECT bed_count FROM room WHERE room_No=?",roomNo);
            if (sleepResultSet.next()){
                sleepCount=sleepResultSet.getString(1);
                return sleepCount;
            }
            else{
                return sleepCount;
            }
        } catch (SQLException e) {
            return sleepCount;
        }
    }

    @Override
    public String getPaymentId(String bookingId) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT payment_id FROM booking WHERE booking_id=?",bookingId);
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
    public LocalDateTime getPaidDateTime(String bookingId) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ResultSet resultSet=CrudUtil.execute("SELECT booking_made_date_time FROM room_booking WHERE booking_id=?",bookingId);
            LocalDateTime paidDateTime=null;
            if(resultSet.next()){
                paidDateTime= LocalDateTime.parse(resultSet.getString(1),formatter);
            }
            return paidDateTime;
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
            result= CrudUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }

    @Override
    public void updateStatus() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            /*ResultSet result=CrudUtil.execute("SELECT booking.booking_id,booking.booked_date_time,booking.booking_duration FROM booking INNER JOIN room_booking ON room_booking.booking_id=booking.booking_id;");
            ArrayList<UpdateStatus> temp=new ArrayList<>();
            while (result.next()){
                temp.add(new UpdateStatus(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3)
                        ));
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            ArrayList<String> tempIds=new ArrayList<>();
            for (UpdateStatus update:temp) {
                LocalDateTime endDate = LocalDateTime.parse(update.getBookedDate()).plusHours(Long.parseLong(update.getDuration()));
                System.out.println(endDate);
                if (endDate.isAfter(LocalDateTime.now())){
                    tempIds.add(update.getId());
                }
            }*/
            ResultSet resultSet=CrudUtil.execute("SELECT booking.booking_id FROM booking INNER JOIN room_booking ON room_booking.booking_id=booking.booking_id WHERE booking.booked_date_time<? AND room_booking.availability!=?;",LocalDateTime.now(),"Cancelled");
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE room_booking SET availability=? WHERE booking_id=?;","End",tempIds.get(i));
            }
            ResultSet roomSet=CrudUtil.execute("SELECT room_booking.room_No FROM room_booking INNER JOIN booking ON room_booking.booking_id=booking.booking_id WHERE booking.booked_date_time<?;",LocalDateTime.now());
            ArrayList<String> tempNos=new ArrayList<>();
            while (roomSet.next()){
                tempNos.add(roomSet.getString(1));
                System.out.println(roomSet.getString(1));
            }
            boolean isRoomAffected=false;
            for (int i = 0; i < tempNos.size(); i++) {
                isRoomAffected=CrudUtil.execute("UPDATE room SET status=? WHERE room_No=?;","Available",tempNos.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
