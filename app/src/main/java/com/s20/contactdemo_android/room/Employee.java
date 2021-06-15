package com.s20.databasedemo_android.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "fname")
    private String fname;

    @NonNull
    @ColumnInfo(name = "lname")
    private String lname;

    @NonNull
    @ColumnInfo(name = "phone")
    private String phone;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "address")
    private String address;




    public Employee(@NonNull String fname, @NonNull String lname, @NonNull String phone, @NonNull String email, String address) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
    @NonNull
    public String getFname() {
        return fname;
    }

    public void setFname(@NonNull String fname) {
        this.fname = fname;
    }

    @NonNull
    public String getLname() {
        return lname;
    }

    public void setLname(@NonNull String lname) {
        this.lname = lname;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

}
