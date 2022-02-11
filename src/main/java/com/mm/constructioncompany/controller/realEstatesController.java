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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class realEstatesController implements Initializable {

    @FXML
    private TableView realEstatesTbl;

    @FXML
    private TableColumn IDTblCol;

    @FXML
    private TableColumn cityTblCol;

    @FXML
    private TableColumn streetTblCol;

    @FXML
    private TableColumn numberTblCol;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField streetTxt;

    @FXML
    private TextField numberTxt;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField searchTxt;

    RealEstate selectedRe=null;

    @FXML
    void onSave(ActionEvent event) {

        String city = this.cityTxt.getText();
        String street = this.streetTxt.getText();
        String number = this.numberTxt.getText();
        if (city.equals("")||street.equals("")||number.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter all fields!", ButtonType.OK);
            alert.setTitle("Warning");
            alert.setHeaderText("Input error!");
            alert.showAndWait();
        }
        else
        {
            RealEstate re;
            if (this.selectedRe == null) {
                re = new RealEstate();
            } else {
                re = this.selectedRe;
            }

            re.setCity(city);
            re.setStreet(street);
            re.setNumber(number);

            try {
                if (this.selectedRe == null) {
                    re.save();
                    this.fillRealEstates();
                    this.removeSelection();
                }
                else
                {
                    re.update();
                    this.fillRealEstates();
                    this.removeSelection();
                }

                fillRealEstates();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
    @FXML
    void onDelete(ActionEvent event) {
        if (selectedRe != null){
            try {
                selectedRe.delete();
                this.fillRealEstates();
                this.removeSelection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<?>RealEstates=null;
        try {
            RealEstates=Table.list(RealEstate.class);
            ObservableList<?> realEstatesObservableList=FXCollections.observableList(RealEstates);

            IDTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<RealEstate, Long>, SimpleLongProperty>) realEstatesLongCellDataFeatures -> new SimpleLongProperty(realEstatesLongCellDataFeatures.getValue().getId())
            );
            cityTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<RealEstate, Long>, SimpleStringProperty>) realEstatesLongCellDataFeatures -> new SimpleStringProperty(realEstatesLongCellDataFeatures.getValue().getCity())
            );
            streetTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<RealEstate, Long>, SimpleStringProperty>) realEstatesLongCellDataFeatures -> new SimpleStringProperty(realEstatesLongCellDataFeatures.getValue().getStreet())
            );
            numberTblCol.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<RealEstate, Long>, SimpleStringProperty>) realEstatesLongCellDataFeatures -> new SimpleStringProperty(realEstatesLongCellDataFeatures.getValue().getNumber())
            );

            fillRealEstates();

            searchTxt.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        try {
                            realEstatesTbl.setItems(filterList((List<RealEstate>) realEstatesObservableList, newValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillRealEstates(){
        try {
            List<?> realEstatesList = Table.list(RealEstate.class);
            ObservableList<?> realEstatesObservableList = FXCollections.observableList(realEstatesList);
            this.realEstatesTbl.setItems(realEstatesObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void selectRealEstate(MouseEvent evt){
        this.selectedRe = (RealEstate) this.realEstatesTbl.getSelectionModel().getSelectedItem();
        this.btnAdd.setText("Edit");
        this.cityTxt.setText(this.selectedRe.getCity());
        this.streetTxt.setText(this.selectedRe.getStreet());
        this.numberTxt.setText(this.selectedRe.getNumber());
    }

    @FXML
    protected void removeSelection(){
        this.selectedRe = null;
        this.fillRealEstates();
        this.btnAdd.setText("Add");
        this.cityTxt.setText("");
        this.streetTxt.setText("");
        this.numberTxt.setText("");

    }

    private boolean searchFindRealEstate(RealEstate realEstate, String searchText) throws Exception {
        return (realEstate.getCity().toLowerCase().contains(searchText.toLowerCase())) ||
                (realEstate.getStreet().toLowerCase().contains(searchText.toLowerCase())) ||
                Integer.valueOf(realEstate.getId()).toString().equals(searchText.toLowerCase())||
                (realEstate.getNumber().toLowerCase().contains(searchText.toLowerCase()));

    }

    private ObservableList<RealEstate> filterList(List<RealEstate> list, String searchText) throws Exception {
        List<RealEstate> filteredList = new ArrayList<>();
        for (RealEstate realEstate : list){
            if(searchFindRealEstate(realEstate, searchText))
                filteredList.add(realEstate);
        }
        return FXCollections.observableList(filteredList);
    }

}

