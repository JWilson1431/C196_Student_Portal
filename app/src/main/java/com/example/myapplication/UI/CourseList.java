package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.util.List;

import Entity.Course;

public class CourseList extends AppCompatActivity {
Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.courserecyclerview);
        Repository repository=new Repository(getApplication());
        List<Course> courses=repository.getAllCourses();
        final CourseAdapter courseAdapter=new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(courses);
    }

    public void addAssessmentPage(View view) {
        Intent intent = new Intent(CourseList.this,AssessmentList.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courselist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshcourse:
                repository= new Repository(getApplication());
                List<Course> courses = repository.getAllCourses();
                RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
                final CourseAdapter courseAdapter=new CourseAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourses(courses);
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddACourse(View view) {
        Intent intent= new Intent(CourseList.this,CreateEditCourse.class);
        startActivity(intent);
    }
}