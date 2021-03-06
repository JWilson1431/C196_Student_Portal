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

import Entity.Term;

public class TermsList extends AppCompatActivity {
Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        Repository repository=new Repository(getApplication());
        List<Term> terms= repository.getAllTerms();
        final TermAdapter adapter=new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
    }

    public void goToAddCourse(View view) {
        Intent intent = new Intent(TermsList.this, ViewCoursesInTerms.class);
        startActivity(intent);
    }

    public void goToCourseList(View view) {
        Intent intent= new Intent(TermsList.this,CourseList.class);
        startActivity(intent);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.termlistmenu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refresh:
                repository=new Repository(getApplication());
                List<Term> allTerms=repository.getAllTerms();
                RecyclerView recyclerView=findViewById(R.id.recyclerview);
                final TermAdapter termAdapter=new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
        }
        return super.onOptionsItemSelected(item);
    }
}