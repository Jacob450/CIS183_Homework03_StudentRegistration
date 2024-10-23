package com.example.cis183_homework03_studentregistration;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentSearch extends AppCompatActivity {
    EditText et_j_ss_fName;
    DataBaseHelper db;
    ListView listView;
    ArrayList<Student> listOfStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_search);
        et_j_ss_fName = findViewById(R.id.et_v_ss_name);
        listView = findViewById(R.id.lv_ss_studentlist);
        db = new DataBaseHelper(this);

        fNameSearch();






    }

    private void fNameSearch(){
        et_j_ss_fName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                listOfStudents = db.getAllFNameLike(et_j_ss_fName.getText().toString());
                fillListView();

                return false;
            }
        });
    }
    public void fillListView(){
        ListAdapter adapter = new StudentListAdapter(this, listOfStudents);
        listView.setAdapter(adapter);
    }
}