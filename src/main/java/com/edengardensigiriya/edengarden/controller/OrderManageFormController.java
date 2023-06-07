package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.ItemDAO;
import com.edengardensigiriya.edengarden.dao.custom.OrderDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.*;
import com.edengardensigiriya.edengarden.dto.tm.OrderItemTM;
import com.edengardensigiriya.edengarden.dto.tm.OrderTM;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.entity.Item;
import com.edengardensigiriya.edengarden.util.RegExPatterns;
import com.edengardensigiriya.edengarden.util.SendEmail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

public class OrderManageFormController {
    public AnchorPane orderRoot;
    public TextField bookingIdTxt;
    public TextField suppIdTxt;
    public TextField deliverTimeTxt;
    public TableView ordTbl;
    public Button orderBtn;
    public DatePicker deliverDateDtPckr;
    public ComboBox itemCmbBx;
    public Button orderCancelBtn;
    public Button updateBtn;
    public TextField qtyTxt;
    public Button addItmBtn;
    public TableView<OrderItemTM> itemTbl;
    public TableColumn columnOrdTblOrdId;
    public TableColumn columnOrdTblSuppId;
    public TableColumn columnOrdTblItms;
    public TableColumn columnOrdTblOrdDte;
    public TableColumn columnOrdTblDeliverDte;
    public TableColumn columnOrdTblOrdCost;
    public TableColumn<OrderItemTM,String> columnItemTblItmName;
    public TableColumn<OrderItemTM,String> columnItemTblItmQty;
    public TableColumn columnOrdTblItm;
    public TableColumn columnOrdTblQty;
    public TableColumn columnOrdTblOrdStatus;
    public TextField costTxt;
    ObservableList<OrderItemTM> obList = FXCollections.observableArrayList();
    List<OrderItemDTO> data = new ArrayList<>();
    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        costTxt.setText("0.00");
        setOrderCellValueFactory();
        getAllOrders();
        getAllItems();
    }

    private void getAllOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderTM> obList = FXCollections.observableArrayList();
        List<OrderDTO> ordList = new ArrayList<>();//orderDAO.getAll();

        for (Custom order : orderDAO.getAll()) {
            ordList.add(new OrderDTO(
                    order.getOrdId(),
                    order.getSuppId(),
                    order.getItemDescription(),
                    order.getQty(),
                    order.getOrderedDateTime(),
                    order.getDeliverDateTime(),
                    order.getOrdCost(),
                    order.getOrdStatus()
            ));
        }
        for (OrderDTO order : ordList) {
            obList.add(new OrderTM(
                    order.getOrdId(),
                    order.getSuppId(),
                    order.getItems(),
                    order.getQty(),
                    order.getOrderedDateTime(),
                    order.getDeliverDateTime(),
                    order.getCost(),
                    order.getStatus()
            ));
        }
        ordTbl.setItems(obList);
    }
    void setOrderCellValueFactory() {
        columnOrdTblOrdId.setCellValueFactory(new PropertyValueFactory<>("ordId"));
        columnOrdTblSuppId.setCellValueFactory(new PropertyValueFactory<>("suppId"));
        columnOrdTblItm.setCellValueFactory(new PropertyValueFactory<>("items"));
        columnOrdTblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnOrdTblOrdDte.setCellValueFactory(new PropertyValueFactory<>("orderedDateTime"));
        columnOrdTblDeliverDte.setCellValueFactory(new PropertyValueFactory<>("deliverDateTime"));
        columnOrdTblOrdCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnOrdTblOrdStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void orderIdSearchOnAction(ActionEvent actionEvent) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<OrderUIDTO> ordList = new ArrayList<>();
            List<Custom> customs = orderDAO.search(bookingIdTxt.getText());

            for (Custom custom:customs) {
                ordList.add(new OrderUIDTO(
                        custom.getOrdId(),
                        custom.getSuppId(),
                        custom.getDeliverDateTime(),
                        custom.getOrdCost()
                ));
            }
            for (Custom custom:orderDAO.getAllItemsOfOrder(bookingIdTxt.getText())) {
                data.add(new OrderItemDTO(
                        custom.getItemDescription(),
                        custom.getQty()
                ));
            }
            setItemsCellValueFactory();
            getUpdateOrderItems();
            if (!ordList.isEmpty()){
                for (OrderUIDTO ord : ordList) {
                    bookingIdTxt.setText(ord.getOrdId());
                    suppIdTxt.setText(ord.getSuppId());
                    deliverDateDtPckr.setValue(LocalDate.parse(ord.getDeleverDateTime(),formatter));
                    String[] datTime=ord.getDeleverDateTime().split(" ");
                    deliverTimeTxt.setText(datTime[1]);
                    costTxt.setText(ord.getCost());
                }
                bookingIdTxt.setDisable(true);
                suppIdTxt.setDisable(true);
                itemTbl.setEditable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Order Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private void getUpdateOrderItems() throws SQLException {
        List<OrderItemDTO> ordItmList = new ArrayList<>();//orderDAO.getAllItemsOfOrder(bookingIdTxt.getText());

        for (Custom custom:orderDAO.getAllItemsOfOrder(bookingIdTxt.getText())) {
            ordItmList.add(new OrderItemDTO(
                    custom.getItemDescription(),
                    custom.getQty()
            ));
        }
        for (OrderItemDTO orderItm : ordItmList) {
            obList.add(new OrderItemTM(
                    orderItm.getItem(),
                    orderItm.getQty()
            ));
        }
        itemTbl.setItems(obList);
    }

    public void orderOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime deliverDate = LocalDateTime.of(deliverDateDtPckr.getValue(), LocalTime.parse(deliverTimeTxt.getText()));
            boolean isAffected=false;
            if (isCorrectPattern()){
                List<Custom> items=new ArrayList<>();
                for (OrderItemDTO orderItem:data) {
                    items.add(new Custom(
                            orderItem.getItem(),
                            orderItem.getQty()
                    ));
                }
                isAffected = orderDAO.save(new Custom(orderDAO.newIdGenerate(),suppIdTxt.getText(),items,String.valueOf(deliverDate),costTxt.getText()));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                sendMail("Order");
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        bookingIdTxt.setText("");
        suppIdTxt.setText("");
        deliverTimeTxt.setText("");
        qtyTxt.setText("");
        itemCmbBx.setValue("Item");
        costTxt.setText("0.00");
        data.clear();
        obList.clear();
        itemTbl.setItems(obList);
        setOrderCellValueFactory();
        getAllOrders();
        getAllItems();
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel the order?").showAndWait();
            if (comfirm.isPresent()){
                boolean isAffected=false;
                if (isCorrectPattern()){
                    isAffected = orderDAO.cancelOrder(bookingIdTxt.getText());
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Order Cancelled!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    sendMail("Cancel");
                    bookingIdTxt.setDisable(false);
                    suppIdTxt.setDisable(false);
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime deliverDate = LocalDateTime.of(deliverDateDtPckr.getValue(), LocalTime.parse(deliverTimeTxt.getText()));
            boolean isAffected=false;
            if (isCorrectPattern()){
                List<Custom> items=new ArrayList<>();
                for (OrderItemDTO orderItem:data) {
                    items.add(new Custom(
                            orderItem.getItem(),
                            orderItem.getQty()
                    ));
                }
                isAffected = orderDAO.update(new Custom(bookingIdTxt.getText(),suppIdTxt.getText(),items,String.valueOf(deliverDate),costTxt.getText()));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Order Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                sendMail("Update");
                bookingIdTxt.setDisable(false);
                suppIdTxt.setDisable(false);
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addItemToTblOnAction(ActionEvent actionEvent) {
        if (RegExPatterns.getDoublePattern().matcher(qtyTxt.getText()).matches()) {
            data.add(new OrderItemDTO(String.valueOf(itemCmbBx.getSelectionModel().getSelectedItem()), qtyTxt.getText()));
            setItemsCellValueFactory();
            getOrderItems();
            itemCmbBx.setValue("Item");
            qtyTxt.setText("");
        }
        else{
            new Alert(Alert.AlertType.WARNING, "Re-Check Details!").showAndWait();
        }
    }

    private void getOrderItems() {
        ObservableList<OrderItemTM> obList = FXCollections.observableArrayList();
        List<OrderItemDTO> ordItmList = data;

        for (OrderItemDTO orderItm : ordItmList) {
            obList.add(new OrderItemTM(
                    orderItm.getItem(),
                    orderItm.getQty()
            ));
        }
        itemTbl.setItems(obList);
    }

    private void getAllItems() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (Item orderItm : itemDAO.getAllDescription()) {
            obList.add(
                    orderItm.getItemDescription()
            );
        }
        itemCmbBx.setItems(obList);
    }

    void setItemsCellValueFactory() {
        columnItemTblItmName.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnItemTblItmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void getRowValueOnMouseClicked(MouseEvent mouseEvent) throws SQLException {
        OrderItemTM selectedItem = itemTbl.getSelectionModel().getSelectedItem();
        int index = itemTbl.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(selectedItem==null)return;
        itemCmbBx.setValue(selectedItem.getItem());
        qtyTxt.setText(selectedItem.getQty());
        obList.remove(index);
        data.remove(index);
        itemTbl.setItems(obList);
    }
    private boolean isCorrectPattern(){
        if (RegExPatterns.getDoublePattern().matcher(costTxt.getText()).matches() && deliverTimeTxt.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$")){
            return true;
        }
        System.out.println("Not validate");
        return false;
    }
    public void sendMail(String status) throws MessagingException, GeneralSecurityException, IOException, SQLException {
        SendEmail.sendMail(orderDAO.getEmail(suppIdTxt.getText()),(status.equals("Order")?"Hotel Eden Garden Order":status.equals("Update")?"Hotel Eden Garden Order Update":"Hotel Eden Garden Order Cancellation"),
                "Your Order ID:"+(status.equals("Order")?orderDAO.getOrderId():status.equals("Update")?bookingIdTxt.getText():bookingIdTxt.getText())+"\nOrder List:\n"+setItemList()+"\n\nOrder Placed time: "+ LocalDateTime.now()+"\nOrder Deliver Date: "+deliverDateDtPckr.getValue()+"  "+deliverTimeTxt.getText()+"\n"+(status.equals("Order")?"Order Placed!":status.equals("Update")?"Order Updated!":"Order Cancelled!"+"\n\nThank you!\n\nHotel Eden Garden,\nInamaluwa,\nSeegiriya"));
    }

    private String setItemList() throws SQLException {
        //List<OrderItem> ordItmList = orderDAO.getAllItemsOfOrder(bookingIdTxt.getText());
        List<OrderItemDTO> ordItmList = new ArrayList<>();

        for (Custom custom:orderDAO.getAllItemsOfOrder(bookingIdTxt.getText())) {
            ordItmList.add(new OrderItemDTO(
                    custom.getItemDescription(),
                    custom.getQty()
            ));
        }
        String list="Item                                   Quantity";
        for (OrderItemDTO orderItm : ordItmList) {
            list+="\n"+orderItm.getItem()+"         "+orderItm.getQty();
        }
        return list;
    }
}
