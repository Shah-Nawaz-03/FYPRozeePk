package com.finalyearproject.replicarozeepk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.AppliedJobs.SetStatus;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.UserData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

public class Uni extends RecyclerView.Adapter<Uni.ViewHolder> {

    public Context context;
    public List<UserData> data;
    public Uni(Context context, List<UserData> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public Uni.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_applicants,
                parent,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        UserData d = data.get(position);
        holder.aname.setText(d.getName());
        holder.aedu.setText(d.getEducation());
        holder.aexp.setText(d.getExperience());
        holder.asalary.setText(d.getExpectedsalary());
        holder.acareerl.setText(d.getCareerlevel());
        holder.cgpa.setText(MessageFormat.format("Cgpa: {0}",d.getCgpa()));
        holder.uni.setText(d.getUni());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SetStatus.class);
                intent.putExtra("applicantsId", d.getCompanyid());
                intent.putExtra("applicantsName",d.getName());
                intent.putExtra("applicantsExp",d.getExperience());
                intent.putExtra("applicantsExpectedSalary",d.getExpectedsalary());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
       super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout;
        public TextView aname,aedu,aexp,asalary,acareerl,uni,cgpa;
        public ViewHolder(@NonNull @NotNull View itemView, Context context1) {
            super(itemView);
            context = context1;


            parentLayout = itemView.findViewById(R.id.applicant_parent);
            aname = itemView.findViewById(R.id.aname);
            aedu = itemView.findViewById(R.id.aedu);
            aexp = itemView.findViewById(R.id.aexp);
            asalary = itemView.findViewById(R.id.asalary);
            acareerl = itemView.findViewById(R.id.acareerl);
            uni = itemView.findViewById(R.id.uni);
            cgpa = itemView.findViewById(R.id.cgpa);
        }
    }

}

