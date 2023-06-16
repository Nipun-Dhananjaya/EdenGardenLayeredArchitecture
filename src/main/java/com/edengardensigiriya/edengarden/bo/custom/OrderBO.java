package com.edengardensigiriya.edengarden.bo.custom;

import com.edengardensigiriya.edengarden.bo.SuperBO;
import com.edengardensigiriya.edengarden.dto.BicycleDTO;
import com.edengardensigiriya.edengarden.dto.OrderDTO;
import com.edengardensigiriya.edengarden.dto.OrderItemDTO;
import com.edengardensigiriya.edengarden.dto.OrderUIDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;
    List<OrderUIDTO> searchOrders(String ordId) throws SQLException, ClassNotFoundException;
    boolean saveOrders(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    String newIdGenerate() throws SQLException, ClassNotFoundException;

    boolean updateOrders(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    List<OrderItemDTO> getAllItemsOfOrder(String ordId) throws SQLException;

    boolean cancelOrder(String ordId) throws SQLException;

    ObservableList<String> getAllDescription() throws SQLException;

    String getOrderId();
}
