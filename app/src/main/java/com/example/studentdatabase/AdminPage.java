package com.example.studentdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.studentdatabase.R.id.btnShowInfo;
import static com.example.studentdatabase.RegisterPage.db;
import static com.example.studentdatabase.RegisterPage.emailPattern;
import static com.example.studentdatabase.RegisterPage.passwordpattern;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {
    EditText editRollno, editName, editEmail, editpass;
    Button btnAdd, btnDelete, btnModify, btnView, btnViewAll, btnShowInfo;
    Cursor cursor;
    String srolno, sname, semail, spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        editRollno = findViewById(R.id.edtadminrollno);
        editName = findViewById(R.id.edtadminName);
        editEmail = findViewById(R.id.edtadminemail);
        editpass = findViewById(R.id.edtadminpass);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnModify = findViewById(R.id.btnModify);
        btnView = findViewById(R.id.btnView);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnShowInfo = findViewById(R.id.btnShowInfo);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,email VARCHAR,password VARCHAR);");
        editRollno.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        editEmail.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        editName.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        editpass.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                srolno = editRollno.getText().toString().toUpperCase().trim();
                sname = editName.getText().toString().toUpperCase().trim();
                semail = editEmail.getText().toString().toLowerCase().trim();
                spass = editpass.getText().toString().trim();


                if (srolno.isEmpty()) {

                    editRollno.setError("Please enter your roll no");
                    return;

                } else if (sname.isEmpty()) {
                    editName.setError("Please Enter Your Name");
                    return;
                } else if (semail.isEmpty() || !semail.matches(emailPattern)) {

                    editEmail.setError("Please enter right email Address");
                    return;


                } else if (!spass.matches(passwordpattern)) {
                    Toast.makeText(AdminPage.this,"Password length is between 6 to 20",Toast.LENGTH_LONG).show();
                    editpass.setError("password should contain uppercase lowercase and special symbol");


                }else
                {
                    db.execSQL("INSERT INTO student VALUES('"+srolno+"','"+sname+"','"+semail+"','"+spass+ "');");
                    showMessage("Success", "Record added");
                    clearText();
                }
                    break;
                    case R.id.btnDelete:
                        if (editRollno.getText().toString().trim().length() == 0) {
                            editRollno.setError("Please Enter Rollno");

                            return;
                        }
                        cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                        if (cursor.moveToFirst()) {
                            db.execSQL("DELETE FROM student WHERE rollno='" + editRollno.getText() + "'");
                            showMessage("Success", "Record Deleted");
                        }
                        break;
                    case R.id.btnModify:

                        if (editRollno.getText().toString().trim().length() == 0) {
                            editRollno.setError("Please Enter Rollno");
                            return;
                        }else if (editName.getText().toString().length()==0) {
                            editName.setError("Please Enter Your Name");
                            return;
                        } else if (editEmail.getText().toString().length()==0 || !editEmail.getText().toString().matches(emailPattern)) {

                            editEmail.setError("Please enter right email Address");
                            return;


                        } else if (!editpass.getText().toString().matches(passwordpattern)) {
                            Toast.makeText(AdminPage.this,"Password length is between 6 to 20",Toast.LENGTH_LONG).show();
                            editpass.setError("password should contain uppercase lowercase and special symbol");


                        }else{

                        cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                        if (cursor.moveToFirst()) {
                            db.execSQL("UPDATE student SET name='" + editName.getText() + "',email='" + editEmail.getText() + "',password='" + editpass.getText() +
                                    "' WHERE rollno='" + editRollno.getText() + "'");
                            showMessage("Success", "Record Modified");
                        } }
                        break;
                    case R.id.btnView:
                        if (editRollno.getText().toString().trim().length() == 0) {
                            editRollno.setError("Please Enter Rollno");
                            return;
                        }
                        cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                        if (cursor.moveToFirst()) {
                            editName.setText(cursor.getString(1));
                            editEmail.setText(cursor.getString(2));
                            editpass.setText(cursor.getString(3));
                        } else {
                            showMessage("Error", "Invalid Rollno");
                            clearText();
                        }
                        break;
                    case R.id.btnViewAll:
//                        cursor = db.rawQuery("SELECT * FROM student ", null);
//                        if (cursor.getCount() == 0) {
//                            showMessage("Error", "No Records Found");
//                            return;
//                        } else {
//                            StringBuffer buffer = new StringBuffer();
//                            while (cursor.moveToNext()) {
//                                buffer.append("Rollno : " + cursor.getString(0) + "\n");
//                                buffer.append("Name   : " + cursor.getString(1) + "\n");
//                                buffer.append("Email  : " + cursor.getString(2) + "\n");
//                                buffer.append("Password  : " + cursor.getString(3) + "\n");
//                            }
//                            showMessage("All Recods !!", buffer.toString());
//                        }
                        Intent intent_viewall = new Intent(AdminPage.this,Listview_User.class);
                        startActivity(intent_viewall);
                        break;
                    case R.id.btnShowInfo:
                        showMessage("Student Record Application", "Developed By Sahil Mulla");
                        break;
                }

        }



    private void clearText() {
        editRollno.setText("");
            editName.setText("");
            editEmail.setText("");
            editpass.setText("");
            editRollno.requestFocus();
    }

    private void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setIcon(R.mipmap.my_icon_round);

            builder.setCancelable(true);
            builder.show();
    }
}