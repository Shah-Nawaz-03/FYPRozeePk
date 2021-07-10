package com.finalyearproject.replicarozeepk.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.Adapters.RecyclerViewAdaptar;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.menu.Employermenu;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Jobs extends AppCompatActivity {
    private static final String TAG = "Jobs";
    Context context;
    public RecyclerView recyclerView;
    private List<JobData> jobDataList;
    private ExtendedFloatingActionButton addNewJob;
    String companyid;
    private ProgressDialog pdj;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        initView();
        toolbar = findViewById(R.id.toolbar_jobs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        companyid = getIntent().getStringExtra("compid");
        addNewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostaJob.class);
                intent.putExtra("Id",companyid);
                startActivity(intent);
            }
        });

        pdj.show();

        ApiClient.getClient().create(ApiInterface.class).getJobs(companyid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try {
                            ArrayList<JobData> jobData = new ArrayList<>();
                            jobData = response.body();

                            Object jobObject = new Object();

                            for (int i = 0; i < jobData.size(); i++) {
                                    jobObject = jobData.get(i);

                                Gson gson = new Gson();
                                String myJson = gson.toJson(jobObject);
                                JobData userData = gson.fromJson(myJson,
                                         JobData.class);

                                JobData job = new JobData();

                                job.setJobtitle(userData.jobtitle);
                                job.setJobdate(userData.jobdate);
                                job.setSkills(userData.skills);
                                job.setSalaryrange(userData.salaryrange);

                                jobDataList.add(job);

                            }
                            RecyclerViewAdaptar adapter = new
                                    RecyclerViewAdaptar(context, jobDataList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            pdj.dismiss();

                        } catch (Exception ex) {
                            pdj.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        pdj.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                        Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
                    }
                });

    }

    private void initView() {
        context = this;
        jobDataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        pdj = new ProgressDialog(context);
        pdj.setMessage("Processing...");
        addNewJob = (ExtendedFloatingActionButton) findViewById(R.id.addNewJob);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(context, Employermenu.class);
        intent.putExtra("Id", companyid);
        startActivity(intent);
        super.onBackPressed();
    }
}