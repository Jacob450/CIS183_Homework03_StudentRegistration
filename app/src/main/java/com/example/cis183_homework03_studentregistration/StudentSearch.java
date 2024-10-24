package com.example.cis183_homework03_studentregistration;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentSearch extends AppCompatActivity {
    EditText et_j_ss_searchbar;
    DataBaseHelper db;
    ListView listView;
    Spinner sp_j_ss_searchTypes;
    ArrayList<Student> listOfStudents;
    SeekBar gpaBar;
    TextView gpa;
    int searchType;
    double progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_search);
        et_j_ss_searchbar = findViewById(R.id.et_v_ss_name);
        listView = findViewById(R.id.lv_ss_studentlist);
        sp_j_ss_searchTypes = findViewById(R.id.sp_v_ss_searchtype);
        gpaBar = findViewById(R.id.sb_v_ss_gpa);
        gpa = findViewById(R.id.tv_v_ss_gpa);

        db = new DataBaseHelper(this);
        listOfStudents = new ArrayList<Student>();


        Search();
        fillSpinner();
        spinnerEventListener();
        seekbar();





    }

    private void seekbar(){
        gpaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = (double) i/10;
                gpa.setText(String.valueOf(progress));

                if(searchType == 4){
                    listOfStudents = db.getAllGPALike(progress);
                    fillListView();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void Search(){
        et_j_ss_searchbar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(searchType == 0){
                    listOfStudents = db.getAllUsernameLike(et_j_ss_searchbar.getText().toString());
                }
                if(searchType == 1){
                    listOfStudents = db.getAllFNameLike(et_j_ss_searchbar.getText().toString());
                }
                if(searchType == 2){
                    listOfStudents = db.getAllLNameLike(et_j_ss_searchbar.getText().toString());
                }
                if(searchType == 3){
                    listOfStudents = db.getAllEmailLike(et_j_ss_searchbar.getText().toString());
                }
                if(searchType == 5){
                    listOfStudents = db.getAllMajorLike((et_j_ss_searchbar.getText().toString()));
                }

                fillListView();
                return false;
            }
        });
    }
    private void spinnerEventListener(){
        sp_j_ss_searchTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchType = i;
                if(i == 4){
                    gpaBar.setVisibility(View.VISIBLE);
                    gpa.setVisibility(View.VISIBLE);
                    et_j_ss_searchbar.setVisibility(View.INVISIBLE);
                }else{
                    gpaBar.setVisibility(View.INVISIBLE);
                    gpa.setVisibility(View.INVISIBLE);
                    et_j_ss_searchbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void fillSpinner(){
        ArrayList<String> searches = new ArrayList<>(Arrays.asList("UserName", "First name", "Last Name", "Email", "GPA", "Major"));
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,searches);
        sp_j_ss_searchTypes.setAdapter(adapter);

    }
    public void fillListView(){
        ListAdapter adapter = new StudentListAdapter(this, listOfStudents);
        listView.setAdapter(adapter);
    }
}