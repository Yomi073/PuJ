package com.mm.constructioncompany;

import com.mm.constructioncompany.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class  Main extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Main.primaryStage = stage;
        Main.showWindow("login.fxml","Login to the system", 1200, 400);
    }

    public static void showWindow(String viewName, String title, int w, int h) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource(viewName));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root.load(), w, h));
        primaryStage.show();
    }
    public static void main(String[] args) {
        try {

            Table.create(Role.class);
            Table.create(User.class);
            Table.create(MaterialStock.class);
            Table.create(Client.class);
            Table.create(Task.class);
            Table.create(MaterialConsumption.class);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        launch();
    }
}