package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Entity.Term;

public class AddCourseToTerm extends AppCompatActivity {
Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_term);
        repository= new Repository(getApplication());
        Spinner termSpinner=(Spinner)findViewById(R.id.termspinner);
        ArrayList<Term> terms=new ArrayList<>();
        terms.add(new Term(1,"term 1","1/1/1", "2/2/2"));
        terms.add(new Term(2,"term 2", "1/1/1", "2/2/2"));
        ArrayAdapter<Term> termAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,terms);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(termAdapter);
        termSpinner.setSelection(1);
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("spinner success");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("nothing selected");
            }
        });
    }
}