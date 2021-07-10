package com.finalyearproject.replicarozeepk.AppliedJobs;

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

import com.finalyearproject.replicarozeepk.Adapters.JobStatusAdapter;
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

public class AppliedJobStatus extends AppCompatActivity {

    private static final String TAG = "AppliedJobStatus";
    public RecyclerView statusView;
    private Toolbar toolbar;
    private ProgressDialog dialog;
    private Context context;
    private String id;
    String uid;
    public List<JobData> jobData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job_status);
        context = this;
        toolbar = findViewById(R.id.toolbar_appliedjobstatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applied Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        statusView = findViewById(R.id.status_rv);
        jobData = new ArrayList<>();
        statusView.setHasFixedSize(true);
        statusView.setLayoutManager(new LinearLayoutManager(context));

        dialog = new ProgressDialog(context);
        dialog.setTitle("Processing...");

        SessionManagerSignup sm = new SessionManagerSignup(context);
        HashMap<String, String> user = sm.getUsersData();
        id = user.get(SessionManagerSignup.KEY_ID);
        uid = id.substring(0,2);
        getStatus();
    }
    private void getStatus() {

        ApiClient.getClient().create(ApiInterface.class).jobStatus(uid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                        try{
                            dialog.show();
                            if (response.body().isEmpty())
                            {
                                View v = LayoutInflater.from(context)
                                        .inflate(R.layout.notappliedyet, null);
                                AlertDialog dialog = new AlertDialog.Builder(context)
                                        .setView(v).create();
                                dialog.show();
                                dialog.setCancelable(false);

                                TextView ok = v.findViewById(R.id.ok_ok);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });
                            }
                            ArrayList<JobData> job = new ArrayList<>();
                            job = response.body();

                            Object obj = new Object();

                            for (int i = 0; i < job.size(); i++) {
                                obj = job.get(i);

                                Gson gson = new Gson();
                                String mJson = gson.toJson(obj);
                                JobData user = gson.fromJson(mJson, JobData.class);

                                JobData j = new JobData();

                                j.setId(user.id);
                                j.setCompanyid(user.companyid);
                                j.setJobtitle(user.jobtitle);
                                j.setCompany(user.company);
                                jobData.add(j);
                            }
                            JobStatusAdapter adapter = new
                                    JobStatusAdapter(context, jobData);
                            statusView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            dialog.dismiss();

                        }catch (Exception exception){
                            Log.d(TAG, "onResponse: "+exception.getLocalizedMessage());
                            dialog.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                        dialog.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                    }
                });
    }
}