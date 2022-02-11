package com.mm.constructioncompany.model;

public class ClientRealEstate extends Table{
    @Entity(type = "INTEGER",size = 32,primary = true)
    int id;

    @Entity(type = "INTEGER",size = 32)
    @ForeignKey(table = "Client",attribute = "id")
    int client_FK;

    @Entity(type = "INTEGER",size = 32)
    @ForeignKey(table = "RealEstate",attribute = "id")
    int realEstate_FK;

    public int getId(){
        return id;
    }

    public void setId(int id) { this.id=id; }

    public Client getClient()throws Exception
    {
        return (Client) Table.get(Client.class,client_FK);
    }
    public void setClient_FK(int Client_FK)
    {
        this.client_FK=Client_FK;
    }

    public RealEstate getRealEstate()throws Exception
    {
        return (RealEstate) Table.get(RealEstate.class,realEstate_FK);
    }

    public void setRealEstate_FK(int RealEstate_FK)
    {
        this.realEstate_FK=RealEstate_FK;
    }

}
