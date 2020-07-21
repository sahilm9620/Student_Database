package com.example.studentdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import static com.example.studentdatabase.LoginPage.cursor;
import static com.example.studentdatabase.LoginPage.email;
import static com.example.studentdatabase.RegisterPage.db;

public class Welcome extends AppCompatActivity {
TextView txv_personMail;
EditText txv_personName,txv_PersonRollno;
int isRecordDeleted=0;

    Button show,modify,deleterec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        txv_personName=findViewById(R.id.txvPersonName);
        txv_PersonRollno=findViewById(R.id.txvRollPerson);
        txv_personMail=findViewById(R.id.txvemaiPerson);
        txv_personMail.setText(email);

        modify=findViewById(R.id.modify);
        deleterec=findViewById(R.id.deletereco);
        txv_personName.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        txv_PersonRollno.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        txv_personMail.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});

//        show.setOnClickListener((View.OnClickListener) this);
//        modify.setOnClickListener((View.OnClickListener) this);
//        deleterec.setOnClickListener((View.OnClickListener) this);


        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");

        cursor = db.rawQuery("SELECT * FROM student WHERE email='" + email + "'", null);
        if (cursor.moveToFirst()) {
            txv_PersonRollno.setText(cursor.getString(0));
            txv_personName.setText(cursor.getString(1));



    }





    }

    public void modify_detail(View view) {
        cursor = db.rawQuery("SELECT * FROM student WHERE email='" + email + "'", null);
        if (cursor.moveToFirst()) {
            db.execSQL("UPDATE student SET rollno='" + txv_PersonRollno.getText() + "',name='" + txv_personName.getText() +
                    "' WHERE email='" + email + "'");
            showMessage("Success", "Record Modified");
        }

    }

    public void delete_rec(View view) {
        cursor = db.rawQuery("SELECT * FROM student WHERE email='" + email + "'", null);
        if (cursor.moveToFirst()) {
            db.execSQL("DELETE FROM student WHERE email='"+email+"'");
            isRecordDeleted=1;
            showMessage("Success", "Record Deleted");

        }
    }

    private void showMessage(String success, String o) {
        final AlertDialog.Builder builder_1 = new AlertDialog.Builder(this);
        builder_1.setTitle(success);
        builder_1.setMessage(o);
        builder_1.setIcon(R.mipmap.my_icon_round);
        builder_1.setCancelable(false);
        if(isRecordDeleted==1){

        builder_1.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder_1.setCancelable(true);
                finish();
            }
        });}
        if(isRecordDeleted==0){
        builder_1.setCancelable(false);
        builder_1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder_1.setCancelable(true);
            }
        });}
        builder_1.show();
        isRecordDeleted=0;
    }
}