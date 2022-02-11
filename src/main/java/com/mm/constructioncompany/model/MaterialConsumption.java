package com.mm.constructioncompany.model;

public class MaterialConsumption extends Table{
    @Entity(type = "INTEGER",size=32,primary = true)
    int id;


    @Entity(type = "VARCHAR",size = 25)
    String length;

    @Entity(type = "VARCHAR",size = 25)
    String num_count;

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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getNum_count() { return num_count; }

    public void setNum_count(String num_count) {
        this.num_count = num_count; }

    public int getMaterialStock_FK() {
        return materialStock_FK;
    }

    public int getTask_FK() {
        return task_FK;
    }
}
