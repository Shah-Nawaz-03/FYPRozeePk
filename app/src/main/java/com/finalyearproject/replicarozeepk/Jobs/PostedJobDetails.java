package com.finalyearproject.replicarozeepk.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.AppliedJobs.Applicants;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.model.QuizData;
import com.finalyearproject.replicarozeepk.quiz.QuizQuestions;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostedJobDetails extends AppCompatActivity {

    private static final String TAG = "PostedJobDetails";
    private TextView jobTitle;
    private TextView skIlls;
    private TextView salAry;
    private TextView daTe;
    private Button addQuizBtn, applicantsBtn;
    private Context context;
    private Toolbar toolbar;
    private String id, lastdate, postdate, today;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job_details);
        context = this;
        dialog = new ProgressDialog(context);
        dialog.setTitle("Processing...");
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Detail");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIntentData();
        listeners();
        getQuizPostedDetail();
        lastdate = getIntent().getStringExtra("lastdate");
        Calendar calendar = Calendar.getInstance();
        today = DateFormat.getDateInstance()
                .format(calendar.getTime());

        if(lastdate.equalsIgnoreCase(today)){
            applicantsBtn.setText("Job Closed");
            applicantsBtn.setEnabled(false);
            addQuizBtn.setEnabled(false);
        }

    }
    private void getQuizPostedDetail() {
        JobIdSession jxid = new JobIdSession(context);
        HashMap<String, String> Jobid = jxid.getJobid();
        String mid = Jobid.get(JobIdSession.KEY_JID);

        ApiClient.getClient().create(ApiInterface.class).getquizid(mid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if(response.isSuccessful() && !response.body().isEmpty()){
                            addQuizBtn.setEnabled(false);
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                    }
                });
    }
    private void listeners() {

        addQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                JobIdSession jxid = new JobIdSession(context);
                HashMap<String, String> Jobid = jxid.getJobid();
                id = Jobid.get(JobIdSession.KEY_JID);

                QuizData data = new QuizData("",id,today);
                ApiClient.getClient().create(ApiInterface.class).createquiz(data)
                        .enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {

                                dialog.dismiss();
                                Intent intent = new Intent(context, QuizQuestions.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                                dialog.dismiss();
                                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        applicantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Applicants.class);
                startActivity(intent);
            }
        });
    }
    private void getIntentData() {
        Log.d(TAG, "getIntentData: Checking Intent Coming");
        if (getIntent().hasExtra("title") && getIntent().hasExtra("date")
                && getIntent().hasExtra("skill") && getIntent().hasExtra("salary")) {
            String title = getIntent().getStringExtra("title");
            String skill = getIntent().getStringExtra("skill");
            String salary = getIntent().getStringExtra("salary");
            String date = getIntent().getStringExtra("date");
            setData(title,skill,salary,date);
        }
    }
    private void setData(String title, String skill, String salary, String date) {
        Log.d(TAG, "setData: setting data to views");
        jobTitle.setText(title);
        skIlls.setText(skill);
        salAry.setText(salary);
        daTe.setText(date);
    }
    private void initView() {
        jobTitle = (TextView) findViewById(R.id.job_title);
        skIlls = (TextView) findViewById(R.id.sk_ills);
        salAry = (TextView) findViewById(R.id.sal_ary);
        daTe = (TextView) findViewById(R.id.da_te);
        addQuizBtn = (Button) findViewById(R.id.add_quiz_btn);
        applicantsBtn = (Button) findViewById(R.id.applicants_btn);
        toolbar = findViewById(R.id.toolbar_PostJob_detail);
    }
}