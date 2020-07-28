package com.example.studentdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.studentdatabase.RegisterPage.db;

public class Listview_User extends AppCompatActivity {
int Profile_image[]={R.drawable.user_image1,R.drawable.user_image2,R.drawable.user_image3,R.drawable.user_image4,R.drawable.user_image5,R.drawable.user_image6,R.drawable.user_image7};
    Cursor cursor;
    EditText thefilter;
    private  CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview__user);
        thefilter=findViewById(R.id.edit_search);
        final ArrayList<CustomClass> items = new ArrayList<>();

        Random random= new Random();
        int random_number=random.nextInt(4);
        cursor = db.rawQuery("SELECT * FROM student ", null);
                        if (cursor.getCount() == 0) {
//                            showMessage("Error", "No Records Found");
                            return;
                        } else {

                            while (cursor.moveToNext()) {
                                random_number=random.nextInt(7);
                                items.add(new CustomClass(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),Profile_image[random_number]));
                                random_number=random.nextInt(7);
                            }
//
                        }

                 customAdapter = new CustomAdapter(Listview_User.this,items);
        ListView listView = findViewById(R.id.list_view);

        listView.setDivider(null);
        listView.setDividerHeight(20);
        listView.setAdapter(customAdapter);

        thefilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}