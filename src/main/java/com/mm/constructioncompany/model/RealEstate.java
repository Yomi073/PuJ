package com.mm.constructioncompany.model;

public class RealEstate extends Table{

    @Entity(type="INTEGER", size=32, primary = true)
    int id;

    @Entity(type="VARCHAR", size=25)
    String city;

    @Entity(type="VARCHAR", size=25)
    String street;

    @Entity(type="VARCHAR", size=25)
    String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String toString() { return this.city; }
}
