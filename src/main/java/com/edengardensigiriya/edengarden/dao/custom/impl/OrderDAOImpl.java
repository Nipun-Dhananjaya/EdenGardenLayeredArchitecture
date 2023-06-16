package com.edengardensigiriya.edengarden.dao.custom.impl;

import com.edengardensigiriya.edengarden.dao.custom.OrderDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.OrderItemDTO;
import com.edengardensigiriya.edengarden.entity.Custom;
import com.edengardensigiriya.edengarden.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Custom> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT order_.ord_id,order_.supp_id,item.description,purchase_detail.bought_qty,order_.ord_made_date,order_.ord_dilever_date,purchase_detail.ord_cost,order_.status FROM order_ INNER JOIN purchase_detail ON purchase_detail.order_id=order_.ord_id INNER JOIN item ON item.item_code=purchase_detail.itm_code ORDER BY order_.ord_id;");
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
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    @Override
    public boolean save(Custom entity) throws SQLException, ClassNotFoundException {
        try {
            int length= entity.getOrderItem().size();
            String itmCode = null;
            boolean isAddedOrd= CrudUtil.execute("INSERT INTO order_ VALUES (?,?,?,?,?);",entity.getOrdId(),entity.getSuppId(), LocalDateTime.now(),entity.getDeliverDateTime(),"Active");
            System.out.println("isAd:   "+ isAddedOrd);
            System.out.println(length);
            boolean isAddedPurchDetls=false;
            for (int j = 0; j < length; j++) {
                OrderItemDTO itm= new OrderItemDTO();
                itm.setItem(entity.getOrderItem().get(j).getItemDescription());
                itm.setQty(entity.getOrderItem().get(j).getQty());
                ResultSet resultSet=CrudUtil.execute("SELECT item_code FROM item WHERE description=?;",itm.getItem());
                if (resultSet.next()){
                    itmCode=resultSet.getString(1);
                }
                isAddedPurchDetls = CrudUtil.execute("INSERT INTO purchase_detail VALUES (?,?,?,?);", entity.getOrdId(),itmCode,Double.parseDouble(String.valueOf(itm.getQty())),entity.getOrdCost()!=null? Double.parseDouble(String.valueOf(entity.getOrdCost())):0.00);
                System.out.println("isP:   "+isAddedPurchDetls);
            }
            if (isAddedOrd & isAddedPurchDetls){
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
        try {
            int length= entity.getOrderItem().size();
            String itmCode = null;
            boolean isUpdatedOrd=CrudUtil.execute("UPDATE order_ SET supp_id=?,ord_dilever_date=? WHERE ord_id=?;",entity.getSuppId(),entity.getDeliverDateTime(),entity.getOrdId());
            boolean isUpdatedPurchDetls=false;
            System.out.println("Ord:"+isUpdatedOrd);
            for (int j = 0; j < length; j++) {
                OrderItemDTO itm= new OrderItemDTO();
                itm.setItem(entity.getOrderItem().get(j).getItemDescription());
                itm.setQty(entity.getOrderItem().get(j).getQty());
                ResultSet resultSet=CrudUtil.execute("SELECT item_code FROM item WHERE description=?;",itm.getItem());
                if (resultSet.next()){
                    itmCode=resultSet.getString(1);
                }
                System.out.println(itmCode);
                isUpdatedPurchDetls = CrudUtil.execute("UPDATE purchase_detail SET bought_qty=?,ord_cost=? WHERE order_id=? AND itm_code=?;",Double.parseDouble(String.valueOf(itm.getQty())),entity.getOrdCost()!=null? Double.parseDouble(String.valueOf(entity.getOrdCost())):0.00, entity.getOrdId(),itmCode);
                if (isUpdatedPurchDetls){
                    continue;
                }else{
                    isUpdatedPurchDetls = CrudUtil.execute("INSERT INTO purchase_detail VALUES(?,?,?,?);", entity.getOrdId(),itmCode,Double.parseDouble(String.valueOf(itm.getQty())),entity.getOrdCost()!=null? Double.parseDouble(String.valueOf(entity.getOrdCost())):0.00);
                }
                System.out.println("Pur"+isUpdatedPurchDetls);
            }
            if (isUpdatedOrd & isUpdatedPurchDetls){
                return true;
            }else{
                System.out.println(1234);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Custom> search(String ordId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT order_.ord_id,order_.supp_id,order_.ord_dilever_date,purchase_detail.ord_cost FROM order_ INNER JOIN purchase_detail ON purchase_detail.order_id=order_.ord_id WHERE order_.ord_id=?;",ordId);
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    @Override
    public String newIdGenerate() throws SQLException, ClassNotFoundException {
        ResultSet result=null;
        String[] idParts;
        String id="O-00000";
        try {
            result= CrudUtil.execute("SELECT ord_id FROM order_ ORDER BY ord_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "O-"+num;
        } catch (SQLException e) {
            return "O-00000";
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
    public String getOrderId() {
        ResultSet result=null;
        String id="";
        try {
            result= CrudUtil.execute("SELECT ord_id FROM order_ ORDER BY ord_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }

    @Override
    public boolean cancelOrder(String ordId) throws SQLException {
        try {
            boolean isUpdatedOrd=CrudUtil.execute("UPDATE order_ SET status=? WHERE ord_id=?;","Cancelled",ordId);
            if (isUpdatedOrd ){
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
    public List<Custom> getAllItemsOfOrder(String ordId) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT item.description,purchase_detail.bought_qty FROM order_ INNER JOIN purchase_detail ON purchase_detail.order_id=order_.ord_id INNER JOIN item ON item.item_code=purchase_detail.itm_code WHERE order_.ord_id=?;",ordId);
        List<Custom> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Custom(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }
}
