package com.mm.constructioncompany.model;

public class MaterialConsumption extends Table{
    @Entity(type = "INTEGER",size=32,primary = true)
    int id;

    @Entity(type = "DOUBLE",size = 25)
    Double quantity;

    @Entity(type = "INTEGER",size=32)
    @ForeignKey(table = "MaterialStock",attribute = "id")
    int materialStock_FK;

    @Entity(type = "INTEGER",size = 32)
    @ForeignKey(table = "Task",attribute = "id")
    int task_FK;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public Task getTask()throws Exception { return (Task) Table.get(Task.class,task_FK); }

    public void setTask_FK(int Task_FK)
    {
        this.task_FK=Task_FK;
    }

    public MaterialStock getMaterialStock()throws Exception { return (MaterialStock) Table.get(MaterialStock.class,materialStock_FK);  }

    public void setMaterialStock_FK(int materialStock_FK) {
        this.materialStock_FK = materialStock_FK;
    }

    public Double getQuantity() { return quantity; }

    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public int getMaterialStock_FK() {
        return materialStock_FK;
    }

    public int getTask_FK() {
        return task_FK;
    }
}
