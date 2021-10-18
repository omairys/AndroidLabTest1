package com.omug.androidlabtest1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class Person implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "personId")
    private long id;
    @ColumnInfo(name = "personName")
    private String name;
    @ColumnInfo(name = "personAge")
    private int age;
    @ColumnInfo(name = "personTuition")
    private double tuition;
    @ColumnInfo(name = "personStartDate")
    private Date startDate;

    public Person(String name, int age, double tuition, Date startDate) {
        this.name = name;
        this.age = age;
        this.tuition = tuition;
        this.startDate = startDate;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public double getTuition() { return tuition; }

    public void setTuition(double tuition) { this.tuition = tuition; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }


}
