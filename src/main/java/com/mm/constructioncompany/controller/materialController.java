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
                selectedMaterial.delete();
                this.fillUsers();
                this.removeSelection();
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

        if (name.equals("")||purchasePrice.equals("")||sellingPrice.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are mandatory except length and pieces!", ButtonType.OK);
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
            m.setPurchasePrice(purchasePrice);
            m.setSellingPrice(sellingPrice);
            try {
                if (this.selectedMaterial == null) {
                    m.save();
                    this.fillUsers();
                    this.removeSelection();
                }
                else
                {
                    m.update();
                    this.fillUsers();
                    this.removeSelection();
                }

                fillUsers();
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
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, String>, SimpleStringProperty>) materialsLongCellDataFeatures -> new SimpleStringProperty(materialsLongCellDataFeatures.getValue().getPurchasePrice())
            );
            sellingPriceTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialStock, String>, SimpleStringProperty>) materialsLongCellDataFeatures -> new SimpleStringProperty(materialsLongCellDataFeatures.getValue().getSellingPrice())
            );

            fillUsers();

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

    private void fillUsers(){
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
        this.fillUsers();
        this.btnAdd.setText("Add");
        this.nameTxt.setText("");
        this.quantityTxt.setText("");
        this.sellingPriceTxt.setText("");
        this.purchasePriceTxt.setText("");

    }

    @FXML
    public void selectMaterial(MouseEvent evt){
        this.selectedMaterial = (MaterialStock) this.materialsTbl.getSelectionModel().getSelectedItem();
        this.btnAdd.setText("Edit");
        this.nameTxt.setText(this.selectedMaterial.getName());
        this.quantityTxt.setText(String.valueOf(this.selectedMaterial.getQuantity()));
        this.sellingPriceTxt.setText(this.selectedMaterial.getSellingPrice());
        this.purchasePriceTxt.setText(this.selectedMaterial.getPurchasePrice());

    }



    private boolean searchFindsMaterial(MaterialStock material, String searchText) throws Exception {
        return (material.getName().toLowerCase().contains(searchText.toLowerCase())) ||
                !               (String.valueOf((material.getQuantity())).contains(searchText.toLowerCase())) ||
                Integer.valueOf(material.getId()).toString().equals(searchText.toLowerCase())||
                (material.getSellingPrice().toLowerCase().contains(searchText.toLowerCase())) ||
                (material.getPurchasePrice().toLowerCase().contains(searchText.toLowerCase()));
    }

    private ObservableList<MaterialStock> filterList(List<MaterialStock> list, String searchText) throws Exception {
        List<MaterialStock> filteredList = new ArrayList<>();
        for (MaterialStock material : list){
            if(searchFindsMaterial(material, searchText))
                filteredList.add(material);
        }
        return FXCollections.observableList(filteredList);
    }
}
