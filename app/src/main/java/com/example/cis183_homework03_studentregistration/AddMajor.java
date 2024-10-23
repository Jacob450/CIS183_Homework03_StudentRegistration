package com.example.cis183_homework03_studentregistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class AddMajor extends AppCompatActivity {
    EditText et_j_am_majorName;
    EditText et_j_am_prefix;
    Button btn_j_am_add;
    TextView tv_j_am_error;
    Major newMajor;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_major);
        et_j_am_majorName = findViewById(R.id.et_v_am_majorname);
        et_j_am_prefix = findViewById(R.id.et_v_am_majorprefix);
        btn_j_am_add = findViewById(R.id.btn_v_am_add);
        tv_j_am_error = findViewById(R.id.tv_v_am_error);

        tv_j_am_error.setVisibility(View.INVISIBLE);
        db = new DataBaseHelper(this);



        newMajor = new Major();
        addNewMajor();


    }

    private void addNewMajor(){

        btn_j_am_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.doesMajorAlreadyExist(et_j_am_majorName.getText().toString())){
                    tv_j_am_error.setVisibility(View.VISIBLE);
                    tv_j_am_error.setText("Error Major with than name already exist");
                    et_j_am_majorName.setText("");
                }else if(et_j_am_majorName.getText().toString().isEmpty() || et_j_am_prefix.getText().toString().isEmpty()){
                    tv_j_am_error.setVisibility(View.VISIBLE);
                    tv_j_am_error.setText("Error you must fill out all fields");
                }else{
                    newMajor.setName(et_j_am_majorName.getText().toString());
                    newMajor.setPrefix(et_j_am_prefix.getText().toString());

                    db.addMajorToDatabase(newMajor);

                    startActivity(new Intent(AddMajor.this, MainActivity.class));
                }
            }
        });

    }


}