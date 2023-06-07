package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.PaymentDAO;
import com.edengardensigiriya.edengarden.dao.custom.TransportDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.*;
import com.edengardensigiriya.edengarden.dto.tm.TransportTM;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.util.RegExPatterns;
import com.edengardensigiriya.edengarden.util.SendEmail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransportManageFormController {
    public AnchorPane transportRoot;
    public TextField transportIdTxt;
    public TextField custIdTxt;
    public TextField nameTxt;
    public TextField startTimeTxt;
    public TableView transportTbl;
    public Button bookBtn;
    public DatePicker DateTimeDtPckr;
    public TextField destinationTxt;
    public TextField costTxt;
    public Button cancelTransportBtn;
    public Button updateBtn;
    public TableColumn columnTransportId;
    public TableColumn columnCustId;
    public TableColumn columnCustName;
    public TableColumn columnStartDateTime;
    public TableColumn columnDestination;
    public TableColumn columnCost;
    public TableColumn columnStatus;

    TransportDAO transportDAO = (TransportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSPORT);
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    public void initialize() throws SQLException, ClassNotFoundException {
        transportDAO.updateStatus();
        setCellValueFactory();
        getAllTransports();
        costTxt.setText("0.00");
    }

    private void getAllTransports() throws SQLException, ClassNotFoundException {
        ObservableList<TransportTM> obList = FXCollections.observableArrayList();
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
        for (TransportDTO transport : transportList) {
            obList.add(new TransportTM(
                    transport.getTransId(),
                    transport.getCustId(),
                    transport.getCustName(),
                    transport.getDateTime(),
                    transport.getDestination(),
                    transport.getCost(),
                    transport.getStatus()
            ));
        }
        transportTbl.setItems(obList);
    }

    void setCellValueFactory() {
        columnTransportId.setCellValueFactory(new PropertyValueFactory<>("transId"));
        columnCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        columnCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        columnStartDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<TransportUIDTO> transportList = new ArrayList<>();
            for (Custom transport : transportDAO.search(transportIdTxt.getText())) {
                transportList.add(new TransportUIDTO(
                        transport.getTransId(),
                        transport.getCustId(),
                        transport.getCustName(),
                        transport.getTransDateTime(),
                        transport.getDestination(),
                        transport.getTransCost()
                ));
            }
            if (!transportList.isEmpty()) {
                for (TransportUIDTO transportUpdate : transportList) {
                    transportIdTxt.setText(transportUpdate.getTransId());
                    custIdTxt.setText(transportUpdate.getCustId());
                    nameTxt.setText(transportUpdate.getCustName());
                    String[] datTime = transportUpdate.getBookFrom().split(" ");
                    DateTimeDtPckr.setValue(LocalDate.parse(transportUpdate.getBookFrom(), formatter));
                    startTimeTxt.setText(datTime[1]);
                    destinationTxt.setText(transportUpdate.getDestination());
                    costTxt.setText(transportUpdate.getCost());
                    transportIdTxt.setDisable(true);
                    custIdTxt.setDisable(true);
                    nameTxt.setDisable(true);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Transport ID Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        DBConnection.getInstance().getConnection().setAutoCommit(false);

        DBConnection.getInstance().getConnection().commit();
        DBConnection.getInstance().getConnection().setAutoCommit(true);
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            String name = transportDAO.searchCustomer(custIdTxt.getText());
            if (name != null) {
                nameTxt.setText(name);
            } else {
                new Alert(Alert.AlertType.WARNING, "Customer ID not Found!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void bookTransportOnAction(ActionEvent actionEvent) throws MessagingException, GeneralSecurityException, IOException, SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDateTime = LocalDateTime.of(DateTimeDtPckr.getValue(), LocalTime.parse(startTimeTxt.getText()));
            String paymentId = paymentDAO.newIdGenerate();
            String transportId = transportDAO.newIdGenerate();
            boolean isAffected = false;
            if (isCorrectPattern()) {
                isAffected = transportDAO.save(new Custom(transportId, custIdTxt.getText(), String.valueOf(startDateTime), destinationTxt.getText(),
                        paymentId, costTxt.getText(),true));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Transport Booking Successful!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                sendMail("Booking");
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void cancelTransportOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel the booking?").showAndWait();
            if (comfirm.isPresent()) {
                String transId = transportIdTxt.getText();
                String paymentId = transportDAO.getPaymentId(transId);
                boolean isAffected = false;
                if (isCorrectPattern()) {
                    isAffected = transportDAO.cancelTransport(new Custom(transId, paymentId, "Cancelled"));
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Transport Cancelled!").showAndWait();
                    sendMail("Cancel");
                    transportIdTxt.setDisable(false);
                    custIdTxt.setDisable(false);
                    nameTxt.setDisable(false);
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
                }
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.of(DateTimeDtPckr.getValue(), LocalTime.parse(startTimeTxt.getText()));
            String transId = transportIdTxt.getText();
            String paymentId = transportDAO.getPaymentId(transId);
            boolean isAffected = false;
            if (isCorrectPattern()) {
                isAffected = transportDAO.update(new Custom(transId, custIdTxt.getText(), String.valueOf(startDate), paymentId,
                        destinationTxt.getText(), costTxt.getText(),0));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Transport Updated!").showAndWait();
                sendMail("Update");
                transportIdTxt.setDisable(false);
                custIdTxt.setDisable(false);
                nameTxt.setDisable(false);
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void resetPage() throws SQLException, ClassNotFoundException {
        custIdTxt.setText("");
        nameTxt.setText("");
        startTimeTxt.setText("");
        destinationTxt.setText("");
        costTxt.setText("");
        setCellValueFactory();
        getAllTransports();
    }

    private boolean isCorrectPattern() {
        if (RegExPatterns.getNamePattern().matcher(nameTxt.getText()).matches() && RegExPatterns.getDoublePattern().matcher(costTxt.getText()).matches() && startTimeTxt.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$") && RegExPatterns.getAddressPattern().matcher(destinationTxt.getText()).matches()) {
            return true;
        }
        return false;
    }

    public void sendMail(String status) throws MessagingException, GeneralSecurityException, IOException, SQLException {
        SendEmail.sendMail(transportDAO.getEmail(custIdTxt.getText()),
                (status.equals("Booking") ? "Transport Booking" : status.equals("Update") ? "Transport Booking Update" : "Transport Booking Cancellation"),
                "Dear Customer,\nYour Transport ID:" + (status.equals("Booking") ? transportDAO.getBookingId() : status.equals("Update") ? transportIdTxt.getText() : transportIdTxt.getText()) + "\nYour Customer ID:" + custIdTxt.getText() + "\nName:" + nameTxt.getText() +
                        "\nRoom Number:" + "\nFrom:" + DateTimeDtPckr.getValue() + "  " + startTimeTxt.getText() + "\nDestination:" + destinationTxt.getText() + "\nTotal Cost:" + costTxt.getText() +
                        "\n" + (status.equals("Booking") ? "Transport Booking Successful!" : status.equals("Update") ? "Transport Booking Update Successfully" : "Transport Booking Cancelled!" + "\n\nThank you for using our service!\n\nHotel Eden Garden,\nInamaluwa,\nSeegiriya"));
    }
}
