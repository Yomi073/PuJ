package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class materialController implements Initializable {

    @FXML
    private TableView materialsTbl;

    @FXML
    private TableColumn IDTblCol;

    @FXML
    private TableColumn nameTblCol;

    @FXML
    private TableColumn quantityTblCol;

    @FXML
    private TableColumn purchasePriceTblCol;

    @FXML
    private TableColumn sellingPriceTblCol;

    @FXML
    private TextField searchTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField quantityTxt;

    @FXML
    private TextField purchasePriceTxt;

    @FXML
    private TextField sellingPriceTxt;

    @FXML
    private Button btnAdd;

    MaterialStock selectedMaterial=null;

    @FXML
    void onDelete(ActionEvent event) {
        if (selectedMaterial != null){
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure you want to delete material "+this.selectedMaterial.getName()+"?");
                alert.setContentText("Delete?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton)
                    {
                        try {
                            selectedMaterial.delete();
                            this.fillMaterials();
                            this.removeSelection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        alert.close();
                    }
                });

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        String name = this.nameTxt.getText();
        String quantity = this.quantityTxt.getText();
        String purchasePrice = this.purchasePriceTxt.getText();
        String sellingPrice = this.sellingPriceTxt.getText();

        if (name.equals("")|| purchasePrice.equals("")|| sellingPrice.equals("") || quantity==null || !isNumeric(sellingPrice) || !isNumeric(purchasePrice)  || !isNumeric(quantity))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are mandatory!Quantity,selling and purchase price must be a number", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        }
        else
        {
            MaterialStock m;
            if (this.selectedMaterial == null) {
                m = new MaterialStock();
            } else {
                m= this.selectedMaterial;
            }
            m.setName(name);
            m.setQuantity(Double.valueOf(quantity));
            m.setPurchasePrice(Double.valueOf(purchasePrice));
            m.setSellingPrice(Double.valueOf(sellingPrice));
            try {
                if (this.selectedMaterial == null) {
                    m.save();
                    this.fillMaterials();
                    this.removeSelection();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "New material added succesfully", ButtonType.OK);
                    alert.setTitle("Information");
                    alert.setHeaderText("Success!");
                    alert.showAndWait();
                }
                else
                {
                    m.update();
                    this.fillMaterials();
                    this.removeSelection();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material edited succesfully", ButtonType.OK);
                    alert.setTitle("Information");
                    alert.setHeaderText("Success!");
                    alert.showAndWait();
                }
                fillMaterials();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            List<?> materialsList = Table.list(MaterialStock.class);
            ObservableList<?> materialsObservableList = FXCollections.observableList(materialsList);

            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, Long>, SimpleLongProperty>) materialsLongCellDataFeatures -> new SimpleLongProperty(materialsLongCellDataFeatures.getValue().getId())
            );
            nameTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, String>, SimpleStringProperty>) materialsLongCellDataFeatures -> new SimpleStringProperty(materialsLongCellDataFeatures.getValue().getName())
            );
            quantityTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, Double>, SimpleDoubleProperty>) materialsLongCellDataFeatures -> new SimpleDoubleProperty(materialsLongCellDataFeatures.getValue().getQuantity())
            );
            purchasePriceTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, Double>, SimpleDoubleProperty>) materialsLongCellDataFeatures -> new SimpleDoubleProperty(materialsLongCellDataFeatures.getValue().getPurchasePrice())
            );
            sellingPriceTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, Double>, SimpleDoubleProperty>) materialsLongCellDataFeatures -> new SimpleDoubleProperty(materialsLongCellDataFeatures.getValue().getSellingPrice())
            );

            fillMaterials();

            searchTxt.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        try {
                            materialsTbl.setItems(filterList((List<MaterialStock>) materialsObservableList, newValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillMaterials(){
        try {
            List<?> materialsList = Table.list(MaterialStock.class);
            ObservableList<?> materialsObservableList = FXCollections.observableList(materialsList);
            this.materialsTbl.setItems(materialsObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void removeSelection() {
        this.selectedMaterial = null;
        this.fillMaterials();
        this.btnAdd.setText("Add");
        this.nameTxt.setText("");
        this.quantityTxt.setText("");
        this.sellingPriceTxt.setText("");
        this.purchasePriceTxt.setText("");

    }

    @FXML
    public void selectMaterial(MouseEvent evt){
        this.selectedMaterial = (MaterialStock) this.materialsTbl.getSelectionModel().getSelectedItem();
        if(this.selectedMaterial!=null)
        {
            this.btnAdd.setText("Edit");
            this.nameTxt.setText(this.selectedMaterial.getName());
            this.quantityTxt.setText(String.valueOf(this.selectedMaterial.getQuantity()));
            this.sellingPriceTxt.setText(String.valueOf(this.selectedMaterial.getSellingPrice()));
            this.purchasePriceTxt.setText(String.valueOf(this.selectedMaterial.getPurchasePrice()));
        }
    }



    private boolean searchFindsMaterial(MaterialStock material, String searchText) throws Exception {
        return (material.getName().toLowerCase().contains(searchText.toLowerCase())) ||
               (String.valueOf((material.getQuantity())).contains(searchText.toLowerCase())) ||
               (Integer.valueOf(material.getId()).toString().equals(searchText.toLowerCase())) ||
               (Double.valueOf(material.getSellingPrice()).toString().equals(searchText.toLowerCase())) ||
               (Double.valueOf(material.getPurchasePrice()).toString().equals(searchText.toLowerCase()));
    }


    private ObservableList<MaterialStock> filterList(List<MaterialStock> list, String searchText) throws Exception {
        List<MaterialStock> filteredList = new ArrayList<>();
        for (MaterialStock material : list){
            if(searchFindsMaterial(material, searchText))
                filteredList.add(material);
        }
        return FXCollections.observableList(filteredList);
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    void remove(MouseEvent event) {
        if(this.selectedMaterial!=null)
        {
            materialsTbl.getSelectionModel().clearSelection();
            removeSelection();
        }

    }
}
