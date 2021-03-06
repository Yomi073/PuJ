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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class AddMaterialController implements Initializable {


    @FXML
    private TextField quantityTxt;

    @FXML
    private ComboBox selectMaterial;

    @FXML
    private TableView materialsTbl;

    @FXML
    private TableColumn nameTblCol;

    @FXML
    private TableColumn priceTblCol;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn quantityTblCol;

    @FXML
    private TableColumn idTblCol;

    private MaterialConsumption selectedMaterial=null;

    private Task selectedTask=null;


    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onDelete(ActionEvent event) {
        if (selectedMaterial != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure you want to delete material "+this.selectedMaterial.getId()+"?");
                alert.setContentText("Delete?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton)
                    {
                        try {
                            MaterialStock materialStock = (MaterialStock) this.selectMaterial.getValue();
                            Double quantity= Double.valueOf(quantityTxt.getText());
                            selectedMaterial.delete();
                            MaterialStock stockQuantityObject= (MaterialStock) Table.get(MaterialStock.class,materialStock.getId());
                            Double stockQuantity=stockQuantityObject.getQuantity();
                            stockQuantityObject.setQuantity(stockQuantity+quantity);
                            stockQuantityObject.update();
                            this.fillMaterialConsumption();
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
    public void onSave(ActionEvent event) throws Exception {

        boolean isSelectMaterialEmpty =(this.selectMaterial.getValue() == null);


        if ( isSelectMaterialEmpty || quantityTxt.getText().equals("") || !isNumeric(quantityTxt.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are mandatory and quantity must be a number!", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        } else {
            MaterialStock materialStock = (MaterialStock) this.selectMaterial.getValue();
            MaterialStock stockQuantityObject= (MaterialStock) Table.get(MaterialStock.class,materialStock.getId());
            Double stockQuantity=stockQuantityObject.getQuantity();

            MaterialConsumption mc;
            if (this.selectedMaterial == null) {
                mc = new MaterialConsumption();
            } else {
                mc = this.selectedMaterial;
            }

                try {
                    if (this.selectedMaterial == null) {
                        if(Double.valueOf(quantityTxt.getText())>stockQuantity)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "There is not enough on stock!Only "+stockQuantity+" left", ButtonType.OK);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Error!");
                            alert.showAndWait();
                        }
                        else
                        {
                            mc.setQuantity(Double.valueOf(quantityTxt.getText()));
                            mc.setTask_FK(selectedTask.getId());
                            mc.setMaterialStock_FK(materialStock.getId());

                            mc.save();
                            stockQuantityObject.setQuantity(stockQuantity-Double.valueOf(quantityTxt.getText()));
                            stockQuantityObject.update();


                            this.fillMaterialConsumption();
                            this.removeSelection();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material added succesfully", ButtonType.OK);
                            alert.setTitle("Information");
                            alert.setHeaderText("Success!");
                            alert.showAndWait();

                        }
                    } else {
                            if(Double.valueOf(quantityTxt.getText())>this.selectedMaterial.getQuantity())
                            {
                                if((this.selectedMaterial.getQuantity()+stockQuantity)<Double.valueOf(quantityTxt.getText()))
                                {
                                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is not enough on stock!Only "+stockQuantity+" left", ButtonType.OK);
                                    alert.setTitle("Warning");
                                    alert.setHeaderText("Error!");
                                    alert.showAndWait();
                                }
                                else {
                                    stockQuantityObject.setQuantity(stockQuantity-(Double.valueOf(quantityTxt.getText())-this.selectedMaterial.getQuantity()));
                                    stockQuantityObject.update();
                                    mc.setQuantity(Double.valueOf(quantityTxt.getText()));
                                    mc.setTask_FK(selectedTask.getId());
                                    mc.setMaterialStock_FK(materialStock.getId());
                                    mc.update();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material edited succesfully", ButtonType.OK);
                                    alert.setTitle("Information");
                                    alert.setHeaderText("Success!");
                                    alert.showAndWait();
                                }

                            }
                            else
                            {
                                stockQuantityObject.setQuantity(stockQuantity+(this.selectedMaterial.getQuantity()-Double.valueOf(quantityTxt.getText())));
                                stockQuantityObject.update();
                                mc.setQuantity(Double.valueOf(quantityTxt.getText()));
                                mc.setTask_FK(selectedTask.getId());
                                mc.setMaterialStock_FK(materialStock.getId());
                                mc.update();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material edited succesfully", ButtonType.OK);
                                alert.setTitle("Information");
                                alert.setHeaderText("Success!");
                                alert.showAndWait();
                            }
                            this.fillMaterialConsumption();
                            this.removeSelection();


                        }

                    fillMaterialConsumption();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<?>materialStock= Table.list(MaterialStock.class);
            ObservableList<?>observableMaterialStockList= FXCollections.observableList(materialStock);
            selectMaterial.setItems(observableMaterialStockList);

            idTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialConsumption, Long>, SimpleLongProperty>) materialConsumptionLongCellDataFeatures -> new SimpleLongProperty(materialConsumptionLongCellDataFeatures.getValue().getId())
            );

            nameTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialConsumption, String>, SimpleStringProperty>) materialConsumptionStringCellDataFeatures -> {
                        try {
                            return new SimpleStringProperty(materialConsumptionStringCellDataFeatures.getValue().getMaterialStock().getName());
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        return null;
                    }
            );
            quantityTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialConsumption, Double>, SimpleDoubleProperty>) materialConsumptionSDoubleCellDataFeatures -> new SimpleDoubleProperty(materialConsumptionSDoubleCellDataFeatures.getValue().getQuantity())
            );
            priceTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<MaterialConsumption, Double>, SimpleDoubleProperty>) materialConsumptionSDoubleCellDataFeatures -> {
                        try {
                            return new SimpleDoubleProperty(materialConsumptionSDoubleCellDataFeatures.getValue().getMaterialStock().getSellingPrice());
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        return null;
                    }
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillMaterialConsumption()
    {
        try {
            List<MaterialConsumption> materialConsumptions = Table.materialConsumptions(makeSelectQuery());
            ObservableList<?> materialsObservableList = FXCollections.observableList(materialConsumptions);
            this.materialsTbl.setItems(materialsObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void selectMaterial(MouseEvent evt) throws Exception
    {
        try {
            this.selectedMaterial = (MaterialConsumption) this.materialsTbl.getSelectionModel().getSelectedItem();
            if(selectedMaterial!=null)
            {
                this.btnAdd.setText("Edit");
                this.selectMaterial.setDisable(true);
                this.quantityTxt.setText(String.valueOf(this.selectedMaterial.getQuantity()));
                this.selectMaterial.setValue(this.selectedMaterial.getMaterialStock());
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }
    public void setSelectedTask(Task t)
    {
        this.selectedTask=t;
    }

    @FXML
    protected void removeSelection() {
        this.selectedMaterial = null;
        this.selectMaterial.setDisable(false);
        this.btnAdd.setText("Add");
        this.selectMaterial.valueProperty().set(null);
        this.quantityTxt.setText("");
    }

    public ResultSet makeSelectQuery() throws SQLException {
        String SQL = "SELECT MaterialConsumption.id,MaterialConsumption.materialStock_FK,MaterialConsumption.task_FK,MaterialConsumption.quantity FROM Task LEFT JOIN MaterialConsumption ON Task.id = MaterialConsumption.task_FK LEFT JOIN MaterialStock ON MaterialConsumption.materialStock_FK = MaterialStock.id WHERE Task.id="+this.selectedTask.getId();
        Statement stmt = DatabaseConnection.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        return rs;
    }

    @FXML
    public void remove(MouseEvent event) {
        if(this.selectedMaterial!=null)
        {
            materialsTbl.getSelectionModel().clearSelection();
            removeSelection();
        }

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


}
