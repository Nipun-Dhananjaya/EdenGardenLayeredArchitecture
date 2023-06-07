package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.CarDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.CarDTO;
import com.edengardensigiriya.edengarden.util.RegExPatterns;
import com.edengardensigiriya.edengarden.dto.tm.CarTM;
import com.edengardensigiriya.edengarden.entity.Car;
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

public class CarManageFormController {
    public TextField regNoTxt;
    public AnchorPane carsRoot;
    public TextField brandTxt;
    public TableView carTbl;
    public Button addBtn;
    public Button updateBtn;
    public ComboBox carTypeCmbBx;
    public Button removeBtn;
    public TextField colourTxt;
    public static ArrayList<String> carTypes = new ArrayList<>();
    public TableColumn columnRegNo;
    public TableColumn columnBrand;
    public TableColumn columnCarType;
    public TableColumn columnColor;
    public TableColumn columnStatus;
    CarDAO carDAO= (CarDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAR);

    public void initialize() throws SQLException, ClassNotFoundException {
        carTypes.add("SUV");
        carTypes.add("Hatchback");
        carTypes.add("Crossover");
        carTypes.add("Convertible");
        carTypes.add("Sedan");
        carTypes.add("Sports Car");
        carTypes.add("Coupe");
        carTypes.add("Minivan");
        carTypes.add("Station Wagon");
        carTypes.add("Pickup Truck");

        ObservableList<String> carType = FXCollections.observableList(carTypes);
        carTypeCmbBx.setItems(carType);
        setCellValueFactory();
        getAllCars();
    }
    private void getAllCars() throws SQLException, ClassNotFoundException {
        ObservableList<CarTM> obList = FXCollections.observableArrayList();
        List<CarDTO> carList = new ArrayList<>();
        for (Car car:carDAO.getAll()) {
            carList.add(new CarDTO(
                    car.getRegNo(),
                    car.getBrand(),
                    car.getCarType(),
                    car.getColour(),
                    car.getStatus()
            ));
        }

        for (CarDTO car : carList) {
            obList.add(new CarTM(
                    car.getRegNo(),
                    car.getBrand(),
                    car.getCarType(),
                    car.getColour(),
                    car.getStatus()
            ));
        }
        carTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnRegNo.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        columnCarType.setCellValueFactory(new PropertyValueFactory<>("carType"));
        columnColor.setCellValueFactory(new PropertyValueFactory<>("colour"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void regNoSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<CarDTO> carList = new ArrayList<>();
            for (Car car:carDAO.search(regNoTxt.getText())) {
                carList.add(new CarDTO(
                        car.getRegNo(),
                        car.getBrand(),
                        car.getCarType(),
                        car.getColour(),
                        car.getStatus()
                ));
            }
            if (!carList.isEmpty()){
                for (CarDTO car : carList) {
                    regNoTxt.setText(car.getRegNo());
                    carTypeCmbBx.setValue(car.getCarType());
                    colourTxt.setText(car.getColour());
                    brandTxt.setText(car.getBrand());
                    regNoTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Car Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addCarOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected = carDAO.save(new Car(regNoTxt.getText(), String.valueOf(carTypeCmbBx.getSelectionModel().getSelectedItem()),
                        colourTxt.getText(), brandTxt.getText(),"Available"));
                System.out.println("Ran");
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Car Added Successfully!").showAndWait();
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

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected = carDAO.update(new Car(regNoTxt.getText(),String.valueOf(carTypeCmbBx.getSelectionModel().getSelectedItem()),
                        colourTxt.getText(),brandTxt.getText(),""));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Car Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                regNoTxt.setDisable(false);
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

    public void removeCarOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the car?").showAndWait();
            if (comfirm.isPresent()){
                boolean isAffected=false;
                if (isCorrectPattern()){
                    isAffected = carDAO.delete(regNoTxt.getText());
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Car Removed Successfully!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    regNoTxt.setDisable(false);
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
        regNoTxt.setText("");
        brandTxt.setText("");
        colourTxt.setText("");
        carTypeCmbBx.setValue("");
        setCellValueFactory();
        getAllCars();
    }
    private boolean isCorrectPattern(){
        if (RegExPatterns.getNamePattern().matcher(colourTxt.getText()).matches() && RegExPatterns.getNamePattern().matcher(brandTxt.getText()).matches()){
            return true;
        }
        return false;
    }
}
