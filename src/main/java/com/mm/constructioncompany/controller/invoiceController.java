package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class invoiceController implements Initializable {

    @FXML
    private TableView materialsTbl;

    @FXML
    private TableColumn idTblCol;

    @FXML
    private TableColumn nameTblCol;

    @FXML
    private TableColumn quantityTblCol;

    @FXML
    private TableColumn priceTblCol;

    @FXML
    private TableColumn sumTblCol;

    @FXML
    private Label clientAdressLbl;

    @FXML
    private Label clientIdLbl;

    @FXML
    private Label clientNameLbl;

    @FXML
    private Label clientPhoneLbl;

    @FXML
    private TextArea commentTxt;

    @FXML
    private Label dateLbl;

    @FXML
    private Label invoiceLbl;


    private Task selectedTask=null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

                idTblCol.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<tempTable, Long>, SimpleLongProperty>) tempTableLongCellDataFeatures -> new SimpleLongProperty(tempTableLongCellDataFeatures.getValue().getId())
                );
                nameTblCol.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<tempTable, String>, SimpleStringProperty>) tempTableLongCellDataFeatures -> new SimpleStringProperty(tempTableLongCellDataFeatures.getValue().getName())
                );
                quantityTblCol.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<tempTable, Double>, SimpleDoubleProperty>) tempTableLongCellDataFeatures -> new SimpleDoubleProperty(tempTableLongCellDataFeatures.getValue().getQuantity())
                );

                priceTblCol.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<tempTable, Double>, SimpleDoubleProperty>) tempTableLongCellDataFeatures -> new SimpleDoubleProperty(tempTableLongCellDataFeatures.getValue().getSellingPrice())
                );

                sumTblCol.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<tempTable, Double>, SimpleDoubleProperty>) tempTableLongCellDataFeatures -> new SimpleDoubleProperty(tempTableLongCellDataFeatures.getValue().getSum())
                );


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void fillTempTable(){
        try {
            List<tempTable> tempTableList = Table.listTempTable(makeQuery());
            ObservableList<tempTable> materialsObservableList = FXCollections.observableList(tempTableList);
            this.materialsTbl.setItems(materialsObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSelectedTask(Task t)
    {
        this.selectedTask=t;
    }
    public ResultSet makeQuery() throws SQLException {
        String SQL = "SELECT MaterialStock.id, MaterialStock.name, SUM(MaterialConsumption.quantity),MaterialStock.sellingPrice FROM Task LEFT JOIN MaterialConsumption ON Task.id = MaterialConsumption.task_FK LEFT JOIN MaterialStock ON MaterialConsumption.materialStock_FK = MaterialStock.id WHERE Task.id="+selectedTask.getId()+" GROUP BY MaterialStock.name ";
        Statement stmt = DatabaseConnection.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        return rs;
    }
    public void initController()throws Exception
    {
        fillTempTable();
        this.invoiceLbl.setText(String.valueOf(this.selectedTask.getId()));
        this.clientIdLbl.setText(String.valueOf(this.selectedTask.getClient().getId()));
        this.dateLbl.setText(this.selectedTask.getDate().toString());
        this.clientNameLbl.setText(this.selectedTask.getClient().getFirstName()+" "+this.selectedTask.getClient().getLastName());
        this.clientPhoneLbl.setText(this.selectedTask.getClient().getPhoneNumber());
        this.clientAdressLbl.setText(this.selectedTask.getClient().getAdress());
    }


}