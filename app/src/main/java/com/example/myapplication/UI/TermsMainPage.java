package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    String myFormat="yyyy-MM-dd";
    SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);

    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar myCalendarStart=Calendar.getInstance();

    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarEnd=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_main_page);
        editName = findViewById(R.id.termname);
        editStart = findViewById(R.id.startdate);
        editEnd = findViewById(R.id.endDate);
        termNum = getIntent().getIntExtra("number", -1);
        repository = new Repository(getApplication());

        myFormat = "yyyy-MM-dd";
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStart.getText().toString();
                if (info.equals("")) info = "2022-09-09";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermsMainPage.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDatePicker= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarStart.set(Calendar.YEAR,year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info=editEnd.getText().toString();
                if(info.equals(""))info="2/12/22";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermsMainPage.this,endDatePicker,myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDatePicker=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarEnd.set(Calendar.YEAR,year);
                myCalendarEnd.set(Calendar.MONTH,month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH,day);
                updateEndLabel();
            }
        };
    }

    public void goToTermsList(View view) {
        Intent intent = new Intent(TermsMainPage.this,TermsList.class);
        startActivity(intent);
    }


    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat,Locale.US);
        editStart.setText(sdf.format(myCalendarStart.getTime()));

    }
    private void updateEndLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat,Locale.US);
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.terms_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveterm:
                Term term;
                if (termNum == -1) {
                    int newNum = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermNum() + 1;
                    term = new Term(newNum, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                    Toast.makeText(TermsMainPage.this, "Term was added", Toast.LENGTH_LONG).show();
                } else {
                    term = new Term(termNum, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                    Toast.makeText(TermsMainPage.this, "Term was updated", Toast.LENGTH_LONG).show();
                    return true;
                }
        }
                return super.onOptionsItemSelected(item);
        }
}