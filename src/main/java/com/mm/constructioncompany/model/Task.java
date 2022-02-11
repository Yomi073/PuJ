package com.mm.constructioncompany.model;



import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;


public class Task extends Table{

    @Entity(type="INTEGER",size = 32, primary = true)
    int id;

    @Entity(type="INTEGER",size = 25)
    long pauseLength;

    @Entity(type = "DATE",isnull = false)
    Date date;

    @Entity(type = "TIME")
    Time startTime;

    @Entity(type = "TIME")
    Time endTime;


    @Entity(type="INTEGER",size=32)
    @ForeignKey(table ="User", attribute = "id")
    int User_FK;

    @Entity(type="INTEGER",size = 32)
    @ForeignKey(table = "RealEstate", attribute = "id")
    int RealEstate_FK;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPauseLength() {
        return pauseLength;
    }

    public void setPauseLength(long pauseLength) {
        this.pauseLength = pauseLength;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public User getUser()throws Exception { return (User) Table.get(User.class,User_FK); }

    public int getUser_FK() {
        return User_FK;
    }

    public void setUser_FK(int user_FK) {
        User_FK = user_FK;
    }

    public RealEstate getRealEstate()throws Exception { return (RealEstate) Table.get(RealEstate.class,RealEstate_FK); }

    public void setRealEstate_FK(int realEstate_FK) {
        RealEstate_FK = realEstate_FK;
    }


}
