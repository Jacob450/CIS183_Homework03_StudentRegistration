package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetails extends AppCompatActivity {
    TextView tv_j_sd_userName;
    TextView tv_j_sd_fName;
    TextView tv_j_sd_lname;
    TextView tv_j_sd_email;
    TextView tv_j_sd_gpa;
    TextView tv_j_sd_major;
    Button btn_j_sd_goBack;
    Button btn_j_sd_studentUpdate;
    Student stu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_details);

        tv_j_sd_userName = findViewById(R.id.tv_v_sd_username);
        tv_j_sd_fName = findViewById(R.id.tv_v_sd_fname);
        tv_j_sd_lname = findViewById(R.id.tv_v_sd_lname);
        tv_j_sd_email = findViewById(R.id.tv_v_sd_email);
        tv_j_sd_gpa = findViewById(R.id.tv_v_sd_gpa);
        tv_j_sd_major = findViewById(R.id.tv_v_sd_major);
        btn_j_sd_goBack = findViewById(R.id.btn_v_sd_goback);
        btn_j_sd_studentUpdate = findViewById(R.id.btn_v_sd_update);


        setStudent();

        tv_j_sd_userName.setText(stu.getUsername());
        tv_j_sd_fName.setText(stu.getFname());
        tv_j_sd_lname.setText(stu.getLname());
        tv_j_sd_email.setText(stu.getEmail());
        tv_j_sd_gpa.setText(String.valueOf(stu.getGpa()));
        tv_j_sd_major.setText(stu.major.getName() + " (" + stu.major.getPrefix() + ")");

        sendToStudentUpdate();
        goBack();


    }

    public void sendToStudentUpdate(){
        btn_j_sd_studentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentUpdate = new Intent(StudentDetails.this, StudentUpdate.class);

                studentUpdate.putExtra("student", stu);
                startActivity(studentUpdate);
            }
        });
    }

    public void goBack(){
        btn_j_sd_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDetails.this, MainActivity.class));
            }
        });
    }



    public void setStudent(){
        Intent camefrom = getIntent();
        Bundle infopassed = camefrom.getExtras();

        if(infopassed != null){
            stu = (Student) infopassed.getSerializable("student");
        }
    }
}