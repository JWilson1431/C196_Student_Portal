package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import Entity.Term;

public class AddEditTerm extends AppCompatActivity {
    EditText editName;
    EditText editStart;
    EditText editEnd;
    String name;
    String startDate;
    String endDate;
    int termNum;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);
        editName=findViewById(R.id.termname);
        editStart=findViewById(R.id.startdate);
        editEnd=findViewById(R.id.endDate);
        termNum=getIntent().getIntExtra("number",-1);
        name=getIntent().getStringExtra("name");
        startDate=getIntent().getStringExtra("start");
        endDate=getIntent().getStringExtra("end");
        editName.setText(name);
        editStart.setText(startDate);
        editEnd.setText(endDate);
        repository=new Repository(getApplication());
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

