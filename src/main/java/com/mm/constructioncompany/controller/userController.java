package com.mm.constructioncompany.controller;


import com.mm.constructioncompany.model.*;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;

import static java.util.Collections.addAll;

public class userController implements Initializable {

    @FXML
    private TableView usersTbl;

    @FXML
    private TableColumn IDTblCol;

    @FXML
    private TableColumn nameTblCol;

    @FXML
    private TableColumn surnameTblCol;

    @FXML
    private TableColumn addressTblCol;

    @FXML
    private TableColumn mobilePhoneTblCol;

    @FXML
    private TableColumn e_mailTblCol;

    @FXML
    private TableColumn userNameTblCol;

    @FXML
    private TableColumn passwordTblCol;

    @FXML
    private TableColumn roleTblCol;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField surnameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField mobilePhoneTxt;

    @FXML
    private TextField e_mailTxt;

    @FXML
    private ComboBox<String> roleTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Button btnSave;

    @FXML
    private TextField searchTxt;

    User selectedUser=null;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<String> rolesList = FXCollections.observableArrayList();
        rolesList.addAll( "Admin", "Worker");
        this.roleTxt.setItems(rolesList);

        try {
            List<?> usersList = Table.list(User.class);
            ObservableList<?> usersObservableList = FXCollections.observableList(usersList);


            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, Long>, SimpleLongProperty>) userLongCellDataFeatures -> new SimpleLongProperty(userLongCellDataFeatures.getValue().getId())
            );

            nameTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getFirstName())
            );

            surnameTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getLastName())
            );

            addressTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getAddress())
            );
            mobilePhoneTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getPhoneNumber())
            );
            e_mailTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getEmail())
            );
            userNameTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getUserName())
            );
            passwordTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) userLongCellDataFeatures -> new SimpleStringProperty(userLongCellDataFeatures.getValue().getPassword())
            );


            roleTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<User, String>, SimpleStringProperty>) roleLongCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(roleLongCellDataFeatures.getValue().getRole().getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
            );


            fillUsers();

            searchTxt.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        try {
                            usersTbl.setItems(filterList((List<User>) usersObservableList, newValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fillUsers(){
        try {
            List<?> usersList = Table.list(User.class);
            ObservableList<?> usersObservableList = FXCollections.observableList(usersList);
            this.usersTbl.setItems(usersObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void onSave (ActionEvent evt){

        String firstName = this.userNameTxt.getText();
        String lastName = this.surnameTxt.getText();
        String phoneNumb = this.mobilePhoneTxt.getText();
        String email = this.e_mailTxt.getText();
        String userName = this.userNameTxt.getText();
        String adress = this.addressTxt.getText();
        String roleName = this.roleTxt.getValue();
        if (firstName.equals("")||lastName.equals("")||phoneNumb.equals("")||email.equals("")||userName.equals("")||adress.equals("")||roleName==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter all fields!", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        }
        else
        {
            User u;
            if (this.selectedUser == null) {
                u = new User();
            } else {
                u = this.selectedUser;
            }
            u.setFirstName(nameTxt.getText());
            u.setLastName(surnameTxt.getText());
            u.setAddress(addressTxt.getText());
            u.setPhoneNumber(mobilePhoneTxt.getText());
            u.setEmail(e_mailTxt.getText());
            u.setUserName(userNameTxt.getText());
            u.setPassword(CryptMD5.cryptWithMD5(passwordTxt.getText()));


            if(roleTxt.getValue().equals("Admin"))
                u.setRole_FK(1);
            else
                u.setRole_FK(2);

            try {
                if (this.selectedUser == null) {
                    u.save();
                    this.fillUsers();
                    this.removeSelection();
                }
                else
                {
                    u.update();
                    this.fillUsers();
                    this.removeSelection();
                }

                fillUsers();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void selectUser(MouseEvent evt){
        this.selectedUser = (User) this.usersTbl.getSelectionModel().getSelectedItem();
        this.btnSave.setText("Edit");
        this.nameTxt.setText(this.selectedUser.getFirstName());
        this.surnameTxt.setText(this.selectedUser.getLastName());
        this.addressTxt.setText(this.selectedUser.getAddress());
        this.mobilePhoneTxt.setText(this.selectedUser.getPhoneNumber());
        this.e_mailTxt.setText(this.selectedUser.getEmail());
        this.userNameTxt.setText(this.selectedUser.getUserName());
        this.passwordTxt.setText(this.selectedUser.getPassword());
        try {
            this.roleTxt.setValue(this.selectedUser.getRole().getName().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void removeSelection(){
        this.selectedUser = null;
        this.fillUsers();
        this.btnSave.setText("Add");
        this.nameTxt.setText("");
        this.surnameTxt.setText("");
        this.addressTxt.setText("");
        this.mobilePhoneTxt.setText("");
        this.e_mailTxt.setText("");
        this.userNameTxt.setText("");
        this.passwordTxt.setText("");
        this.roleTxt.setValue("");
    }

    @FXML
    public void onDelete()
    {
        if (selectedUser != null){
            try {
                selectedUser.delete();
                this.fillUsers();
                this.removeSelection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean searchFindsUser(User user, String searchText) throws Exception {
        return (user.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                Integer.valueOf(user.getId()).toString().equals(searchText.toLowerCase())||
                (user.getAddress().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getPhoneNumber().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getUserName().toLowerCase().contains(searchText.toLowerCase())) ||
                (user.getRole().getName().toLowerCase().contains(searchText.toLowerCase())) ;
    }

    private ObservableList<User> filterList(List<User> list, String searchText) throws Exception {
        List<User> filteredList = new ArrayList<>();
        for (User user : list){
            if(searchFindsUser(user, searchText))
                filteredList.add(user);
        }
        return FXCollections.observableList(filteredList);
    }

}
