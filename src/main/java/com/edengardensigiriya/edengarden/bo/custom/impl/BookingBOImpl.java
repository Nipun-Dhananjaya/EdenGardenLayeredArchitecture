package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.BookingBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.BookingDAO;
import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.dto.BookingDTO;
import com.edengardensigiriya.edengarden.dto.BookingUIDTO;
import com.edengardensigiriya.edengarden.entity.Custom;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingBOImpl implements BookingBO {
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    BookingDAO bookingDAO= (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);

    @Override
    public List<BookingDTO> getAllBookings() throws SQLException, ClassNotFoundException {
        List<BookingDTO> bookList = new ArrayList<>();
        for (Custom booking : bookingDAO.getAll()) {
            bookList.add(new BookingDTO(
                    booking.getBookingId(),
                    booking.getCustId(),
                    booking.getCustName(),
                    booking.getRoomNo(),
                    booking.getBookFrom(),
                    booking.getDuration(),
                    booking.getRoomBookingCost(),
                    booking.getBookedOn(),
                    booking.getRoomAvailability()
            ));
        }
        return bookList;
    }

    @Override
    public List<BookingUIDTO> searchBookings(String bookingId) throws SQLException, ClassNotFoundException {
        List<BookingUIDTO> bookList =new ArrayList<>();
        for (Custom custom:bookingDAO.search(bookingId)) {
            bookList.add(new BookingUIDTO(
                    custom.getBookingId(),
                    custom.getCustId(),
                    custom.getCustName(),
                    custom.getRoomType(),
                    custom.getRoomNo(),
                    custom.getSleepCount(),
                    custom.getBookFrom(),
                    custom.getDuration(),
                    custom.getRoomBookingCost()
            ));
        }
        return bookList;
    }

    @Override
    public boolean saveBookings(BookingDTO bookingDTO) throws SQLException, ClassNotFoundException {
        return bookingDAO.save(new Custom(bookingDTO.getBookingId(), bookingDTO.getCustId(), bookingDTO.getBookFrom(), bookingDTO.getDuration(), bookingDTO.getPaymentId(),bookingDTO.getRoomNo(), bookingDTO.getCost(), bookingDTO.getBookedOn(),bookingDTO.getAvailability(),bookingDTO.getPaymentStatus()));
    }

    @Override
    public boolean updateBookings(BookingDTO bookingDTO) throws SQLException, ClassNotFoundException {
        System.out.println(bookingDTO.getCost());
        Custom custom=new Custom(bookingDTO.getBookingId(), bookingDTO.getBookFrom(), bookingDTO.getDuration(), bookingDTO.getPaymentId(),bookingDTO.getRoomNo(), bookingDTO.getCost(),bookingDTO.getAvailability(),bookingDTO.getPaymentStatus(),0);
        System.out.println("C:"+ custom.getRoomBookingCost());
        return bookingDAO.update(custom);
    }

    @Override
    public void updateStatus() throws SQLException {
        bookingDAO.updateStatus();
    }

    @Override
    public String searchCustomer(String custId) {
        return bookingDAO.searchCustomer(custId);
    }

    @Override
    public String newPayIdGenerate() throws SQLException, ClassNotFoundException {
        return paymentDAO.newIdGenerate();
    }

    @Override
    public String newBookIdGenerate() throws SQLException, ClassNotFoundException {
        return bookingDAO.newIdGenerate();
    }

    @Override
    public String getPaymentId(String bookingId) {
        return bookingDAO.getPaymentId(bookingId);
    }

    @Override
    public boolean cancelBookings(BookingDTO bookingDTO) throws SQLException {
        return bookingDAO.cancelBooking(new Custom(bookingDTO.getBookingId(),bookingDTO.getPaymentId(),bookingDTO.getPaymentStatus(),bookingDTO.getRoomNo(),0));
    }

    @Override
    public LocalDateTime getPaidDateTime(String bookingId) {
        return bookingDAO.getPaidDateTime(bookingId);
    }

    @Override
    public void setRoomNumbers(String room) {
        bookingDAO.setRoomNumbers(room);
    }

    @Override
    public String setSleepCount(String roomNo) {
        return bookingDAO.setSleepCount(roomNo);
    }

    @Override
    public String getBookingId() {
        return bookingDAO.getBookingId();
    }
}
