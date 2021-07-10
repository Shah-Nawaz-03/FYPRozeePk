package com.finalyearproject.replicarozeepk.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.Adapters.JobSearchAdapter;
import com.finalyearproject.replicarozeepk.Adapters.MatchedJobsAdapter;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobSearchResult extends AppCompatActivity {

    private static final String TAG = "JobSearchResult";
    public RecyclerView jobSearchRV;
    public List<JobData> jobData;
    public Context context;
    private Toolbar toolbar;
    private String jobdata;
    private String citydata;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_result);
        context=this;
        toolbar= findViewById(R.id.toolbar_jobsearchResults);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Results");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jobData = new ArrayList<>();
        jobSearchRV = findViewById(R.id.jobSearchResultRV);
        jobSearchRV.setHasFixedSize(true);
        jobSearchRV.setLayoutManager(new LinearLayoutManager(context));

         jobdata = getIntent().getStringExtra("jobtitle");
         citydata = getIntent().getStringExtra("citydata");
         progressDialog = new ProgressDialog(context);
         progressDialog.setTitle("Processing...");
        if(citydata.equalsIgnoreCase("all cities")){
            progressDialog.show();
            searchwithtitle();
        }else {
            progressDialog.show();
            searchwithtitleandcity();
        }
    }
    private void searchwithtitle() {
        ApiClient.getClient().create(ApiInterface.class)
                .searchJobswithtitle(jobdata).enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                if (!response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.errorBody());
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    return;
                }
                try {
                    progressDialog.dismiss();
                    if (response.body().isEmpty()){
                        View v = LayoutInflater.from(context)
                                .inflate(R.layout.nojobfound, null);
                        AlertDialog dialog = new AlertDialog.Builder(context)
                                .setView(v)
                                .create();
                        dialog.show();
                        dialog.setCancelable(false);

                        TextView ok = v.findViewById(R.id.ok_finish_it);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    }
                    ArrayList<JobData> job = new ArrayList<>();
                    job = response.body();

                    Object object = new Object();

                    for(int i = 0;i<job.size();i++){
                        object = job.get(i);

                        Gson gson = new Gson();
                        String mJson = gson.toJson(object);
                        JobData user = gson.fromJson(mJson,JobData.class);

                        JobData j = new JobData();

                        j.setId(user.id);
                        j.setJobtitle(user.jobtitle);
                        j.setCompany(user.company);
                        j.setCity(user.city);
                        j.setJobdate(user.jobdate);
                        j.setSalaryrange(user.salaryrange);
                        jobData.add(j);

                    }
                    MatchedJobsAdapter adapter = new MatchedJobsAdapter(context,jobData);
                    jobSearchRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }catch (Exception ex){
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Log.d("exception", "onResponse: "+ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                        .create();
                dialog.show();
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
    private void searchwithtitleandcity() {
        ApiClient.getClient().create(ApiInterface.class)
                .searchJobs(jobdata,citydata).enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                if (!response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.errorBody());
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    return;
                }
                try {
                    if (response.body().isEmpty()){
                        View v = LayoutInflater.from(context)
                                .inflate(R.layout.nojobfound, null);
                        AlertDialog dialog1 = new AlertDialog.Builder(context)
                                .setView(v)
                                .create();
                        dialog1.show();
                        dialog1.setCancelable(false);
                        TextView Ok = v.findViewById(R.id.ok_finish_it);
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    }
                    ArrayList<JobData> job = new ArrayList<>();
                    job = response.body();

                    Object object = new Object();

                    for(int i = 0;i<job.size();i++){
                        object = job.get(i);

                        Gson gson = new Gson();
                        String mJson = gson.toJson(object);
                        JobData user = gson.fromJson(mJson,JobData.class);

                        JobData j = new JobData();

                        j.setId(user.id);
                        j.setJobtitle(user.jobtitle);
                        j.setCompany(user.company);
                        j.setCity(user.city);
                        j.setJobdate(user.jobdate);
                        j.setSalaryrange(user.salaryrange);

                        jobData.add(j);
                    }
                    JobSearchAdapter adapter = new JobSearchAdapter(context,jobData);
                    jobSearchRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }catch (Exception ex){
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Log.d("exception", "onResponse: "+ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                        .create();
                dialog.show();
                Log.d("TAG", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}