package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class SidebarController implements Initializable{

    @FXML
    public void btnClientsClicked(ActionEvent event)throws Exception{
        Main.showWindow("client.fxml","Clients", 1200, 740);
    }

    @FXML
    public void btnMaterialsClicked(ActionEvent event) throws Exception{
        Main.showWindow("material.fxml","Materials", 1200, 740);
    }

    @FXML
    public void btnTasksClicked(ActionEvent event)throws Exception {
        Main.showWindow("task.fxml","Tasks", 1200, 740);
    }

    @FXML
    public void btnUsersClicked(ActionEvent event) throws Exception {
        Main.showWindow("user.fxml","Users", 1200, 740);
    }

    @FXML
    public void btnLogoutClicked(ActionEvent event)throws Exception {
        Main.showWindow("login.fxml","Welcome to the login", 1200, 400);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
