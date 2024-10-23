package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentUpdate extends AppCompatActivity {

    EditText et_j_su_username;
    EditText et_j_su_fName;
    EditText et_j_su_lName;
    EditText et_j_su_email;
    EditText et_j_su_gpa;
    Spinner sp_j_majorSpinner;
    Button btn_j_su_update;
    Button btn_j_su_back;
    TextView tv_v_su_error;
    Student stu;
    DataBaseHelper db;
    String originalUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_update);
        db = new DataBaseHelper(this);

        et_j_su_username = findViewById(R.id.et_v_su_username);
        et_j_su_fName = findViewById(R.id.et_v_su_fname);
        et_j_su_lName = findViewById(R.id.et_v_su_lname);
        et_j_su_email = findViewById(R.id.et_v_su_email);
        et_j_su_gpa = findViewById(R.id.et_v_su_gpa);
        sp_j_majorSpinner = findViewById(R.id.sp_v__su_majorspinner);
        btn_j_su_update = findViewById(R.id.btn_v_su_update);
        btn_j_su_back = findViewById(R.id.btn_v_su_goback);
        tv_v_su_error = findViewById(R.id.tv_v_su_error);
        tv_v_su_error.setVisibility(View.INVISIBLE);


        setStudent();

        //used when updating database
        originalUsername = stu.getUsername();

        fillMajorSpinner();
        setEditTexts();
        setSpinnerListener();
        updateStudent();
        goBack();

    }

    public void goBack() {
        btn_j_su_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentUpdate.this, MainActivity.class));
            }
        });
    }

    private void updateStudent(){
        tv_v_su_error.setVisibility(View.INVISIBLE);
        btn_j_su_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if any of the fields are empty
                if(et_j_su_username.getText().toString().isEmpty() || et_j_su_fName.getText().toString().isEmpty() || et_j_su_lName.getText().toString().isEmpty() || et_j_su_email.getText().toString().isEmpty() || et_j_su_gpa.getText().toString().isEmpty() ){
                    tv_v_su_error.setText("You Must Fill out all fields");
                    tv_v_su_error.setVisibility(View.VISIBLE);
                    //if username is taken or is unchanged
                }else if(db.isUsernameTaken(et_j_su_username.getText().toString()) && !et_j_su_username.getText().toString().equals(stu.getUsername())){
                    tv_v_su_error.setText("That Username is Taken");
                    tv_v_su_error.setVisibility(View.VISIBLE);
                    et_j_su_username.setText(stu.getUsername());
                }else {//If everything is good update student info

                    stu.setUsername(et_j_su_username.getText().toString());
                    stu.setFname(et_j_su_fName.getText().toString());
                    stu.setLname(et_j_su_lName.getText().toString());
                    stu.setEmail(et_j_su_email.getText().toString());
                    stu.setGpa(Double.valueOf(et_j_su_gpa.getText().toString()));

                    db.updateStudent(stu, originalUsername);
                    startActivity(new Intent(StudentUpdate.this, MainActivity.class));

                }

            }
        });
    }

    private void setSpinnerListener(){
        sp_j_majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,majorNames);
        sp_j_majorSpinner.setAdapter(adapter);

    }


    private void setEditTexts(){
        et_j_su_username.setText(stu.getUsername());
        et_j_su_fName.setText(stu.getFname());
        et_j_su_lName.setText(stu.getLname());
        et_j_su_email.setText(stu.getEmail());
        et_j_su_gpa.setText(String.valueOf(stu.getGpa()));
        sp_j_majorSpinner.setSelection(stu.major.getID()-1);

    }


    private void setStudent(){
        Intent cameFrom = getIntent();
        Bundle infoPassed = cameFrom.getExtras();
        if(infoPassed != null){
            stu = (Student) infoPassed.getSerializable("student");
        }
    }
}