package com.example.studentdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.studentdatabase.RegisterPage.db;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {
    EditText editRollno, editName, editEmail;
    Button btnAdd, btnDelete, btnModify, btnView, btnViewAll, btnShowInfo;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        editRollno = findViewById(R.id.editRollno);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editemail);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                if (editRollno.getText().toString().trim().length() == 0 ||
                        editName.getText().toString().trim().length() == 0 ||
                        editEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student(rollno,name,email) VALUES('" + editRollno.getText() + "','" + editName.getText() +
                        "','" + editEmail.getText() + "');");
                showMessage("Success", "Record added");
                clearText();
                break;
            case R.id.btnDelete:
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (cursor.moveToFirst()) {
                    db.execSQL("DELETE FROM student WHERE rollno='"+editRollno.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                break;
            case R.id.btnModify:
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (cursor.moveToFirst()) {
                    db.execSQL("UPDATE student SET name='" + editName.getText() + "',email='" + editEmail.getText() +
                            "' WHERE rollno='" + editRollno.getText() + "'");
                    showMessage("Success", "Record Modified");
                }
                break;
            case R.id.btnView:
                if (editRollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                cursor = db.rawQuery("SELECT * FROM student WHERE rollno='" + editRollno.getText() + "'", null);
                if (cursor.moveToFirst()) {
                    editName.setText(cursor.getString(1));
                    editEmail.setText(cursor.getString(2));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
                break;
            case R.id.btnViewAll:
                cursor = db.rawQuery("SELECT * FROM student ", null);
                if (cursor.getCount()==0)
                {
                    showMessage("Error","No Records Found");
                    return;
                }
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext())
                    {
                        buffer.append("Rollno : "+cursor.getString(0)+"\n");
                        buffer.append("Name   : "+cursor.getString(1)+"\n");
                        buffer.append("Email  : "+cursor.getString(2)+"\n");
                    }
                    showMessage("All Recods !!",buffer.toString());
                }
                break;
            case R.id.btnShowInfo:
                showMessage("Student Record Application", "Developed By Sahil Mulla");
                break;
        }

    }
    private void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.mipmap.my_icon_round);

        builder.setCancelable(true);
        builder.show();
    }
    private void clearText() {
        editRollno.setText("");
        editName.setText("");
        editEmail.setText("");
        editRollno.requestFocus();
    }

}