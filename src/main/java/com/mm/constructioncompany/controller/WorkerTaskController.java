package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import com.mm.constructioncompany.model.DatabaseConnection;
import com.mm.constructioncompany.model.Table;
import com.mm.constructioncompany.model.Task;
import com.mm.constructioncompany.model.WorkerView;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class WorkerTaskController implements Initializable {


    @FXML
    private TableColumn clientAddressTblCol;

    @FXML
    private TableColumn clientNameTblCol;

    @FXML
    private TableColumn idTblCol;

    @FXML
    private TableColumn taskDateTblCol;

    @FXML
    private TableView tasksTbl;

    WorkerView selectedTask=null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTblCol.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<WorkerView, Long>, SimpleLongProperty>) workerTaskLongCellDataFeatures -> new SimpleLongProperty(workerTaskLongCellDataFeatures.getValue().getTaskId())
        );
        clientNameTblCol.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<WorkerView, String>, SimpleStringProperty>) workerTaskStringCellDataFeatures -> new SimpleStringProperty(workerTaskStringCellDataFeatures.getValue().getClientName())
        );
        clientAddressTblCol.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<WorkerView, String>, SimpleStringProperty>) workerTaskStringCellDataFeatures -> new SimpleStringProperty(workerTaskStringCellDataFeatures.getValue().getClientAddress())
        );
        taskDateTblCol.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<WorkerView, String>, SimpleStringProperty>) workerTaskStringCellDataFeatures -> new SimpleStringProperty(workerTaskStringCellDataFeatures.getValue().getDate())
        );
    fillWorkerTasks();
    }

    private void fillWorkerTasks() {
        try {
            List<WorkerView> workerTasks = Table.WorkerTasks(makeQueryforTasks());
            ObservableList<WorkerView> workerTasksObservableList = FXCollections.observableList(workerTasks);
            this.tasksTbl.setItems(workerTasksObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet makeQueryforTasks()throws SQLException {
        String SQL = "SELECT Task.id,Client.firstName,Client.address,Task.date,Client.id FROM Client LEFT JOIN Task ON Client.id = Task.Client_FK LEFT JOIN User ON User_FK=User.id  WHERE Task.User_FK="+ LoginController.loggedUser.getId();
        Statement stmt = DatabaseConnection.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        return rs;
    }

    @FXML
    public void selectTask(MouseEvent evt) throws Exception {
        this.selectedTask = (WorkerView) this.tasksTbl.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void taskDetails(ActionEvent event) throws Exception {
        if(selectedTask!=null)
        {
            Task t=new Task();

            t.setId(this.selectedTask.getTaskId());
            t.setDate(Date.valueOf(this.selectedTask.getDate()));
            t.setClient_FK(this.selectedTask.getClientId());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("invoice.fxml"));
            Parent root = fxmlLoader.load();
            InvoiceController incont = fxmlLoader.getController();
            incont.setSelectedTask(t);
            incont.initController();
            Stage stage = new Stage();
            stage.setTitle("View materials");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    @FXML
    public void remove(MouseEvent event) {
        tasksTbl.getSelectionModel().clearSelection();
    }

}
