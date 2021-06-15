package com.s20.databasedemo_android.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    void insertEmployee(Employee employee);

    @Query("DELETE FROM employee")
    void deleteAllEmployees();

    @Query("DELETE FROM employee WHERE id = :id" )
    int deleteEmployee(int id);

    @Query("UPDATE employee SET fname = :fname, lname = :lname, phone = :phone, email = :email, address = :address WHERE id = :id")
    int updateEmployee(int id, String fname,String lname, String phone, String email, String address);

    @Query("SELECT * FROM employee ORDER BY fname")
    List<Employee> getAllEmployees();

}