package com.example.studentdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CustomClass> {
    public CustomAdapter(@NonNull Context context, ArrayList<CustomClass> resource) {
        super(context, 0, resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);


        CustomClass item = getItem(position);


        TextView name = convertView.findViewById(R.id.textname);
        name.setText(item.getName());


        TextView rollno = convertView.findViewById(R.id.textrollno);
        rollno.setText("Roll no : " + item.getRollno());

        TextView email = convertView.findViewById(R.id.textemail);
        email.setText("Email : " + item.getEmail());

        TextView password = convertView.findViewById(R.id.textpassword);
        password.setText("Password : " + item.getPassword());

        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(item.getProfile_image());


        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.custom_listview_animation);
        convertView.startAnimation(animation);

        return convertView;
    }

}
