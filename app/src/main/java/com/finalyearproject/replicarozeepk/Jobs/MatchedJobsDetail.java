package com.finalyearproject.replicarozeepk.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.activity.MainActivity;
import com.finalyearproject.replicarozeepk.model.AppliedJobData;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchedJobsDetail extends AppCompatActivity {

    private static final String TAG = "MatchedJobsDetail";
    private Toolbar toolbar;
    private ImageView jdImage;
    private TextView publishDateView, applyByView, jbMj, expDummy, jobTitleMj, companyMj;
    private TextView carDummy, genDummy, salDummy, eduDummy, skillDummy, cityMj;
    private EditText descriptionDummy;
    private Button ApplyBtn;
    private Context context;
    private String id, userId, companyId;
    private JobData job;
    private AppliedJobData data;
    String skillsjs;
    String jid, uid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_jobs_detail);
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing...");
        toolbar = findViewById(R.id.toolbar_detailsmj);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Detail");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gettingId();
        gettingdatafromsession();
        getJobDetail();
        getAppliedInfo();
        applyButtonListener();
    }
    private void getAppliedInfo() {
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).applied(jid,uid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try{
                            if (!response.body().isEmpty()){
                                ApplyBtn.setEnabled(false);
                                ApplyBtn.setText("Applied");
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();

                        }catch (Exception exception){
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
    private void gettingdatafromsession() {
        SessionManagerSignup sessionManagerSignup = new SessionManagerSignup(context);
        HashMap<String,String> userDetails = sessionManagerSignup.getUsersData();

        userId = userDetails.get(SessionManagerSignup.KEY_ID);
        uid = userId.substring(0,2);
        skillsjs = userDetails.get(SessionManagerSignup.KEY_SKILLS);
    }
    private void applyButtonListener() {
        ApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                gettingdatafromsession();
                String cid = companyId.substring(0,2);

                data = new AppliedJobData(jid,cid,uid,"Processing");

                if(skillDummy.getText().toString().equalsIgnoreCase(skillsjs)){
                    ApiClient.getClient().create(ApiInterface.class).applyforJob(data)
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    try{

                                        if(response.isSuccessful()) {

                                            progressDialog.dismiss();
                                            View v = LayoutInflater.from(context).inflate(R.layout.jobapplied,
                                                    null);

                                            AlertDialog dialog = new AlertDialog.Builder(context)
                                                    .setView(v)
                                                    .setCancelable(false).create();
                                            dialog.show();

                                            TextView ok = v.findViewById(R.id.ok_success);
                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            ApplyBtn.setEnabled(false);
                                            ApplyBtn.setText("Applied");
                                            Log.d(TAG, "onResponse: " + response.body());
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, "Unable to apply", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception ex){
                                        progressDialog.dismiss();
                                        View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                                        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(v)
                                                .create();
                                        dialog.show();
                                        Log.d(TAG, "onResponse: "+ response.errorBody());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {

                                    progressDialog.dismiss();
                                    View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(v)
                                            .create();
                                    dialog.show();
                                    Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                                }
                            });
                }
                else {
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.levelup,
                            null);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setView(v).setCancelable(false).create();
                    dialog.show();
                    TextView ok = v.findViewById(R.id.ok_sorry);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
    private void getJobDetail() {
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).jobDetail(jid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try {
                            if (!response.isSuccessful()){
                                Log.d(TAG, "onResponse: Try Again");
                                progressDialog.dismiss();
                            }
                                ArrayList<JobData> jb = new ArrayList<>();
                                jb = response.body();
                                Object obj = new Object();
                                for (int i = 0; i < jb.size(); i++) {
                                    obj = jb.get(i);

                                    Gson gson = new Gson();
                                    String mjson = gson.toJson(obj);
                                    job = gson.fromJson(mjson, JobData.class);
                                    companyId = job.companyid;

                                    jobTitleMj.setText(job.jobtitle);
                                    companyMj.setText(job.company);
                                    cityMj.setText(job.city);
                                    publishDateView.setText(job.jobdate);
                                    applyByView.setText(job.joblastdate);
                                    jbMj.setText(job.jobtype);
                                    expDummy.setText(job.experience);
                                    eduDummy.setText(job.requirededucation);
                                    carDummy.setText(job.careerlevel);
                                    salDummy.setText(job.salaryrange);
                                    descriptionDummy.setText(job.jobdescription);
                                    genDummy.setText(job.gender);
                                    skillDummy.setText(job.skills);
                            }
                            progressDialog.dismiss();
                        }catch (Exception ex){
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
    private void gettingId(){
        if(getIntent().hasExtra("mid")){
            id = getIntent().getStringExtra("mid");
            jid = id.substring(0, 2);
        }
    }
    private void initView() {
        jdImage = (ImageView) findViewById(R.id.jd_image);
        publishDateView = (TextView) findViewById(R.id.publish_date_view);
        applyByView = (TextView) findViewById(R.id.applyBy_view);
        jbMj = (TextView) findViewById(R.id.jb_mj);
        expDummy = (TextView) findViewById(R.id.exp_dummy);
        carDummy = (TextView) findViewById(R.id.car_dummy);
        genDummy = (TextView) findViewById(R.id.gen_dummy);
        salDummy = (TextView) findViewById(R.id.sal_dummy);
        eduDummy = (TextView) findViewById(R.id.edu_dummy);
        skillDummy = (TextView) findViewById(R.id.skill_dummy);
        descriptionDummy = (EditText) findViewById(R.id.description_dummy);
        ApplyBtn = (Button) findViewById(R.id.apply_btn);
        jobTitleMj = (TextView) findViewById(R.id.job_title_mj);
        companyMj = (TextView) findViewById(R.id.company_mj);
        cityMj = (TextView) findViewById(R.id.city_mj);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lagout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_id:{

                View vx = LayoutInflater.from(context)
                        .inflate(R.layout.signout,null);

                TextView cancel;
                TextView signout;

                cancel = vx.findViewById(R.id.cancel_viewx);
                signout = vx.findViewById(R.id.signout_view);

                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setView(vx)
                        .create();

                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(new Intent(context, MainActivity.class));
                    }
                });
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}