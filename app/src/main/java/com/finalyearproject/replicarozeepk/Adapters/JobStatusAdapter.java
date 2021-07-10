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

import com.finalyearproject.replicarozeepk.AppliedJobs.StatusDetails;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.JobData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class JobStatusAdapter extends Adapter<JobStatusAdapter.ViewHolder> {

   public Context context;
   public List<JobData> data ;
    public JobStatusAdapter(Context context, List<JobData> data){
        this.context = context;
        this.data = data;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusrow,parent
               ,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JobStatusAdapter.ViewHolder holder, int position) {

        JobData jobData = data.get(position);
        holder.id = jobData.getId();
        holder.cid = jobData.getCompanyid();
        holder.title_status.setText(MessageFormat.format("Job Title: {0}",jobData.getJobtitle()));
        holder.company_status.setText(MessageFormat.format("Company: {0}",jobData.getCompany()));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StatusDetails.class);
                intent.putExtra("apjId",jobData.id.substring(0,2));
                intent.putExtra("jobid",jobData.companyid.substring(0, 2));
                intent.putExtra("apjtitle", jobData.jobtitle);
                intent.putExtra("apjcompany",jobData.company);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout parentLayout;
        public String  id,cid;
        public TextView title_status;
        public TextView company_status;
        public TextView processing_status;

        public ViewHolder(@NonNull @NotNull View itemView, Context context1) {
            super(itemView);
            context = context1;

            parentLayout = itemView.findViewById(R.id.parent_layoutstatus);
            title_status = itemView.findViewById(R.id.title_status);
            company_status = itemView.findViewById(R.id.company_status);
            //processing_status = itemView.findViewById(R.id.processing_status);


        }
    }
}
