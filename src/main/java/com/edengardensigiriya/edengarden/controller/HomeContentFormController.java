package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.bo.BOFactory;
import com.edengardensigiriya.edengarden.bo.custom.ChangeUserBO;
import com.edengardensigiriya.edengarden.bo.custom.HomeBO;
import com.edengardensigiriya.edengarden.dao.DAOFactory;
import com.edengardensigiriya.edengarden.dao.custom.QueryDAO;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;

public class HomeContentFormController {
    public AnchorPane homeRoot;
    public BarChart todayUsageBarChrt;
    public BarChart monthUsageBarChrt;
    HomeBO homeBO= (HomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HOME);

    public void initialize() throws SQLException {
        setDayBarChart();
        setMonthBarChart();
    }

    private void setMonthBarChart() throws SQLException {
        String rooms="Rooms";
        String rental="Rentals";
        String transport="Transport";
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Services");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Customer Count");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        System.out.println(LocalDate.now().getMonth());
        series.getData().add(new XYChart.Data<>(rooms, homeBO.getMonthBookingCount()));
        series.getData().add(new XYChart.Data<>(rental, (homeBO.getMonthCarRentCount()+homeBO.getMonthBicycleRentCount())));
        series.getData().add(new XYChart.Data<>(transport, homeBO.getMonthTransCount()));

        //Setting the data to bar chart
        monthUsageBarChrt.getData().addAll(series);
    }

    private void setDayBarChart() throws SQLException {
        String rooms="Rooms";
        String rental="Rentals";
        String transport="Transport";
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Services");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Customer Count");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        System.out.println(LocalDate.now());
        series.getData().add(new XYChart.Data<>(rooms, homeBO.getBookingCount()));
        series.getData().add(new XYChart.Data<>(rental, (homeBO.getCarRentCount()+homeBO.getBicycleCount())));
        series.getData().add(new XYChart.Data<>(transport, homeBO.getTransCount()));

        //Setting the data to bar chart
        todayUsageBarChrt.getData().addAll(series);
    }
}
