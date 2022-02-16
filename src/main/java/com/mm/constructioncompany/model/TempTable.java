package com.mm.constructioncompany.model;

public class TempTable extends Table{

    private int id;
    private String name;
    private Double quantity;
    private Double sellingPrice;
    private Double sum;

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

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void
    setSellingPrice(Double sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum() {
        this.sum = this.sellingPrice*this.quantity;
    }



}