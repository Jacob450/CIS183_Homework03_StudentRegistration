package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Struct;
import java.util.ArrayList;

public class AddStudent extends AppCompatActivity {
    EditText et_j_as_username;
    EditText et_j_as_fName;
    EditText et_j_as_lName;
    EditText et_j_as_email;
    EditText et_j_as_gpa;
    TextView tv_v_as_error;
    Button btn_j_as_register;
    Button btn_j_as_goBack;
    Spinner sp_j_majors;
    DataBaseHelper db;
    ArrayAdapter adapter;
    Student stu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        et_j_as_username = findViewById(R.id.et_v_as_username);
        et_j_as_fName = findViewById(R.id.et_v_as_fname);
        et_j_as_lName = findViewById(R.id.et_v_as_lname);
        et_j_as_email = findViewById(R.id.et_v_as_email);
        et_j_as_gpa = findViewById(R.id.et_v_as_gpa);
        sp_j_majors = findViewById(R.id.sp_v_as_majorspinner);
        btn_j_as_goBack = findViewById(R.id.btn_v_as_goback);
        btn_j_as_register = findViewById(R.id.btn_v_as_register);
        tv_v_as_error = findViewById(R.id.tv_v_as_error);

        stu = new Student();

        tv_v_as_error.setVisibility(View.INVISIBLE);
        db = new DataBaseHelper(this);
        fillMajorSpinner();
        register();
        spinnerEventListner();
        goBack();

    }

    private void goBack(){
        btn_j_as_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddStudent.this, MainActivity.class));
            }
        });
    }

    private void register(){
        btn_j_as_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if any of the fields are empty
                if(et_j_as_username.getText().toString().isEmpty() || et_j_as_fName.getText().toString().isEmpty() || et_j_as_lName.getText().toString().isEmpty() || et_j_as_email.getText().toString().isEmpty() || et_j_as_gpa.getText().toString().isEmpty() ){
                    tv_v_as_error.setText("You Must Fill out all fields");
                    tv_v_as_error.setVisibility(View.VISIBLE);
                //if username is taken
                }else if(db.isUsernameTaken(et_j_as_username.getText().toString())){
                    tv_v_as_error.setText("That Username is Taken");
                    tv_v_as_error.setVisibility(View.VISIBLE);
                    et_j_as_username.setText("");
                }else{//If everything is good create new student and add to database
                    tv_v_as_error.setVisibility(View.INVISIBLE);


                    stu.setUsername(et_j_as_username.getText().toString());
                    stu.setFname(et_j_as_fName.getText().toString());
                    stu.setLname(et_j_as_lName.getText().toString());
                    stu.setEmail(et_j_as_email.getText().toString());
                    stu.setGpa(Double.valueOf(et_j_as_gpa.getText().toString()));

                    db.addStudentToDatabase(stu);
                    startActivity(new Intent(AddStudent.this, MainActivity.class));

                }

            }
        });
    }

    private void spinnerEventListner(){
        sp_j_majors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.e("From Spinner", db.getAllMajors().get(i).getName());
                stu.major = db.getAllMajors().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillMajorSpinner(){
        ArrayList<String> majorNames = new ArrayList<String>();

        for(Major m : db.getAllMajors()){
            majorNames.add(m.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,majorNames);
        sp_j_majors.setAdapter(adapter);
    }
}
