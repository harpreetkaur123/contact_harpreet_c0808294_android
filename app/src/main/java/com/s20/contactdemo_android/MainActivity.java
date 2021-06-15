package com.s20.databasedemo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.s20.databasedemo_android.room.Employee;
import com.s20.databasedemo_android.room.EmployeeRoomDb;
import com.s20.databasedemo_android.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Database name
    /*public static final String DATABASE_NAME = "my_database";
    SQLiteDatabase sqLiteDatabase;*/

    // sqLite openHelper instance
    DatabaseHelper sqLiteDatabase;

    private EmployeeRoomDb employeeRoomDb;

    EditText etName, etLname,etPhone,etEmail,etAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_ufname);
        etLname = findViewById(R.id.et_lname);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_ulname);
        etAddress = findViewById(R.id.et_address);

        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_view_employees).setOnClickListener(this);

        // initialise our database
        /*sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createTable();*/

        // initializing the instance of sqLLite openHelper class
//        sqLiteDatabase = new DatabaseHelper(this);

        // Room db
        employeeRoomDb = EmployeeRoomDb.getInstance(this);
    }

    /*private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(20) NOT NULL, " +
                "department VARCHAR(20) NOT NULL, " +
                "joining_date DATETIME NOT NULL, " +
                "salary DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }*/

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_employee:
                addEmployee();
                break;
            case R.id.tv_view_employees:
                startActivity(new Intent(this, ContactActivity.class));
                break;
        }

    }

    private void addEmployee() {
        String fname = etName.getText().toString().trim();
        String lname = etLname.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // getting the current time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.CANADA);
        String joiningDate = sdf.format(cal.getTime());

        if (fname.isEmpty()) {
            etName.setError("name field cannot be empty");
            etName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Phone cannot be empty");
            etPhone.requestFocus();
            return;
        }

        /*String sql = "INSERT INTO employee (name, department, joining_date, salary)" +
                "VALUES (?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql, new String[]{name, department, joiningDate, salary});*/

        /*
        // insert employee into database table with the help of database openHelper class
        if (sqLiteDatabase.addEmployee(name, department, joiningDate, Double.valueOf(salary)))
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Employee NOT Added", Toast.LENGTH_SHORT).show();
*/
        // Insert into room db
        Employee employee = new Employee(fname,lname,phone,email,address);
        employeeRoomDb.employeeDao().insertEmployee(employee);
        Toast.makeText(this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etName.setText("");
        etLname.setText("");
        etPhone.setText("");
        etEmail.setText("");
        etAddress.setText("");
        etName.clearFocus();
        etPhone.clearFocus();
    }
}










