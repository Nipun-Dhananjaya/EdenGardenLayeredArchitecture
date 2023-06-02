package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.BicycleDAO;
import com.edengardensigiriya.edengarden.dao.custom.impl.BicycleDAOImpl;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.BicycleDTO;
import com.edengardensigiriya.edengarden.dto.RegExPatterns;
import com.edengardensigiriya.edengarden.dto.tm.BicycleTM;
import com.edengardensigiriya.edengarden.entity.Bicycle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BicycleManageFormController implements Initializable {
    public TextField bicycleNoTxt;
    public TextField brandTxt;
    public Button addBtn;
    public Button updateBtn;
    public ComboBox bicycleTypeCmbBx;
    public Button removeBtn;
    public TextField colourTxt;
    public AnchorPane bicycleRoot;
    public TableView bicycleTbl;
    public TableColumn columnBicycleNo;
    public TableColumn columnBrand;
    public TableColumn columnType;
    public TableColumn columnColor;
    public static ArrayList<String> bicycleTypes = new ArrayList<>();
    public TableColumn columnStatus;
    BicycleDAO bicycleDAO= (BicycleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BICYCLE);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bicycleTypes.add("Cruiser");
        bicycleTypes.add("Fixed Gear");
        bicycleTypes.add("Mountain Bike");
        bicycleTypes.add("BMX");
        bicycleTypes.add("Touring Bike");
        bicycleTypes.add("Utility Bike");
        bicycleTypes.add("Folding Bike");

        ObservableList<String> bicycleType = FXCollections.observableList(bicycleTypes);
        bicycleTypeCmbBx.setItems(bicycleType);
        setCellValueFactory();
        getAllBicycles();
    }
    private void getAllBicycles() throws SQLException, ClassNotFoundException {
        ObservableList<BicycleTM> obList = FXCollections.observableArrayList();
        List<BicycleDTO> bicycleList = new ArrayList<>();
        for (Bicycle bicycle:bicycleDAO.getAll()) {
            bicycleList.add(new BicycleDTO(
                    bicycle.getBicycleNo(),
                    bicycle.getBrand(),
                    bicycle.getBicycleType(),
                    bicycle.getColour(),
                    bicycle.getStatus()

            ));
        }

        for (BicycleDTO bicycle : bicycleList) {
            obList.add(new BicycleTM(
                    bicycle.getBicycleNo(),
                    bicycle.getBrand(),
                    bicycle.getBicycleType(),
                    bicycle.getColour(),
                    bicycle.getStatus()
            ));
        }
        bicycleTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnBicycleNo.setCellValueFactory(new PropertyValueFactory<>("bicycleNo"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("bicycleType"));
        columnColor.setCellValueFactory(new PropertyValueFactory<>("colour"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void bicycleNoSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<BicycleDTO> bicycleList = new ArrayList<>();
            for (Bicycle bicycle:bicycleDAO.search(bicycleNoTxt.getText())) {
                bicycleList.add(new BicycleDTO(
                        bicycle.getBicycleNo(),
                        bicycle.getBrand(),
                        bicycle.getBicycleType(),
                        bicycle.getColour(),
                        bicycle.getStatus()

                ));
            }
            if (!bicycleList.isEmpty()){
                for (BicycleDTO bike : bicycleList) {
                    bicycleNoTxt.setText(bike.getBicycleNo());
                    bicycleTypeCmbBx.setValue(bike.getBicycleType());
                    colourTxt.setText(bike.getColour());
                    brandTxt.setText(bike.getBrand());
                    bicycleNoTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "BicycleDTO Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addBicycleOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected = bicycleDAO.save(new Bicycle(bicycleDAO.newIdGenerate(),String.valueOf(bicycleTypeCmbBx.getSelectionModel().getSelectedItem()),
                        colourTxt.getText(),brandTxt.getText(),"Available"));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "BicycleDTO Added Successfully!").showAndWait();
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected = bicycleDAO.update(new Bicycle(bicycleNoTxt.getText(),String.valueOf(bicycleTypeCmbBx.getSelectionModel().getSelectedItem()),
                        colourTxt.getText(),brandTxt.getText(),""));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Bicycle Updated Successfully!").showAndWait();
                bicycleNoTxt.setDisable(false);
                resetPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void removeBicycleOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the bicycle?").showAndWait();
            if (comfirm.isPresent()){
                boolean isAffected=false;
                if (isCorrectPattern()){
                    isAffected = bicycleDAO.delete(bicycleNoTxt.getText());
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Bicycle Removed Successfully!").showAndWait();
                    bicycleNoTxt.setDisable(false);
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
                }
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
    public void resetPage() throws SQLException, ClassNotFoundException {
        bicycleNoTxt.setText("");
        brandTxt.setText("");
        colourTxt.setText("");
        bicycleTypeCmbBx.setValue("");
        setCellValueFactory();
        getAllBicycles();
    }
    private boolean isCorrectPattern(){
        if (RegExPatterns.getNamePattern().matcher(colourTxt.getText()).matches() && RegExPatterns.getNamePattern().matcher(brandTxt.getText()).matches()){
            return true;
        }
        return false;
    }


}
