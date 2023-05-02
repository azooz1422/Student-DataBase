package com.example.project2;

import java.util.Date;

public class Student {
    private int ID;
    private String Name;
    private Date BH;
    private double GPA;

    public Student(int ID, String name, Date BH, double GPA) {
        this.ID = ID;
        Name = name;
        this.BH = BH;
        this.GPA = GPA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getBH() {
        return BH;
    }

    public void setBH(Date BH) {
        this.BH = BH;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
