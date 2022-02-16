package com.mm.constructioncompany.model;

public class MaterialStock extends Table{
    @Entity(type="INTEGER", size=32, primary = true)
    int id;

    @Entity(type="VARCHAR", size=50)
    String name;

    @Entity(type="DOUBLE",isnull = false)
    Double quantity;

    @Entity(type="DOUBLE", size=25)
    Double purchasePrice;

    @Entity(type="DOUBLE", size=25)
    Double sellingPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public String toString()
    {
        return this.name;
    }
}
