package com.example.myapplication.Database;

import android.app.Application;

import com.example.myapplication.DAO.AssessmentDAO;
import com.example.myapplication.DAO.CourseDAO;
import com.example.myapplication.DAO.TermDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entity.Assessment;
import Entity.Course;
import Entity.Term;

public class Repository {
    private TermDAO mtermDAO;
    private CourseDAO mcourseDAO;
    private AssessmentDAO massessmentDAO;
    private List<Term> mallTerms;
    private List<Course> mallCourses;
    private List<Assessment> mallAssessments;


    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        StudentPortalDatabaseBuilder db = StudentPortalDatabaseBuilder.getDatabase(application);
        mtermDAO=db.termDAO();
        mcourseDAO=db.courseDAO();
        massessmentDAO=db.assessmentDAO();
    }
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mtermDAO.insert(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            mcourseDAO.insert(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            massessmentDAO.insert(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()-> {
            mallTerms = mtermDAO.getallTerms();
        });

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mallTerms;
    }

    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mallCourses=mcourseDAO.getallCourses();
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mallCourses;
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mallAssessments=massessmentDAO.getallAssessments();
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return mallAssessments;
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mtermDAO.update(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mcourseDAO.update(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            massessmentDAO.update(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mtermDAO.delete(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mcourseDAO.delete(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            massessmentDAO.delete(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
