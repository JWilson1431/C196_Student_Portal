package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import Entity.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;

        private CourseViewHolder(View courseView){
            super(courseView);
            courseItemView=courseView.findViewById(R.id.listofcourses);
            courseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current=mCourses.get(position);
                    Intent intent = new Intent(context, CreateEditCourse.class);
                    intent.putExtra("courseid",current.getCourseId());
                    intent.putExtra("title",current.getCourseTitle());
                    intent.putExtra("start",current.getStartDate());
                    intent.putExtra("end",current.getEndDate());
                    intent.putExtra("status",current.getStatus());
                    intent.putExtra("instructorname",current.getInstructorName());
                    intent.putExtra("instructorphone",current.getInstructorPhone());
                    intent.putExtra("instructoremail",current.getInstructorEmail());
                    intent.putExtra("notes",current.getNoteInfo());
                    intent.putExtra("termNum",current.getTermNum());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View courseView=mInflater.inflate(R.layout.course_list_items,parent,false);
        return new CourseViewHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position){
        if(mCourses!=null){
            Course current= mCourses.get(position);
            int courseId=current.getCourseId();
            String title=current.getCourseTitle();
            holder.courseItemView.setText(title);
        }
        else{
            holder.courseItemView.setText("No course title");
        }
    }
    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else return 0;
    }
}
