package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class sidebarController {

    @FXML
    protected static Label loggedUserLbl;

    @FXML
    private Button btnUsers;

    @FXML
    void btnClientsClicked(ActionEvent event)throws Exception{
        Main.showWindow("client.fxml","Clients", 1200, 800);
    }

    @FXML
    void btnMaterialsClicked(ActionEvent event) throws Exception{
        Main.showWindow("materials.fxml","Materials", 1200, 800);
    }


    @FXML
    void btnTasksClicked(ActionEvent event)throws Exception
    {
        Main.showWindow("tasks.fxml","Tasks", 1200, 800);
    }

    @FXML
    void btnUsersClicked(ActionEvent event) throws Exception
    {
        Main.showWindow("users.fxml","Users", 1200, 800);
    }


    @FXML
    void btnLogoutClicked(ActionEvent event)throws Exception
    {
        Main.showWindow("login.fxml","Welcome to the login", 1200, 400);
    }

}
