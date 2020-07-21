package com.example.studentdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.studentdatabase.RegisterPage.db;

public class LoginPage extends AppCompatActivity {
TextView txv_register;
EditText edt_username,edt_password;
Button btn_login;
String admin_email="admin@gmail.com",admin_password="admin";

    static String email,password;
    static Cursor cursor,cursor1;
    int flag=0,flag_admin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        txv_register=findViewById(R.id.txv_register);
        edt_username=findViewById(R.id.edt_username);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,email VARCHAR,password VARCHAR);");
        edt_username.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});
        edt_password.setFilters(new InputFilter[]{new RegisterPage.EmojiExcludeFilter()});


    }



    public void registeruser(View view) {
        Intent intent_reg = new Intent(getApplicationContext(),RegisterPage.class);
        startActivity(intent_reg);
    }

    public void login_ac(View view) {

        email = edt_username.getText().toString().toLowerCase().trim();
        password = edt_password.getText().toString().trim();
        if(email.isEmpty() )
        {
            edt_username.setError("Please Enter Your Email Address");
        }else if(password.isEmpty())
        {
            edt_password.setError("Please Enter Your Password");
        }

        cursor = db.rawQuery("SELECT * FROM student WHERE email='" + email + "' AND password='" + password + "'" , null);
        cursor1 = db.rawQuery("SELECT * FROM student WHERE email='" + email + "' " , null);

            if(email.equals(admin_email) && password.equals(admin_password))
            {
                Intent intent_adm = new Intent(getApplicationContext(),AdminPage.class);
                startActivity(intent_adm);

                clearText();

            }else


        if (cursor.moveToFirst()) {

//                showMessage("success","Login successfully");
                flag=1;

        }
        else if(cursor1.moveToFirst())
        {
            showMessage("Error", "Wrong Credentials ");
            return;

        }else
         {
             showMessage("Error", "Not Registerd User ");
             return;
         }



        if(flag==1)
        {
            Intent intent_wel = new Intent(getApplicationContext(),Welcome.class);
            startActivity(intent_wel);
            flag=0;
            clearText();
        }
    }

    private void showMessage(String title, String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.mipmap.my_icon_round);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                builder.setCancelable(true);

            }
        });
        builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void clearText() {
        edt_username.setText("");
        edt_password.setText("");
    }


}