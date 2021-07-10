package com.finalyearproject.replicarozeepk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.JobData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdaptar extends Adapter<RecyclerViewAdaptar.ViewHolder> {

    public Context context;
    public List<JobData> jobDataList;
    public RecyclerViewAdaptar(Context context, List<JobData> jobDataList){
        this.context = context;
        this.jobDataList= jobDataList;
    }

    @NonNull
    @Override
    public RecyclerViewAdaptar.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_row, parent, false);



        return new ViewHolder(view, context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        JobData data = jobDataList.get(position);
        holder.JobTitle.setText(MessageFormat.format("Job Title: {0}",data.getJobtitle()));
        holder.Date.setText(MessageFormat.format("Date: {0}",data.getJobdate()));
        holder.Salary.setText(MessageFormat.format("Salary: {0}",data.getSalaryrange()));
        holder.Skills.setText(MessageFormat.format("Skills: {0}",data.getSkills()));

    }

    @Override
    public int getItemCount() {
        return jobDataList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView JobTitle;
        public TextView Date;
        public TextView Skills;
        public TextView Salary;


        public ViewHolder(@NonNull  View itemView, Context context1) {
            super(itemView);
            context = context1;

            JobTitle = itemView.findViewById(R.id.jobtitle_cardview);
            Date = itemView.findViewById(R.id.date_cardview);
            Skills = itemView.findViewById(R.id.skills_cardview);
            Salary = itemView.findViewById(R.id.salary_cardview);


        }
    }
}
