package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Entity.Assessment;
import Entity.Course;

public class AddAssessment extends AppCompatActivity {
    int assessmentId;
    String assessmentTitle;
    String start;
    String end;
    int courseId;
    String assessmentType;
    Repository repository;
    Spinner courseSpinner;

    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    Assessment currentAssessment;

    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarEnd=Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar myCalendarStart= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.assessmenttitle);
        editStart = findViewById(R.id.assessmentstartdate);
        editEnd = findViewById(R.id.assessmentenddate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        assessmentId=getIntent().getIntExtra("number",-1);
        assessmentTitle=getIntent().getStringExtra("title");
        start=getIntent().getStringExtra("startdateassessment");
        end=getIntent().getStringExtra("assessmentenddate");
        courseId=getIntent().getIntExtra("assessmentcourseid",1);
        assessmentType=getIntent().getStringExtra("assessmenttype");

        //spinner for assessment types
        Spinner assessmentTypeSpinner=(Spinner) findViewById(R.id.assessmenttypespinner);
        ArrayAdapter<CharSequence> assessmentAdapter=ArrayAdapter.createFromResource(this,R.array.assessment_type, android.R.layout.simple_spinner_item);
        assessmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentTypeSpinner.setAdapter(assessmentAdapter);
        assessmentTypeSpinner.setSelection(0);

        Assessment currentAssessment= findAssessment(assessmentId);

        if(currentAssessment!=null) {
            setAssessmentSpinner(assessmentTypeSpinner, currentAssessment);
        }

        //assessmentTypeSpinner.setSelection(1);
        assessmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                assessmentType=assessmentTypeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spinner for courses
        courseSpinner=(Spinner) findViewById(R.id.spinnercourseid);
        List<Course> listOfCourses=repository.getAllCourses();
        ArrayList<Course> myCourses=new ArrayList<>();
        myCourses.addAll(listOfCourses);
        ArrayAdapter<Course> courseAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,myCourses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseSpinner.setAdapter(courseAdapter);




        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseId=myCourses.get(i).getCourseId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("nothing selected");
            }
        });


        editTitle.setText(assessmentTitle);
        editStart.setText(start);
        editEnd.setText(end);
        selectSpinnerItem(courseSpinner,courseId);




        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String info = editStart.getText().toString();
                if (info.equals("")) info = "2/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info=editEnd.getText().toString();
                if(info.equals("")) info="2/10/22";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this,endDatePicker,myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDatePicker= new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                myCalendarEnd.set(Calendar.YEAR,year);
                myCalendarEnd.set(Calendar.MONTH,month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH,day);
                updateEndLabel();
            }
        };
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
        getMenuInflater().inflate(R.menu.add_edit_assessment_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.shareassessment:
                Intent intent=new Intent(AddAssessment.this,AssessmentList.class);
                startActivity(intent);
                return true;
            case R.id.notifyassessment:
                String dateFromScreen=editStart.getText().toString();
                Date myDate=null;
                myDate= Date.valueOf(dateFromScreen);

                Long trigger=myDate.getTime();
                Intent intent1= new Intent(AddAssessment.this,MyReceiver.class);
                intent1.putExtra("key","Assessment : " +assessmentTitle + " starts " + editStart.getText().toString());
                PendingIntent sender=PendingIntent.getBroadcast(AddAssessment.this,MainActivity.numAlert++,intent1,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                return true;
            case R.id.notifyend:
                String dateFromScreen1=editEnd.getText().toString();
                Date myEndDate=null;
                myEndDate= Date.valueOf(dateFromScreen1);

                Long trigger1=myEndDate.getTime();
                Intent intent2=new Intent(AddAssessment.this,MyReceiver.class);
                intent2.putExtra("key", "Assessment : " + assessmentTitle + " ends " + editEnd.getText().toString());
                PendingIntent sender1=PendingIntent.getBroadcast(AddAssessment.this,MainActivity.numAlert++,intent2,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP,trigger1,sender1);
                return true;
            case R.id.saveassessment:
                Assessment assessment;
                if(assessmentId== -1){

                    int newNum=repository.getAllAssessments().get(repository.getAllAssessments().size()-1).getAssessmentId()+1;
                    assessment=new Assessment(newNum,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString()
                    ,courseId,assessmentType);
                    repository.insert(assessment);
                    Toast.makeText(AddAssessment.this,"Assessment was added", Toast.LENGTH_LONG).show();
                }
                else{
                    assessment= new Assessment(assessmentId,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(),
                            courseId,assessmentType);
                    repository.update(assessment);
                    Toast.makeText(AddAssessment.this,"Assessment was updated",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.deleteAssessment:
                for(Assessment assessment1: repository.getAllAssessments()) {
                    if (assessment1.getAssessmentId() == assessmentId) {
                        repository.delete(assessment1);
                        Toast.makeText(AddAssessment.this, "Assessment was deleted", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void selectSpinnerItem(Spinner spnr, long value) {
        ArrayAdapter adapter = (ArrayAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if (adapter.getItemId(position) == value) {
                spnr.setSelection(position - 1);
                return;
            }
        }
    }

    public Assessment findAssessment(int assessmentId){
        Assessment currentAssessment=null;
        List<Assessment> allAssessments = repository.getAllAssessments();
        for(Assessment assessment: allAssessments){
            if(assessmentId==assessment.getAssessmentId()){
                currentAssessment=assessment;
            }
        }
        return currentAssessment;
    }

    public void setAssessmentSpinner(Spinner spnr, Assessment assessment){
        if(assessment.getAssessmentType().equals("Objective")){
            spnr.setSelection(0);
        }
        if(assessment.getAssessmentType().equals("Performance")){
            spnr.setSelection(1);
        }
    }


}