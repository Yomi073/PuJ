package com.mm.constructioncompany.model;

public class MaterialStock extends Table{
    @Entity(type="INTEGER", size=32, primary = true)
    int id;

    @Entity(type="VARCHAR", size=25)
    String name;

    @Entity(type="DOUBLE",isnull = false)
    Double quantity;

    @Entity(type="VARCHAR", size=25)
    String purchasePrice;

    @Entity(type="VARCHAR", size=25)
    String sellingPrice;


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

    public double getQuantity() { return quantity; }

    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getPurchasePrice() { return purchasePrice; }

    public void setPurchasePrice(String purchasePrice) { this.purchasePrice = purchasePrice; }

    public String getSellingPrice() { return sellingPrice; }

    public void setSellingPrice(String sellingPrice) { this.sellingPrice = sellingPrice; }

}
