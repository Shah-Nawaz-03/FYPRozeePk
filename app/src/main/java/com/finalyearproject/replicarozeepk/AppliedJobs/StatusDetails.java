package com.finalyearproject.replicarozeepk.AppliedJobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.AppliedJobData;
import com.finalyearproject.replicarozeepk.quiz.AttemptQuiz;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDetails extends AppCompatActivity {
    private TextView statusJtitle, statusJcompany, statusStatus;
    private String status,id,jobid;
    private ProgressDialog pd;
    private Toolbar toolbar;
    private Context context;
    private Button attemptQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);
        context=this;
        toolbar = findViewById(R.id.toolbar_statusdetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("apjId");
        jobid = getIntent().getStringExtra("jobid");
        String title = getIntent().getStringExtra("apjtitle");
        String company = getIntent().getStringExtra("apjcompany");
        initView();
        pd = new ProgressDialog(this);
        pd.setTitle("Processing...");

        statusJtitle.setText(title);
        statusJcompany.setText(company);
        ApiClient.getClient().create(ApiInterface.class).status(id)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try {
                            pd.show();
                            List<AppliedJobData> data = new ArrayList<>();

                            data = response.body();
                            Object o = new Object();

                            for (int i =0;i<data.size();i++){
                                o= data.get(i);

                                Gson gson = new Gson();
                                String json = gson.toJson(o);
                                AppliedJobData d = gson.fromJson(json, AppliedJobData.class);

                                statusStatus.setText(d.status);
                                attemptQuiz();
                            }
                            pd.dismiss();

                        }catch (Exception ex){

                            pd.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        pd.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                    }
                });
    }
    private void attemptQuiz() {
        if (statusStatus.getText().toString().equalsIgnoreCase("In Review")){
            attemptQuiz.setEnabled(true);
            attemptQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AttemptQuiz.class);
                    intent.putExtra("jjobid",jobid);
                    startActivity(intent);
                }
            });
        }
    }
    private void initView() {
        statusJtitle = (TextView) findViewById(R.id.status_jtitle);
        statusJcompany = (TextView) findViewById(R.id.status_jcompany);
        statusStatus = (TextView) findViewById(R.id.status_status);
        attemptQuiz = findViewById(R.id.attemptbtn);
    }
}