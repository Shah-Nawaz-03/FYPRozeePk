package com.finalyearproject.replicarozeepk.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.Adapters.PostedJobsAdapter;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostedJobs extends AppCompatActivity {
    public RecyclerView postedjobsRV;
    private Toolbar toolbar;
    private List<JobData> jobData;
    private String CId;
    private Context context;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_jobs);
        context = this;
        toolbar = findViewById(R.id.toolbar_jobposted);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        SessionManagerSignup id = new SessionManagerSignup(context);
        HashMap<String, String> userid = id.getUsersData();

       String uid = userid.get(SessionManagerSignup.KEY_ID);
       CId = uid.substring(0,2);

        pd = new ProgressDialog(context);
        pd.setMessage("Processing...");
        pd.show();
        ApiClient.getClient().create(ApiInterface.class)
                .getJobs(CId).enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                try{
                    if(response.body().isEmpty()){
                        View v = LayoutInflater.from(context).inflate(R.layout.postyourjob,
                                null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                        dialog.setCancelable(false);

                        TextView ok = v.findViewById(R.id.ok_post);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    }
                        ArrayList<JobData> jobData1 = new ArrayList<>();
                        jobData1 = response.body();
                        Object obj = new Object();
                        for(int i = 0; i < jobData1.size(); i++){
                                obj = jobData1.get(i);

                            Gson g = new Gson();
                            String myJson = g.toJson(obj);
                            JobData user = g.fromJson(myJson,JobData.class);

                            JobData job = new JobData();
                            job.setId(user.id);
                            job.setJobtitle(user.jobtitle);
                            job.setJobdate(user.jobdate);
                            job.setJoblastdate(user.joblastdate);
                            job.setSkills(user.skills);
                            job.setSalaryrange(user.salaryrange);
                            jobData.add(job);
                        }

                       PostedJobsAdapter adaptar = new
                               PostedJobsAdapter(context, jobData);
                    adaptar.notifyDataSetChanged();
                        postedjobsRV.setAdapter(adaptar);
                        pd.dismiss();
                }catch (Exception ex){
                    pd.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
                    dialog.show();
                    Log.d("PostedJobs", "onResponse: " +ex
                            .getLocalizedMessage());
                }
            }
            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                pd.dismiss();
                View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                AlertDialog dialog = new AlertDialog.Builder(context).setView(v).create();
                dialog.show();
                Log.d("", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
    private void initView() {
        jobData =new ArrayList<>();
        postedjobsRV = (RecyclerView) findViewById(R.id.postedjobsRV);
        postedjobsRV.setHasFixedSize(true);
        postedjobsRV.hasFixedSize();
        postedjobsRV.setLayoutManager(new LinearLayoutManager(context));
    }
}