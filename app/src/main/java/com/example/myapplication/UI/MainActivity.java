package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

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
        repo.insert(term);
        repo.insert(term2);
    }
}