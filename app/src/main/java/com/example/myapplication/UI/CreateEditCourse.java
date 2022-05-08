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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Entity.Course;
import Entity.Term;

public class CreateEditCourse extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editNote;
    Repository repository;
    int courseId;
    String courseTitle;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String note;
    int termNum;

    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar myCalendarStart= Calendar.getInstance();

    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarEnd=Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_course);
        editTitle=findViewById(R.id.coursetitle);
        editStart=findViewById(R.id.coursestartdate);
        editEnd=findViewById(R.id.courseenddate);
        editInstructorName=findViewById(R.id.instructorname);
        editInstructorPhone=findViewById(R.id.instructorphone);
        editInstructorEmail=findViewById(R.id.instructoremail);
        editNote=findViewById(R.id.notes);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        repository=new Repository(getApplication());

        courseId=getIntent().getIntExtra("courseid",-1);
        courseTitle=getIntent().getStringExtra("title");
        startDate=getIntent().getStringExtra("start");
        endDate=getIntent().getStringExtra("end");
        status=getIntent().getStringExtra("status");
        instructorName=getIntent().getStringExtra("instructorname");
        instructorPhone=getIntent().getStringExtra("instructorphone");
        instructorEmail=getIntent().getStringExtra("instructoremail");
        note=getIntent().getStringExtra("notes");
        termNum=getIntent().getIntExtra("termNum",1);

        editTitle.setText(courseTitle);
        editStart.setText(startDate);
        editEnd.setText(endDate);
        //editStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
        editNote.setText(note);


        //status spinner
        Spinner statusSpinner=(Spinner) findViewById(R.id.statusspinner);
        ArrayAdapter<CharSequence> statusAdapter=ArrayAdapter.createFromResource(this,R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(0);

        Course currentCourse=findCourse(courseId);
        if(currentCourse!=null) {
            setCourseStatusSpinner(statusSpinner, currentCourse);
        }
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status=statusSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //term spinner
        Spinner termSpinner=(Spinner) findViewById(R.id.termspinnerincourse);
        List<Term> allTerms=repository.getAllTerms();
        ArrayList<Term> myTerms=new ArrayList<>();
        myTerms.addAll(allTerms);
        ArrayAdapter<Term> termAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,myTerms);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(termAdapter);
        termSpinner.setSelection(1);
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                termNum=myTerms.get(i).getTermNum();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectTermSpinner(termSpinner,termNum);
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info=editStart.getText().toString();
                if(info.equals("")) info="2/12/22";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CreateEditCourse.this,startDatePicker,myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
                if(info.equals("")) info="2/12/22";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CreateEditCourse.this,endDatePicker,myCalendarEnd.get(Calendar.YEAR),
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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.addeditcoursemenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.sharecourse:
                Intent sendIntent =new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Note to share");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;

            case R.id.deletecourse:
                for(Course course:repository.getAllCourses()){
                    if(course.getCourseId()==courseId){
                        repository.delete(course);
                        Toast.makeText(CreateEditCourse.this,"Course was deleted",Toast.LENGTH_LONG).show();
                        return true;
                    }
                }

            case R.id.savecourse:
                Course course;
                if(courseId==-1){

                    int newNum=repository.getAllCourses().get(repository.getAllCourses().size()-1).getCourseId()+1;
                    course=new Course(newNum,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(),
                            status,editInstructorName.getText().toString(),editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString(),editNote.getText().toString(),termNum);
                    repository.insert(course);
                    Toast.makeText(CreateEditCourse.this,"Course was saved",Toast.LENGTH_LONG).show();
                }
                else{

                    course =new Course(courseId,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(),
                            status,editInstructorName.getText().toString(),editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString(),editNote.getText().toString(),termNum);
                    repository.update(course);
                    Toast.makeText(CreateEditCourse.this,"Course was updated",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.alertcoursestart:
                String dateFromScreen=editStart.getText().toString();
                Date myDate=null;
                myDate=Date.valueOf(dateFromScreen);

                Long trigger=myDate.getTime();
                Intent intent = new Intent(CreateEditCourse.this,MyReceiver.class);
                intent.putExtra("key", "Course " + courseId + " starts " + editStart.getText().toString());
                PendingIntent sender=PendingIntent.getBroadcast(CreateEditCourse.this,MainActivity.numAlert++,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                return true;
            case R.id.alertendcourse:
                String dateFromScreen1=editEnd.getText().toString();
                Date myDate1=null;
                myDate1=Date.valueOf(dateFromScreen1);

                Long trigger1=myDate1.getTime();
                Intent intent1=new Intent(CreateEditCourse.this,MyReceiver.class);
                intent1.putExtra("key", "Course " + courseId + " ends " + editEnd.getText().toString());
                PendingIntent sender1=PendingIntent.getBroadcast(CreateEditCourse.this,MainActivity.numAlert++,intent1,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP,trigger1,sender1);
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

    private static void selectTermSpinner(Spinner spnr,long value){
        ArrayAdapter adapter=(ArrayAdapter) spnr.getAdapter();
        for(int position=0; position<adapter.getCount();position++){
            if(adapter.getItemId(position)==value){
                spnr.setSelection(position-1);
                return;
            }
        }
    }

    public Course findCourse(int courseId){
        Course currentCourse=null;
        List<Course> allCourses=repository.getAllCourses();
        for(Course course:allCourses){
            if(courseId==course.getCourseId()){
                currentCourse=course;
            }
        }
        return currentCourse;
    }

    public void setCourseStatusSpinner(Spinner spnr, Course course){
        if(course.getStatus().equals("In progress")){
            spnr.setSelection(0);
        }
        if(course.getStatus().equals("Completed")){
            spnr.setSelection(1);
        }
        if(course.getStatus().equals("Dropped")){
            spnr.setSelection(2);
        }
        if(course.getStatus().equals("Planned to take")){
            spnr.setSelection(3);
        }
    }

}