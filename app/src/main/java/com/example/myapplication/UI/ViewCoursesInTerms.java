package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Entity.Course;
import Entity.Term;

public class ViewCoursesInTerms extends AppCompatActivity {
    Repository repository;
    int courseTermId;
    int termId;
    private Spinner termSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcoursesinterms);
        repository = new Repository(getApplication());
        termSpinner = (Spinner) findViewById(R.id.termspinner);
        RecyclerView courseRecycleView=findViewById(R.id.courseintermrecycleview);
        final CourseAdapter courseAdapter=new CourseAdapter(this);
        courseRecycleView.setAdapter(courseAdapter);
        courseRecycleView.setLayoutManager(new LinearLayoutManager(this));



        List<Term> listOfTerms = repository.getAllTerms();
        ArrayList<Term> myTerms = new ArrayList<Term>();
        myTerms.addAll(listOfTerms);
        ArrayAdapter<Term> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, myTerms);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(termAdapter);
        termSpinner.setSelection(1);
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("spinner success");
                TextView termText = findViewById(R.id.termtext);
                int termNum=myTerms.get(i).getTermNum();

                List<Course> allCourses=repository.getAllCourses();
                ArrayList<Course> filteredCourses = new ArrayList<>();


                for(Course course: allCourses){
                    if(course.getTermNum()==termNum){
                        filteredCourses.add(course);
                    }
                }
                if(filteredCourses.size()==0){
                    courseAdapter.setCourses(null);
                    termText.setText("There are no courses associated with this term");
                }
                else {
                    courseAdapter.setCourses(filteredCourses);
                    termText.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("nothing selected");
            }
        });
    }
}




