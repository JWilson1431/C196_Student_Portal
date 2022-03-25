package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import Entity.Term;

public class TermsMainPage extends AppCompatActivity {
    int termNum;
    String name;
    String start;
    String end;
    Repository repository;
    EditText editName;
    EditText editStart;
    EditText editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_main_page);
        editName=findViewById(R.id.termname);
        editStart=findViewById(R.id.startdate);
        editEnd=findViewById(R.id.endDate);
        termNum=getIntent().getIntExtra("number",-1);
        repository= new Repository(getApplication());
    }

    public void goToTermsList(View view) {
        Intent intent = new Intent(TermsMainPage.this,TermsList.class);
        startActivity(intent);
    }

    public void saveButton(View view) {
        Term term;
        if(termNum == -1){
            int newNum=repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermNum()+1;
            term = new Term(newNum,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
            repository.insert(term);
        }
        else{
            term = new Term(termNum,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
            repository.update(term);
        }
    }
}