package com.edengardensigiriya.edengarden.controller;

import com.edengardensigiriya.edengarden.bo.BOFactory;
import com.edengardensigiriya.edengarden.bo.custom.CustomerBO;
import com.edengardensigiriya.edengarden.dao.custom.CustomerDAO;
import com.edengardensigiriya.edengarden.db.DBConnection;
import com.edengardensigiriya.edengarden.dto.CustomerDTO;
import com.edengardensigiriya.edengarden.util.RegExPatterns;
import com.edengardensigiriya.edengarden.dto.tm.CustomerTM;
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
import java.util.Arrays;
import java.util.List;

public class CustomerManageFormController{
    public AnchorPane custRoot;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField nicTxt;
    public TextField emailTxt;
    public TextField addressTxt;
    public TextField contactTxt;
    public Button addContactBtn;
    public RadioButton maleRdBtn;
    public ToggleGroup gender;
    public RadioButton femaleRdBtn;
    public TableView<CustomerTM> custTbl;
    public Button addBtn;
    public TableColumn columnCustId;
    public TableColumn columnCustName;
    public TableColumn columnCustNic;
    public TableColumn columnCustEmail;
    public TableColumn columnCustAddress;
    public TableColumn columnCustContact;
    public TableColumn columnCustGender;
    public Button updateBtn;
    public ComboBox contactCmbBx;
    static String con;
    CustomerBO customer= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        getAllCustomers();
    }

    private void getAllCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        List<CustomerDTO> cusList = customer.getAllCustomers();

        for (CustomerDTO customerDTO : cusList) {
            obList.add(new CustomerTM(
                    customerDTO.getCustId(),
                    customerDTO.getCustName(),
                    customerDTO.getCustNic(),
                    customerDTO.getCustEmail(),
                    customerDTO.getCustAddress(),
                    customerDTO.getCustContact(),
                    customerDTO.getCustGender()
            ));
        }
        custTbl.setItems(obList);
        contactCmbBx.getSelectionModel().getSelectedItem();
    }

    void setCellValueFactory() {
        columnCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        columnCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        columnCustNic.setCellValueFactory(new PropertyValueFactory<>("custNic"));
        columnCustEmail.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        columnCustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        columnCustContact.setCellValueFactory(new PropertyValueFactory<>("custContact"));
        columnCustGender.setCellValueFactory(new PropertyValueFactory<>("custGender"));
    }

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            CustomerDAO.contact.clear();
            List<CustomerDTO> cusList = customer.searchCustomers(idTxt.getText());
            if (!cusList.isEmpty()){
                for (CustomerDTO customerDTO : cusList) {
                    nameTxt.setText(customerDTO.getCustName());
                    nicTxt.setText(customerDTO.getCustNic());
                    emailTxt.setText(customerDTO.getCustEmail());
                    addressTxt.setText(customerDTO.getCustAddress());
                    contactCmbBx.setItems(getContactObList(customerDTO.getCustContact()));
                    if (customerDTO.getCustGender().equals("MALE")){
                        maleRdBtn.setSelected(true);
                    }else{
                        femaleRdBtn.setSelected(true);
                    }
                    idTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Customer Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private ObservableList getContactObList(String custContact) {
        String[] contacts=custContact.split(" , ");
        List<String> conList = Arrays.asList(contacts);
        ObservableList<String> cont = FXCollections.observableList(conList);
        int length=contacts.length;
        while (length>0){
            CustomerDAO.contact.add(contacts[length-1]);
            length-=1;
        }
        return cont;
    }

    public void addContactOnAction(ActionEvent actionEvent) {
        if (contactCmbBx.getItems().contains(con)) {
            CustomerDAO.contact.remove(con);
        }
        if (RegExPatterns.getMobilePattern().matcher(contactTxt.getText()).matches()){
            boolean isAlreadyHas = customer.addContact(contactTxt.getText());
            if (!isAlreadyHas) {
                new Alert(Alert.AlertType.WARNING, "Contact Already Added!").showAndWait();
            } else {
                ObservableList<String> cont = FXCollections.observableList(CustomerDAO.contact);
                contactCmbBx.setItems(cont);
                contactCmbBx.setValue("Contact List");
                contactTxt.setText("");
            }
        }
        else{
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number!").showAndWait();
        }
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, GeneralSecurityException, IOException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected = customer.saveCustomer(new CustomerDTO(customer.newIdGenerate(),
                        nameTxt.getText(), nicTxt.getText(), addressTxt.getText(), emailTxt.getText(), String.join(" , ", CustomerDAO.contact),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE"));
            }
            if (isAffected) {
                System.out.println(456);
                new Alert(Alert.AlertType.INFORMATION, "Customer Added!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                resetPage();
            } else {
                System.out.println(789);
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
            boolean isAffected =false;
            if (isCorrectPattern()){
                isAffected = customer.updateCustomer(new CustomerDTO(idTxt.getText(),
                        nameTxt.getText(), nicTxt.getText(), addressTxt.getText(), emailTxt.getText(), String.join(" , ", CustomerDAO.contact),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE"));
                System.out.println(isCorrectPattern());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").showAndWait();
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

    private void resetPage() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        getAllCustomers();
        idTxt.setText("");
        nameTxt.setText("");
        nicTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
        contactTxt.setText("");
        contactCmbBx.setValue("Contact List");
        CustomerDAO.contact.clear();
    }

    public void setTxtBxValueOnAction(ActionEvent actionEvent) {
        contactTxt.setText(String.valueOf(contactCmbBx.getSelectionModel().getSelectedItem()));
        con = contactTxt.getText();
    }
    private boolean isCorrectPattern(){
        if ((RegExPatterns.getEmailPattern().matcher(emailTxt.getText()).matches()) && RegExPatterns.getNamePattern().matcher(nameTxt.getText()).matches() && (RegExPatterns.getIdPattern().matcher(nicTxt.getText()).matches() ||RegExPatterns.getOldIDPattern().matcher(nicTxt.getText()).matches() ) && RegExPatterns.getAddressPattern().matcher(addressTxt.getText()).matches()){
            return true;
        }
        return false;
    }
    /*public void sendMail() throws MessagingException, GeneralSecurityException, IOException {
        SendEmail.sendMail(emailTxt.getText(),"Customer Verification","Dear Customer," +
                "\nYour Customer ID:"+customer.getCustId()+"\nName:"+nameTxt.getText()+"\nRegistered time: "+ LocalDateTime.now()+"\n\nThank you for using our service!\n\nHotel Eden Garden,\nInamaluwa,\nSeegiriya");
    }*/
}
