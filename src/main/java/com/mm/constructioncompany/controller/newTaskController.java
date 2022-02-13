package com.mm.constructioncompany.controller;

import com.mm.constructioncompany.Main;
import com.mm.constructioncompany.model.MaterialConsumption;
import com.mm.constructioncompany.model.MaterialStock;
import com.mm.constructioncompany.model.Table;
import com.mm.constructioncompany.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class newTaskController  implements Initializable {

    @FXML
    private invoiceController invoiceController;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField commentTxt;

    @FXML
    private TextField quantityTxt;

    @FXML
    private ComboBox selectMaterial;

    Task selectedTask=null;

    @FXML
    void onClose(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {

    }


    @FXML
    public void onSave(ActionEvent event) {


        MaterialStock ms=(MaterialStock) this.selectMaterial.getValue();

        MaterialConsumption materialConsumption=new MaterialConsumption();

        materialConsumption.setMaterialStock_FK(ms.getId());

        materialConsumption.setTask_FK(this.selectedTask.getId());
        System.out.println(selectedTask.getId());

        materialConsumption.setQuantity(2.0);


        try {
            materialConsumption.save();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<?>materialStock= Table.list(MaterialStock.class);
            ObservableList<?>observableMaterialStockList= FXCollections.observableList(materialStock);
            selectMaterial.setItems(observableMaterialStockList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
