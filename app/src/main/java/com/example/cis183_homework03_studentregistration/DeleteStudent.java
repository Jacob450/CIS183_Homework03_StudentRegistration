package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeleteStudent extends AppCompatActivity {
    Student std;
    TextView tv_j_ds_name;
    Button btn_j_ds_delete;
    Button btn_j_ds_goBack;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_student);
        tv_j_ds_name = findViewById(R.id.tv_v_ds_studentname);
        btn_j_ds_goBack = findViewById(R.id.btn_v_ds_back);
        btn_j_ds_delete = findViewById(R.id.btn_v_ds_delete);
        db = new DataBaseHelper(this);

        setStudentToDelete();
        deleteButtonListener();

    }

    private void goBack(){
        btn_j_ds_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeleteStudent.this, MainActivity.class));
            }
        });
    }

    private void deleteButtonListener(){
        btn_j_ds_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteStudentFromDatabase(std);
                startActivity(new Intent(DeleteStudent.this, MainActivity.class));
            }
        });
    }

    private void setStudentToDelete(){
        Intent cameFrom =getIntent();
        Bundle info = cameFrom.getExtras();
        if (info != null){
            std = (Student) info.getSerializable("student");
            tv_j_ds_name.setText(std.getFname()+" "+std.getLname()+" ("+ std.getUsername()+")?");
        }

    }
}