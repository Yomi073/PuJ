package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.model.CryptMD5;
import com.mm.constructioncompany.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class workerProfileController implements Initializable {

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField phoneNumTxt;

    @FXML
    private TextField userNameTxt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    this.firstNameTxt.setText(loginController.loggedUser.getFirstName());
    this.lastNameTxt.setText(loginController.loggedUser.getLastName());
    this.addressTxt.setText(loginController.loggedUser.getAddress());
    this.phoneNumTxt.setText(loginController.loggedUser.getPhoneNumber());
    this.emailTxt.setText(loginController.loggedUser.getEmail());
    this.userNameTxt.setText(loginController.loggedUser.getUserName());
    this.passwordTxt.setText(loginController.loggedUser.getPassword());
    }

    @FXML
    void editProfile(MouseEvent event) throws Exception {
    String firstName=this.firstNameTxt.getText();
    String lastName=this.lastNameTxt.getText();
    String address=this.lastNameTxt.getText();
    String phoneNum=this.phoneNumTxt.getText();
    String email=this.emailTxt.getText();
    String userName=this.userNameTxt.getText();
    String password=this.passwordTxt.getText();

    User u=loginController.loggedUser;

    u.setFirstName(firstName);
    u.setLastName(lastName);
    u.setAddress(address);
    u.setPhoneNumber(phoneNum);
    u.setEmail(email);
    u.setUserName(userName);
    if(password.equals(u.getPassword()))
        u.setPassword((password));
    else
        u.setPassword(CryptMD5.cryptWithMD5(password));

    u.update();

    this.firstNameTxt.setText(u.getFirstName());
    this.lastNameTxt.setText(u.getLastName());
    this.addressTxt.setText(u.getAddress());
    this.phoneNumTxt.setText(u.getPhoneNumber());
    this.emailTxt.setText(u.getEmail());
    this.userNameTxt.setText(u.getUserName());
    this.passwordTxt.setText(u.getPassword());

    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully", ButtonType.OK);
    alert.setTitle("Success");
    alert.setHeaderText("Profile edited!");
    alert.showAndWait();

    }
}
