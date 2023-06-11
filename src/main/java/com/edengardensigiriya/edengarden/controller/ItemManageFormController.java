package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.bo.BOFactory;
import com.edengardensigiriya.edengarden.bo.custom.ItemBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.ItemDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.ItemDTO;
import com.edengardensigiriya.edengarden.dto.tm.ItemTM;
import com.edengardensigiriya.edengarden.entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemManageFormController {
    public TextField idTxt;
    public AnchorPane itemRoot;
    public TextField nameTxt;
    public TableColumn columnId;
    public TableColumn columnName;
    public Button addBtn;
    public Button updateBtn;
    public Button removeBtn;
    public TableView itmTbl;
    ItemBO itemBO= (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        getAllItems();
    }
    private void getAllItems() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        List<ItemDTO> itmList = itemBO.getAllItems();

        for (ItemDTO itm : itmList) {
            obList.add(new ItemTM(
                    itm.getItemCode(),
                    itm.getItemDescription()
            ));
        }
        itmTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<ItemDTO> itemList =  itemBO.searchItems(idTxt.getText());

            if (!itemList.isEmpty()){
                for (ItemDTO item : itemList) {
                    idTxt.setText(item.getItemCode());
                    nameTxt.setText(item.getItemDescription());
                    idTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Item Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAdded = itemBO.saveItems(new ItemDTO(itemBO.newIdGenerate(), nameTxt.getText()));
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
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

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAdded = itemBO.updateItems(new ItemDTO(idTxt.getText(), nameTxt.getText()));
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                idTxt.setDisable(false);
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

    public void removeItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the item?").showAndWait();
            if (comfirm.isPresent()){
                boolean isAdded = itemBO.removeItems(idTxt.getText());
                if (isAdded) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Removed Successfully!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    idTxt.setDisable(false);
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
    public void resetPage() throws SQLException, ClassNotFoundException {
        idTxt.setText("");
        nameTxt.setText("");
        setCellValueFactory();
        getAllItems();
    }
}
