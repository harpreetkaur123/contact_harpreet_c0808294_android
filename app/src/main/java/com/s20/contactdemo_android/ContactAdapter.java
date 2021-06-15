package com.s20.databasedemo_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.s20.databasedemo_android.room.Employee;
import com.s20.databasedemo_android.room.EmployeeRoomDb;
import com.s20.databasedemo_android.util.DatabaseHelper;

import java.util.List;

public class ContactAdapter extends ArrayAdapter {

    private static final String TAG = "EmployeeAdapter";

    Context context;
    int layoutRes;
    List<Employee> employeeList;
//    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper sqLiteDatabase;
    EmployeeRoomDb employeeRoomDb;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private TelephonyManager mTelephonyManager;

//    public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employeeList, DatabaseHelper sqLiteDatabase) {
//        super(context, resource, employeeList);
//        this.employeeList = employeeList;
//        this.sqLiteDatabase = sqLiteDatabase;
//        this.context = context;
//        this.layoutRes = resource;
//    }

    public ContactAdapter(@NonNull Context context, int resource, List<Employee> employeeList) {
        super(context, resource, employeeList);
        this.employeeList = employeeList;
        this.context = context;
        this.layoutRes = resource;
        employeeRoomDb = EmployeeRoomDb.getInstance(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutRes, null);
        TextView nameTV = v.findViewById(R.id.tv_name);
        TextView phoneTV = v.findViewById(R.id.tv_phone);
        TextView emailTV = v.findViewById(R.id.tv_email);
        TextView addressTV = v.findViewById(R.id.tv_address);

        final Employee employee = employeeList.get(position);
        nameTV.setText(employee.getFname() +" "+ employee.getLname());
        phoneTV.setText(employee.getPhone());
        emailTV.setText(employee.getEmail());
        addressTV.setText(employee.getAddress());



        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }

            private void updateEmployee(final Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_employee, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText etName = view.findViewById(R.id.et_ufname);
                final EditText etLname = view.findViewById(R.id.et_ulname);
                final EditText etPhone = view.findViewById(R.id.et_uphone);
                final EditText etEmail = view.findViewById(R.id.et_uemail);
                final EditText etAddress = view.findViewById(R.id.et_uaddress);


                //String[] deptArray = context.getResources().getStringArray(R.array.departments);
                //int position = Arrays.asList(deptArray).indexOf(employee.getDepartment());

                etName.setText(employee.getFname());
                etLname.setText(employee.getLname());
                etPhone.setText(employee.getPhone());
                etEmail.setText(employee.getEmail());
                etAddress.setText(employee.getAddress());

                //etSalary.setText(String.valueOf(employee.getSalary()));
                //spinnerDept.setSelection(position);

                view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fname = etName.getText().toString().trim();
                        String lname = etLname.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                        String email = etEmail.getText().toString().trim();
                        String address = etAddress.getText().toString().trim();
                        //String salary = etSalary.getText().toString().trim();
                        //String department = spinnerDept.getSelectedItem().toString();

                        if (fname.isEmpty()) {
                            etName.setError("name field cannot be empty");
                            etName.requestFocus();
                            return;
                        }

                        if (phone.isEmpty()) {
                            etPhone.setError("salary cannot be empty");
                            etPhone.requestFocus();
                            return;
                        }

                        /*String sql = "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new String[]{name, department, salary, String.valueOf(employee.getId())});*/

                        /*if (sqLiteDatabase.updateEmployee(employee.getId(), name, department, Double.parseDouble(salary)))
                            loadEmployees();*/

                        // Room
                        employeeRoomDb.employeeDao().updateEmployee(employee.getId(),fname, lname, phone,email,address);
                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }

            private void deleteEmployee(final Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String sql = "DELETE FROM employee WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});*/
                        /*if (sqLiteDatabase.deleteEmployee(employee.getId()))
                            loadEmployees();*/
                        // Room

                        employeeRoomDb.employeeDao().deleteEmployee(employee.getId());
                        loadEmployees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The employee (" + employee.getFname() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        v.findViewById(R.id.btn_edit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option(employee);
            }

            private void option(final Employee employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.option, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final TextView etName = view.findViewById(R.id.textView);
                final TextView etphone = view.findViewById(R.id.textView2);
                final TextView etemail = view.findViewById(R.id.textView3);

                etName.setText(employee.getFname() + " "+ employee.getLname());
                etphone.setText(employee.getPhone());
                etemail.setText(employee.getEmail());

                view.findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phone = etphone.getText().toString().trim();

                        // an intent to dial a number
                       MyActivity o = new MyActivity(context);
                       Log.d("before call","call");
                       o.call(phone);


                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });

                view.findViewById(R.id.btn_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phone = etphone.getText().toString().trim();



                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });

                view.findViewById(R.id.btn_email).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phone = etemail.getText().toString().trim();



                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });

            }
        });


        Log.d(TAG, "getView: " + getCount());
        return v;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    private void loadEmployees() {
        /*String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);*/

        /*Cursor cursor = sqLiteDatabase.getAllEmployees();
        employeeList.clear();
        if (cursor.moveToFirst()) {
            do {
                // create an employee instance
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }*/

        employeeList = employeeRoomDb.employeeDao().getAllEmployees();
        notifyDataSetChanged();
    }
}










