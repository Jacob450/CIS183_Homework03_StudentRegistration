package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper db;
    ArrayList<Student> listOfStudents;
    ListView listView;
    Button btn_j_addStudent;
    Button btn_j_addMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_v_studentlist);
        btn_j_addStudent = findViewById(R.id.btn_v_addstudent);
        btn_j_addMajor = findViewById(R.id.btn_v_addmajor);

         db = new DataBaseHelper(this);

         db.initTables();
         listOfStudents = db.getAllStudents();

         fillListView();
         studentClickListener();

         logAllMajors();
         addMajorButtonListener();


    }

    private void addMajorButtonListener(){
        btn_j_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddMajor.class));
            }
        });
    }

    private void logAllMajors(){
        for(Major m : db.getAllMajors()){
            Log.v("Major", m.getName());
        }
    }


    public void studentClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, StudentDetails.class);
                intent.putExtra("student", listOfStudents.get(i));
                startActivity(intent);
            }
        });

        btn_j_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddStudent.class));
            }
        });
    }

    public void fillListView(){
        ListAdapter adapter = new StudentListAdapter(this, db.getAllStudents());
        listView.setAdapter(adapter);
    }
}