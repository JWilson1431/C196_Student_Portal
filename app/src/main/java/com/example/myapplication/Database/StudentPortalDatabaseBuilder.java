package com.example.myapplication.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.DAO.AssessmentDAO;
import com.example.myapplication.DAO.CourseDAO;
import com.example.myapplication.DAO.TermDAO;

import Entity.Assessment;
import Entity.Course;
import Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=5, exportSchema = false)
public abstract class StudentPortalDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile StudentPortalDatabaseBuilder INSTANCE;

    static StudentPortalDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (StudentPortalDatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentPortalDatabaseBuilder.class, "myStudentPortalDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

