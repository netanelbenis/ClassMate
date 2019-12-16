package com.example.classmate;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Algorithm_adapter extends ArrayAdapter<Student> {
    public Algorithm_adapter(@NonNull Context context, @NonNull List<Student> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview_row,parent,false);
        }
        TextView fullName = convertView.findViewById(R.id.full_name);
        TextView email = convertView.findViewById(R.id.email);
        TextView phone = convertView.findViewById(R.id.phone);
        TextView skill= convertView.findViewById(R.id.skills);
        Student student = getItem(position);

        fullName.setText(student.getFullName());
        email.setText(student.getEmail());
        phone.setText(student.getPhone());
        skill.setText(student.getSkills());

        return convertView;
    }
}
