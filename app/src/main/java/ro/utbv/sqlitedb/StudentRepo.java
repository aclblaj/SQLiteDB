package ro.utbv.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;


public class StudentRepo {
    private final DBHelper dbHelper;

    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name, student.name);

        long student_Id = db.insert(Student.TABLE, null, values);
        db.close();
        return (int) student_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name, student.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_email + "," +
                Student.KEY_age +
                " FROM " + Student.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                int idIndex = cursor.getColumnIndex(Student.KEY_ID);
                int nameIndex = cursor.getColumnIndex(Student.KEY_name);
                int emailIndex = cursor.getColumnIndex(Student.KEY_email);

                if (idIndex != -1 && nameIndex != -1 && emailIndex != -1) {
                    student.put("id", cursor.getString(idIndex));
                    student.put("name", cursor.getString(nameIndex));
                    student.put("email", cursor.getString(emailIndex));
                    studentList.add(student);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_email + "," +
                Student.KEY_age +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(Student.KEY_ID);
                int nameIndex = cursor.getColumnIndex(Student.KEY_name);
                int emailIndex = cursor.getColumnIndex(Student.KEY_email);
                int ageIndex = cursor.getColumnIndex(Student.KEY_age);

                if (idIndex != -1 && nameIndex != -1 && emailIndex != -1 && ageIndex != -1) {
                    student.student_ID = cursor.getInt(idIndex);
                    student.name = cursor.getString(nameIndex);
                    student.email = cursor.getString(emailIndex);
                    student.age = cursor.getInt(ageIndex);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

}
