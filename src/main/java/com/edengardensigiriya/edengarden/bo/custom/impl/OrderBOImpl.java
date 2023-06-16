package com.edengardensigiriya.edengarden.bo.custom.impl;

import com.edengardensigiriya.edengarden.bo.custom.OrderBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.ItemDAO;
import com.edengardensigiriya.edengarden.dao.custom.OrderDAO;
import com.edengardensigiriya.edengarden.dto.OrderDTO;
import com.edengardensigiriya.edengarden.dto.OrderItemDTO;
import com.edengardensigiriya.edengarden.dto.OrderUIDTO;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
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
        return ordList;
    }

    @Override
    public List<OrderUIDTO> searchOrders(String ordId) throws SQLException, ClassNotFoundException {
        List<OrderUIDTO> ordList = new ArrayList<>();
        List<Custom> customs = orderDAO.search(ordId);

        for (Custom custom:customs) {
            ordList.add(new OrderUIDTO(
                    custom.getOrdId(),
                    custom.getSuppId(),
                    custom.getDeliverDateTime(),
                    custom.getOrdCost()
            ));
        }
        return ordList;
    }

    @Override
    public boolean saveOrders(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        List<Custom> items=new ArrayList<>();
        for (OrderItemDTO orderItem:orderDTO.getOrderItemDTOS()) {
            items.add(new Custom(
                    orderItem.getItem(),
                    orderItem.getQty()
            ));
        }
        return orderDAO.save(new Custom(orderDTO.getOrdId(),orderDTO.getSuppId(),items,orderDTO.getDeliverDateTime(), orderDTO.getCost()));
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        return orderDAO.newIdGenerate();
    }

    @Override
    public boolean updateOrders(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        List<Custom> items=new ArrayList<>();
        for (OrderItemDTO orderItem:orderDTO.getOrderItemDTOS()) {
            items.add(new Custom(
                    orderItem.getItem(),
                    orderItem.getQty()
            ));
        }
        System.out.println(items.get(0).getItemDescription());
        return orderDAO.update(new Custom(orderDTO.getOrdId(),orderDTO.getSuppId(),items,orderDTO.getDeliverDateTime(),orderDTO.getCost()));
    }

    @Override
    public List<OrderItemDTO> getAllItemsOfOrder(String ordId) throws SQLException {
        List<OrderItemDTO> data=new ArrayList<>();
        for (Custom custom:orderDAO.getAllItemsOfOrder(ordId)) {
            data.add(new OrderItemDTO(
                    custom.getItemDescription(),
                    custom.getQty()
            ));
        }
        return data;
    }

    @Override
    public boolean cancelOrder(String ordId) throws SQLException {
        return orderDAO.cancelOrder(ordId);
    }

    @Override
    public ObservableList<String> getAllDescription() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (Item orderItm : itemDAO.getAllDescription()) {
            obList.add(
                    orderItm.getItemDescription()
            );
        }
        return obList;
    }

    @Override
    public String getOrderId() {
        return orderDAO.getOrderId();
    }
}
