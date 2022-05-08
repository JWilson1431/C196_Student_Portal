package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import Entity.Course;
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
    Term currentTerm;
    int numCourses;
    String myFormat="yyyy-MM-dd";
    SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);

    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar myCalendarStart= Calendar.getInstance();

    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarEnd=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);
        editName = findViewById(R.id.termname);
        editStart = findViewById(R.id.startdate);
        editEnd = findViewById(R.id.endDate);

        termNum = getIntent().getIntExtra("number", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start");
        endDate = getIntent().getStringExtra("end");

        editName.setText(name);
        editStart.setText(startDate);
        editEnd.setText(endDate);
        repository = new Repository(getApplication());

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info=editStart.getText().toString();
                if(info.equals("")) info = "2022-01-01";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddEditTerm.this, startDatePicker,myCalendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AddEditTerm.this,endDatePicker,myCalendarEnd.get(Calendar.YEAR),
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addtermmenu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save:
                Term term;
                if (termNum == -1) {
                    int newNum = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermNum() + 1;
                    term = new Term(newNum, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                } else {
                    term = new Term(termNum, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                }
                return true;
            case R.id.delete:
                for (Term term1 : repository.getAllTerms()) {
                    if (term1.getTermNum() == termNum) currentTerm = term1;
                }
                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermNum() == termNum) ++numCourses;
                }
                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(AddEditTerm.this, "Term was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddEditTerm.this, "Can't delete a term with courses", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return true;
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

}