package com.example.cis183_homework03_studentregistration;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "StudentReg.db";
    private static final String students_table_name = "Students";
    private static final String majors_table_name = "Majors";


    public DataBaseHelper(Context c)
    {
        super(c,database_name,null,2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + students_table_name + "(username varchar(50) primary key not null, fname varchar(50), lname varchar(50), email varchar(50), gpa double, majorid integer);");
        db.execSQL("CREATE TABLE " + majors_table_name + "(majorid integer primary key autoincrement not null, majorname varchar(50), prefix varchar(50));" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + students_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + majors_table_name + ";");

        onCreate(db);

    }

    public void initTables(){
        initStudents();
        initMajors();
    }

    private void initStudents(){
        SQLiteDatabase db = this.getWritableDatabase();

        if(countRecordsFromTable(students_table_name) == 0){
            db.execSQL("INSERT INTO " +students_table_name+"(username, fname, lname, email, gpa, majorid) VALUES ('zmoore', 'Zackary', 'Moore', 'zmoore@monroeccc.edu', '4', '1');");
            db.execSQL("INSERT INTO " +students_table_name+"(username, fname, lname, email, gpa, majorid) VALUES ('jperez4', 'Jacob', 'Perez', 'jperez4@monroeccc.edu', '3.2', '1');");
            db.execSQL("INSERT INTO " +students_table_name+"(username, fname, lname, email, gpa, majorid) VALUES ('sbell', 'Susan', 'Bell', 'sbell@monroeccc.edu', '3.5', '2');");
            db.execSQL("INSERT INTO " +students_table_name+"(username, fname, lname, email, gpa, majorid) VALUES ('dperez', 'Dylan', 'Perez', 'dmoney@monroeccc.edu', '1.8', '3');");
        }

        db.close();
    }

    private void initMajors(){
        SQLiteDatabase db = this.getWritableDatabase();

        if(countRecordsFromTable(majors_table_name) == 0){
            db.execSQL("INSERT INTO "+majors_table_name+"(majorname, prefix) VALUES ('Computer Science', 'CIS')");
            db.execSQL("INSERT INTO "+majors_table_name+"(majorname, prefix) VALUES ('Nursing', 'MED')");
            db.execSQL("INSERT INTO "+majors_table_name+"(majorname, prefix) VALUES ('Business', 'BUS')");
        }

        db.close();
    }

    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> list = new ArrayList<Student>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectStatement = "Select username From " + students_table_name + ";";
        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst()){
            do {
                list.add(getStudentGivenUsername(cursor.getString(0)));
            }while (cursor.moveToNext());
            return list;
        }else{
            Log.e("Error Student list null", "Cursor could not move to first");
            return null;
        }

    }

    public Student getStudentGivenUsername(String un){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectStatement = "SELECT fname, lname, email, gpa, majorid FROM " + students_table_name + " WHERE username = '"+un+"';";

        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst()){
            Student stu = new Student();
            stu.setUsername(un);
            stu.setFname(cursor.getString(0));
            stu.setLname(cursor.getString(1));
            stu.setEmail(cursor.getString(2));
            stu.setGpa(cursor.getDouble(3));
            stu.major = getMajorGivenID(cursor.getInt(4));


            return stu;

        }else{
            Log.e("Error: User Null","Could not find username given: " + un);
            return null;
        }

    }

    public ArrayList<Major> getAllMajors(){
        ArrayList<Major> majors = new ArrayList<Major>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectStatement = "Select majorid From " + majors_table_name + ";";
        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst()){
            do {
                majors.add(getMajorGivenID(cursor.getInt(0)));
            }while (cursor.moveToNext());

            return majors;
        }else{
            Log.e("Error: Major list null", "Cursor did not return tp first");
            return null;
        }
    }

    public Major getMajorGivenID(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectStatement = "SELECT majorname, prefix FROM " + majors_table_name + " WHERE majorid = '"+id+"';";

        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst()){
            Major major = new Major();
            major.setID(id);
            major.setName(cursor.getString(0));
            major.setPrefix(cursor.getString(1));
            return major;
        }else{
            Log.e("Error: Major Null","Could not find major given id: " + id);
            return null;
        }

    }


    public boolean isUsernameTaken(String un){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT username FROM " + students_table_name +" WHERE username = '"+un+"';";

        Cursor cursor = db.rawQuery(selectStatement, null);
        //If the cursor moves to first then there is a matching username
        //else it will return false
        return cursor.moveToFirst();
    }

    public boolean doesMajorAlreadyExist(String mn){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectStatement = "SELECT majorname FROM " + majors_table_name +" WHERE majorname = '"+mn+"';";

        Cursor cursor = db.rawQuery(selectStatement, null);

        //If the cursor moves to first then there is a matching username
        //else it will return false
        return cursor.moveToFirst();
    }

    public void updateStudent(Student s, String og){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + students_table_name + " SET username = '"+s.getUsername()+"', fname = '"+s.getFname()+"', lname = '"+s.getLname()+"', email = '"+s.getEmail()+"', gpa = '"+s.getGpa()+"', majorid = '"+s.major.getID()+"' WHERE username = '"+og+"'");
        db.close();
    }

    public void addStudentToDatabase(Student s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + students_table_name+ "(username, fname, lname, email, gpa, majorid) VALUES ('"+s.getUsername()+"', '"+s.getFname()+"', '"+s.getLname()+"', '"+s.getEmail()+"', '"+s.getGpa()+"', '"+s.major.getID()+"');");
        db.close();
    }

    public void deleteStudentFromDatabase(Student s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ students_table_name+" WHERE username = '"+s.getUsername()+"';");
        db.close();
    }

    public void addMajorToDatabase(Major m){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + majors_table_name + "(majorname, prefix) VALUES ('"+m.getName()+"', '"+m.getPrefix()+"');");
        db.close();
    }
    //Search functions
    public ArrayList<Student> getAllFNameLike(String fn){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> likeNames = new ArrayList<Student>();

        String selectStatement = "SELECT username FROM " + students_table_name +" WHERE fname LIKE '"+'%'+fn+'%'+"';";
        Cursor cursor = db.rawQuery(selectStatement, null);
        //If there is any data in the cursor then it will add it all to the list
        if(cursor.moveToFirst()){
            do {
                likeNames.add(getStudentGivenUsername(cursor.getString(0)));
            }while(cursor.moveToNext());

            return likeNames;
        }else{
            //If there is no cursor data it will return an empty list
            Log.e("Error", "Could not find any fName like" + fn);
            return likeNames;
        }



    }


    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);



        return numRows;
    }
}
