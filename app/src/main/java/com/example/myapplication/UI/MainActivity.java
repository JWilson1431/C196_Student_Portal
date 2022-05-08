package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import Entity.Assessment;
import Entity.Course;
import Entity.Term;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterPortal(View view) {
        Intent intent = new Intent(MainActivity.this,TermsMainPage.class);
        startActivity(intent);
        Repository repo=new Repository(getApplication());
        Term term=new Term(1,"First term", "5/23/23", "6/23/23");
        Term term2=new Term(2,"Second term", "2/2/22", "3/3/33");
        Course course=new Course(1,"math","1/2/23","2,2,23","planned to take", "Beth Jones","610-555-5555","jjj@yahoo.com","note", 1);
        Course course2=new Course(2, "english", "2/3/23", "3/3/23","planned to take", "John Jones", "626-555-5555","John@gmail.com","note",2);
        Assessment assessment=new Assessment(1,"Assessment1","03/03/23","04/04/24",1, "Objective");
        Assessment assessment2=new Assessment(2,"Assessment2","02/14/22", "05/14/22",2,"Performance");
        repo.insert(term);
        repo.insert(term2);
        repo.insert(course);
        repo.insert(course2);
        repo.insert(assessment);
        repo.insert(assessment2);
    }
}