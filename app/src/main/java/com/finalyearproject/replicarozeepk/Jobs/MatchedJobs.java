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

import com.finalyearproject.replicarozeepk.Adapters.MatchedJobsAdapter;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchedJobs extends AppCompatActivity {

    private static final String TAG = "MatchedJobs";
    Toolbar toolbar;
    private List<JobData> jobData;
    private String Skill, Cgpa, Uni;
    private Context context;
    private RecyclerView matchedJobsRv;
    private ProgressDialog mpg;
    String today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_jobs);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Matched Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calendar = Calendar.getInstance();
        today = DateFormat.getDateInstance()
                .format(calendar.getTime());
        gettingSkill();
        getMatchedJobs();
    }
    private void getMatchedJobs() {
        mpg.show();
        ApiClient.getClient().create(ApiInterface.class).profileMatched(Skill, Cgpa, Uni)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try {

                            if (response.body().isEmpty()) {
                                mpg.dismiss();
                                View v = LayoutInflater.from(context)
                                        .inflate(R.layout.nojobfound, null);
                                AlertDialog dialog = new AlertDialog.Builder(context)
                                        .setView(v).create();
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
                            else {
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
                                    j.setJobtitle(user.jobtitle);
                                    j.setCompany(user.company);
                                    j.setCity(user.city);
                                    j.setJobdate(user.jobdate);
                                    j.setUni(user.uni);
                                    j.setCgpa(user.cgpa);
                                    j.setRequirededucation(user.requirededucation);
                                    j.setSalaryrange(user.salaryrange);
                                    jobData.add(j);

                                }
                                MatchedJobsAdapter adapter = new
                                        MatchedJobsAdapter(context, jobData);
                                matchedJobsRv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                mpg.dismiss();
                            }

                        } catch (Exception exception) {

                            Log.d(TAG, "onResponse: "+exception.getLocalizedMessage());
                            mpg.dismiss();
                            View v = LayoutInflater.from(context)
                                    .inflate(R.layout.nojobfound, null);
                            AlertDialog dialog = new AlertDialog.Builder(context)
                                    .setView(v).create();
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
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                        mpg.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                    }
                });
    }
    private void gettingSkill() {
        SessionManagerSignup skillSession = new SessionManagerSignup(context);
        HashMap<String, String> myskill = skillSession.getUsersData();
        Skill = myskill.get(SessionManagerSignup.KEY_SKILLS);

        Cgpa = myskill.get(SessionManagerSignup.KEY_CGPA);
        Uni = myskill.get(SessionManagerSignup.KEY_UNI);

    }
    private void initView() {
        context = this;
        mpg = new ProgressDialog(context);
        matchedJobsRv = (RecyclerView) findViewById(R.id.matchedJobs_rv);
        matchedJobsRv.setHasFixedSize(true);
        matchedJobsRv.setLayoutManager(new LinearLayoutManager(context));
        jobData = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar_matchedJobs);
        mpg.setTitle("Processing...");
    }
}