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

import Entity.Assessment;

public class AssessmentList extends AppCompatActivity {
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.assessmentrecyclerview);
        Repository repository=new Repository(getApplication());
        List<Assessment> assessments=repository.getAllAssessments();
        final AssessmentAdapter adapter= new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void goToAddAssessment(View view) {
        Intent intent=new Intent(AssessmentList.this,AddAssessment.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessmentlist,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshassessmentlist:
                repository=new Repository(getApplication());
                List<Assessment> assessments=repository.getAllAssessments();
                RecyclerView recyclerView=findViewById(R.id.assessmentrecyclerview);
                final AssessmentAdapter assessmentAdapter=new AssessmentAdapter(this);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessments(assessments);
        }
        return super.onOptionsItemSelected(item);
    }
}