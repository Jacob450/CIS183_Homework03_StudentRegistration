package com.example.cis183_homework03_studentregistration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Student> studentList;

    public StudentListAdapter(Context c, ArrayList<Student> sl){
        context =c;
        studentList = sl;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.custom_cell, null);
        }
        TextView name = view.findViewById(R.id.tv_v_cc_name);
        TextView userName = view.findViewById(R.id.tv_v_cc_username);
        TextView major = view.findViewById(R.id.tv_v_cc_major);
        TextView email = view.findViewById(R.id.tv_v_cc_email);
        TextView gpa = view.findViewById(R.id.tv_v_cc_gpa);

        Student stu = studentList.get(i);

        userName.setText(stu.getUsername());
        name.setText(stu.getFname() + " " + stu.getLname());
        major.setText(stu.major.getName());
        email.setText(stu.getEmail());
        gpa.setText(String.valueOf(stu.getGpa()));




        return view;
    }
}
