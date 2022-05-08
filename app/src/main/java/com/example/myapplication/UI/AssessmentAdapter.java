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

import Entity.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View assessmentView){
            super(assessmentView);
            assessmentItemView=assessmentView.findViewById(R.id.listofassessments);
            assessmentView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current=mAssessments.get(position);
                    Intent intent=new Intent(context,AddAssessment.class);
                    intent.putExtra("number",current.getAssessmentId());
                    intent.putExtra("title",current.getTitle());
                    intent.putExtra("startdateassessment",current.getStartDate());
                    intent.putExtra("assessmentenddate",current.getEndDate());
                    intent.putExtra("assessmentcourseid",current.getCourseId());
                    intent.putExtra("assessmenttype",current.getAssessmentType());
                    context.startActivity(intent);
                    }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View assessmentView=mInflater.inflate(R.layout.assessment_list_items,parent,false);
        return new AssessmentViewHolder(assessmentView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current=mAssessments.get(position);
            String title=current.getTitle();
            holder.assessmentItemView.setText(title);
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
        }
    }
    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount(){
        if(mAssessments!=null){
            return mAssessments.size();
        }
        else return 0;
    }
}
