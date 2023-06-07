package com.edengardensigiriya.edengarden.controller;

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
    QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

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
        series.getData().add(new XYChart.Data<>(rooms, queryDAO.getMonthBookingCount()));
        series.getData().add(new XYChart.Data<>(rental, (queryDAO.getMonthCarRentCount()+queryDAO.getMonthBicycleRentCount())));
        series.getData().add(new XYChart.Data<>(transport, queryDAO.getMonthTransCount()));

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
        series.getData().add(new XYChart.Data<>(rooms, queryDAO.getBookingCount()));
        series.getData().add(new XYChart.Data<>(rental, (queryDAO.getCarRentCount()+queryDAO.getBicycleCount())));
        series.getData().add(new XYChart.Data<>(transport, queryDAO.getTransCount()));

        //Setting the data to bar chart
        todayUsageBarChrt.getData().addAll(series);
    }
}
