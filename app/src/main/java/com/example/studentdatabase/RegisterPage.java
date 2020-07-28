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
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {
EditText edt_rollno,edt_name,edt_emailadd,edt_pas,edt_confpas;
Button btn_Register;
TextView txv_reg;

    static String s_name, s_rolno, s_email, s_pass, s_cnf_pass;
   static SQLiteDatabase db;

    int flag=0,f1=0;
   static String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]{2,}+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   static   String passwordpattern =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        edt_rollno=findViewById(R.id.edt_rollno);
        edt_name=findViewById(R.id.edt_name);
        edt_emailadd=findViewById(R.id.edt_email);
        edt_pas=findViewById(R.id.edt_pass);
        edt_confpas=findViewById(R.id.edt_confpass);
        btn_Register=findViewById(R.id.btn_register);
        txv_reg=findViewById(R.id.txv_register);
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,email VARCHAR,password VARCHAR);");
        edt_name.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_rollno.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_emailadd.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_pas.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        edt_confpas.setFilters(new InputFilter[]{new EmojiExcludeFilter()});


    }
    public void register_ac(View view) {


        s_rolno = edt_rollno.getText().toString().toUpperCase().trim();
        s_name = edt_name.getText().toString().toUpperCase().trim();
        s_email = edt_emailadd.getText().toString().toLowerCase().trim();
        s_pass = edt_pas.getText().toString().trim();
        s_cnf_pass = edt_confpas.getText().toString().trim();



        if(s_rolno.isEmpty())
        {

            edt_rollno.setError("Please enter your roll no");
            return;

        }else if(s_name.isEmpty())
        {
            edt_name.setError("Please Enter Your Name");
            return;
        }else  if(s_email.isEmpty() || !s_email.matches(emailPattern))
        {

            edt_emailadd.setError("Please enter right email Address");
            return;



        }
        else if (   s_pass.isEmpty()|| s_cnf_pass.isEmpty()|| !s_pass.equals(s_cnf_pass)  ) {
            Toast.makeText(RegisterPage.this,"Password Not matching",Toast.LENGTH_LONG).show();
            edt_pas.setError("Please enter right Password");
            return;

        } else if(!s_pass.matches(passwordpattern))
        {

            Toast.makeText(RegisterPage.this,"Password length is between 6 to 20",Toast.LENGTH_LONG).show();
            edt_pas.setError("password should contain uppercase lowercase and special symbol");

        }


          else   {

          flag=1;
        }

        if(flag==1)
        {
            db.execSQL("INSERT INTO student VALUES('"+s_rolno+"','"+s_name+"','"+s_email+"','"+s_pass+ "');");
            show_Message_Confirmation("Success","Registration Done");
            clearText();

        }



    }

//    private void show_Message(String title, String input) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(input);
//        builder.setIcon(R.mipmap.my_icon_round);
//        builder.setCancelable(false);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//            }
//        });
//
//        builder.show();
//    }

    private void show_Message_Confirmation(String success, String o) {
        final AlertDialog.Builder builder_1 = new AlertDialog.Builder(this);
        builder_1.setTitle(success);
        builder_1.setMessage(o);
        builder_1.setIcon(R.mipmap.my_icon_round);
            flag=0;
            builder_1.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent_my = new Intent(RegisterPage.this, LoginPage.class);
                    startActivity(intent_my);
                    builder_1.setCancelable(true);
                }
            });
            builder_1.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

                }
            });
            builder_1.show();


    }




    public void reg_to_login(View view) {
        Intent intent_log = new Intent(getApplicationContext(),LoginPage.class);
        startActivity(intent_log);
    }
    private void clearText() {
        edt_rollno.setText("");
        edt_name.setText("");
        edt_emailadd.setText("");
        edt_pas.setText("");
        edt_confpas.setText("");

    }
public static class EmojiExcludeFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            int type = Character.getType(source.charAt(i));
            if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                return "";
            } }

        return null;
    }
}
}