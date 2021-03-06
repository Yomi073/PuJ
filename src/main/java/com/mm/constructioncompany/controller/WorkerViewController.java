package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkerViewController implements Initializable {


    @FXML
    public void logout(ActionEvent event) throws IOException {
        Main.showWindow("login.fxml","Login to the system",1200,400);
    }

    @FXML
    public void myProfile(ActionEvent event) throws IOException {
    Main.showWindow("workerProfile.fxml","My profile",900,700);
    }

    @FXML
    public void myTasks(ActionEvent event) throws IOException {
        Main.showWindow("workerTask.fxml","My tasks",900,700);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
