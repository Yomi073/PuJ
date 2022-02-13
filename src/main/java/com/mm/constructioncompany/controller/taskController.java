package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import com.mm.constructioncompany.model.*;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class taskController implements Initializable {


    @FXML
    private TextField searchTxt;

    @FXML
    private TableView tasksTbl;

    @FXML
    private TableColumn IDTblCol;

    @FXML
    private TableColumn workerTblCol;

    @FXML
    private TableColumn dateTblCol;

    @FXML
    private TableColumn clientTblCol;

    @FXML
    private DatePicker datePick;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnPreview;

    @FXML
    private Button btnAddMaterial;

    @FXML
    private ComboBox selectWorker;

    @FXML
    private ComboBox selectClient;

    public Task selectedTask = null;


    @FXML
    void onDelete(ActionEvent event) {

        if (selectedTask != null) {
            try {
                selectedTask.delete();
                this.fillTasks();
                this.removeSelection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void onSave(ActionEvent event) {

        boolean isSelectWorkerEmpty = this.selectWorker.getSelectionModel().isEmpty();
        boolean isSelectClientEmpty = this.selectClient.getSelectionModel().isEmpty();
        System.out.println(isSelectWorkerEmpty);
        System.out.println(isSelectClientEmpty);
        System.out.println(datePick.getValue());
        if (isSelectWorkerEmpty || datePick.getValue() == null || isSelectClientEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter all fields!", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        } else {
            User worker = (User) this.selectWorker.getValue();
            Client client = (Client) this.selectClient.getValue();
            String date = this.datePick.getValue().toString();
            Task t;
            if (this.selectedTask == null) {
                t = new Task();
            } else {
                t = this.selectedTask;
            }
            t.setUser_FK(worker.getId());
            t.setDate(Date.valueOf(date));
            t.setStartTime(Time.valueOf(LocalTime.now()));
            t.setEndTime(Time.valueOf(LocalTime.now()));
            t.setPauseLength(0.5);
            t.setClient_FK(client.getId());

            try {
                if (this.selectedTask == null) {
                    t.save();
                    this.fillTasks();
                    this.removeSelection();
                } else {
                    t.update();
                    this.fillTasks();
                    this.removeSelection();
                }
                fillTasks();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<?> userList = Table.list(User.class);
            List<?> clientList = Table.list(Client.class);
            ObservableList<?> observableTaskList = FXCollections.observableList(userList);
            ObservableList<?> observableClientList = FXCollections.observableList(clientList);
            selectWorker.setItems(observableTaskList);
            selectClient.setItems(observableClientList);
            btnPreview.setDisable(true);
            btnAddMaterial.setDisable(true);

            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, Long>, SimpleLongProperty>) taskLongCellDataFeatures -> new SimpleLongProperty(taskLongCellDataFeatures.getValue().getId())
            );
            workerTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, String>, SimpleStringProperty>) taskLongCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(taskLongCellDataFeatures.getValue().getUser().getUserName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
            );
            clientTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, String>, SimpleStringProperty>) taskLongCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(taskLongCellDataFeatures.getValue().getClient().getFirstName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
            );

            dateTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, String>, SimpleStringProperty>) taskLongCellDataFeatures -> new SimpleStringProperty(taskLongCellDataFeatures.getValue().getDate().toString())
            );


            fillTasks();

/*
            searchTxt.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        try {
                            usersTbl.setItems(filterList((List<User>) usersObservableList, newValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
 */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTasks() {
        try {
            List<?> tasksList = Table.list(Task.class);
            ObservableList<?> tasksObservableList = FXCollections.observableList(tasksList);
            this.tasksTbl.setItems(tasksObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void removeSelection() {
        this.selectedTask = null;
        this.fillTasks();
        this.selectWorker.setValue("");
        this.selectClient.setValue("");
        this.btnSave.setText("Add");
        this.datePick.setValue(null);
        this.btnPreview.setDisable(true);
        this.btnAddMaterial.setDisable(true);
    }

    @FXML
    public void selectTask(MouseEvent evt) throws Exception {
        this.selectedTask = (Task) this.tasksTbl.getSelectionModel().getSelectedItem();
        this.btnSave.setText("Edit");
        this.selectClient.setValue(this.selectedTask.getClient().getFirstName());
        this.selectWorker.setValue(this.selectedTask.getUser().getUserName());
        this.datePick.setValue(this.selectedTask.getDate().toLocalDate());
        this.btnPreview.setDisable(false);
        this.btnAddMaterial.setDisable(false);

    }

    public void onPreview() throws Exception {
        if(selectedTask!=null)
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("invoice.fxml"));
            Parent root = fxmlLoader.load();
            invoiceController incont = fxmlLoader.getController();
            incont.setSelectedTask(selectedTask);
            incont.initController();
            Stage stage = new Stage();
            stage.setTitle("Set material");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    public void onAddMaterial() throws IOException {
        if (selectedTask != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("newTask.fxml"));
            Parent root = fxmlLoader.load();
            newTaskController newTaskController = fxmlLoader.getController();
            newTaskController.selectedTask = this.selectedTask;
            Stage stage = new Stage();
            stage.setTitle("Set material");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

    }
}







