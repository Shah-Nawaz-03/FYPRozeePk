package com.finalyearproject.replicarozeepk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.finalyearproject.replicarozeepk.Jobs.MatchedJobsDetail;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.JobData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class JobSearchAdapter extends Adapter<JobSearchAdapter.ViewHolder> {
    public Context context;
    public List<JobData> jobData;

    public JobSearchAdapter(Context context, List<JobData> jobData) {
        this.context = context;
        this.jobData = jobData;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobsearchrow,
                parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        JobData jobData1 = jobData.get(position);
        holder.titejobsearch.setText(MessageFormat.format("Job Title: {0}",jobData1.getJobtitle()));
        holder.company.setText(MessageFormat.format("Company: {0}",jobData1.getCompany()));
        holder.cityjobsearch.setText(MessageFormat.format("City: {0}",jobData1.getCity()));
        holder.date.setText(MessageFormat.format("Date: {0}",jobData1.getJobdate()));
        holder.salary.setText(MessageFormat.format("Salary: {0}",jobData1.getSalaryrange()));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MatchedJobsDetail.class);
                i.putExtra("mid", jobData1.id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout parentLayout;
        public String  id;
        public TextView titejobsearch;
        public TextView company;
        public TextView cityjobsearch;
        public TextView date;
        public TextView salary;

        public ViewHolder(@NonNull @NotNull View itemView, Context context1) {
            super(itemView);
            context = context1;

            parentLayout = itemView.findViewById(R.id.parent_layoutjsr);
            titejobsearch =  itemView.findViewById(R.id.titejobsearch);
            company = itemView.findViewById(R.id.company);
            cityjobsearch = itemView.findViewById(R.id.cityjobsearch);
            date= itemView.findViewById(R.id.jobsearchdate);
            salary = itemView.findViewById(R.id.jobsearchsalary);
        }
    }
}
