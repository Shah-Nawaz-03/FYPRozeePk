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

import com.finalyearproject.replicarozeepk.Jobs.PostedJobDetails;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.model.JobData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class PostedJobsAdapter extends Adapter<PostedJobsAdapter.ViewHolder> {

    private static final String TAG = "PostedJobsAdapter";
    public Context context;
    public List<JobData> jobData;

    public PostedJobsAdapter(Context context, List<JobData> jobData) {
        this.context = context;
        this.jobData = jobData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R
                .layout.job_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        JobData data = jobData.get(position);
        holder.JobTitle.setText(MessageFormat.format("Job Title: {0}",
                data.getJobtitle()));
        holder.Date.setText(MessageFormat.format("Date: {0}", data.getJobdate()));
        holder.Salary.setText(MessageFormat.format("Salary: {0}",data.getSalaryrange()));
        holder.Skills.setText(MessageFormat.format("Skills: {0}", data.getSkills()));
        holder.postedJobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostedJobDetails.class);

                JobIdSession aJid = new JobIdSession(context);
                aJid.createJidSession(data.id.substring(0, 2));

                intent.putExtra("lastdate",data.joblastdate);
                intent.putExtra("title", data.jobtitle);
                intent.putExtra("date",data.jobdate);
                intent.putExtra("skill",data.skills);
                intent.putExtra("salary",data.salaryrange);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView JobTitle;
            public TextView Date;
            public TextView Skills;
            public TextView Salary;
            public String id;
            public RelativeLayout postedJobLayout;

        public ViewHolder(@NonNull View itemView, Context context1) {
                super(itemView);
                context = context1;

                JobTitle = itemView.findViewById(R.id.jobtitle_cardview);
                Date = itemView.findViewById(R.id.date_cardview);
                Skills = itemView.findViewById(R.id.skills_cardview);
                Salary = itemView.findViewById(R.id.salary_cardview);
                postedJobLayout = itemView.findViewById(R.id.parent_layout);


            }
        }
    }