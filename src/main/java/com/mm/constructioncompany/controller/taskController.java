package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.model.*;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    private TableColumn realEstateTblCol;

    @FXML
    private ComboBox selectRealEstate;

    @FXML
    private ComboBox selectWorker;

    @FXML
    private DatePicker datePick;

    @FXML
    private Button btnSave;

    Task selectedTask=null;

    @FXML
    void onDelete(ActionEvent event) {

        if (selectedTask != null){
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

        boolean isSelectWorkerEmpty = selectWorker.getSelectionModel().isEmpty();
        boolean isRealEstateEmpty = selectWorker.getSelectionModel().isEmpty();

        if (isSelectWorkerEmpty || isRealEstateEmpty || datePick.getValue()==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter all fields!", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        }

        else
        {
            User worker = (User) this.selectWorker.getValue();
            RealEstate realestate = (RealEstate) this.selectRealEstate.getValue();
            String date = this.datePick.getValue().toString();
            Task t;
            if (this.selectedTask == null) {
                t = new Task();
            } else {
                t = this.selectedTask;
            }
            t.setRealEstate_FK(realestate.getId());
            t.setUser_FK(worker.getId());
            t.setDate(Date.valueOf(date));
            t.setStartTime(Time.valueOf(LocalTime.now()));
            t.setEndTime(Time.valueOf(LocalTime.now()));
            t.setPauseLength(30);

            try {
                if (this.selectedTask == null) {
                    t.save();
                    this.fillTasks();
                    this.removeSelection();
                }
                else
                {
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
            List<?>workersList=Table.list(User.class);

            List<?>realEstatesList=Table.list(RealEstate.class);

            ObservableList<?>workersObservableList=FXCollections.observableList(workersList);
            ObservableList<?> realEstatesObservableList =FXCollections.observableList(realEstatesList);

            selectWorker.setItems(workersObservableList);
            selectRealEstate.setItems(realEstatesObservableList);


            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, Long>, SimpleLongProperty>) taskLongCellDataFeatures -> new SimpleLongProperty(taskLongCellDataFeatures.getValue().getId())
            );
            workerTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, String>, SimpleStringProperty>) taskLongCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(taskLongCellDataFeatures.getValue().getUser().getFirstName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
            );
            realEstateTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<Task, String>, SimpleStringProperty>) taskLongCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(taskLongCellDataFeatures.getValue().getRealEstate().getCity());
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

    private void fillTasks(){
        try {
            List<?> tasksList = Table.list(Task.class);
            ObservableList<?> tasksObservableList = FXCollections.observableList(tasksList);
            this.tasksTbl.setItems(tasksObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void removeSelection(){
        this.selectedTask = null;
        this.fillTasks();
        this.btnSave.setText("Add");
        this.selectRealEstate.setValue("");
        this.selectWorker.setValue("");
        this.datePick.setValue(null);

    }

    @FXML
    public void selectTask(MouseEvent evt) throws Exception {
        this.selectedTask = (Task) this.tasksTbl.getSelectionModel().getSelectedItem();
        this.btnSave.setText("Edit");
        this.selectWorker.setValue(this.selectedTask.getUser().getId());
        this.selectRealEstate.setValue(this.selectedTask.getRealEstate().getId());
        this.datePick.setValue(this.selectedTask.getDate().toLocalDate());
    }

}
