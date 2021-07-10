package com.finalyearproject.replicarozeepk.AppliedJobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.model.AppliedJobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetStatus extends AppCompatActivity {

    private static final String TAG = "SetStatus";
    private Toolbar toolbar;
    private Context context;
    private Spinner statusofemp;
    private TextView name ,exp, expectedSalary;
    private String userid, jobid, jid, eid, jstatus;
    private Button setStatusBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_status);
        context = this;
        toolbar = findViewById(R.id.toolbar_setstatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Set Applicants Status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing...");
        statusofemp = findViewById(R.id.setstatusSpinner);
        name = findViewById(R.id.applicantsname);
        exp = findViewById(R.id.applicantsexp);
        expectedSalary = findViewById(R.id.applicants_expectedSalary);
        setStatusBtn = findViewById(R.id.finalStatus_btn);
        userid = getIntent().getStringExtra("applicantsId");

        JobIdSession jxid = new JobIdSession(context);
        HashMap<String, String> Jobid = jxid.getJobid();
        jid = Jobid.get(JobIdSession.KEY_JID);

        String nameofa = getIntent().getStringExtra("applicantsName");
        String expofa = getIntent().getStringExtra("applicantsExp");
        String expectedSalaryofa = getIntent().getStringExtra("applicantsExpectedSalary");
         eid = userid.substring(0, 2);
         jobid = jid.substring(0, 2);
        name.setText(nameofa);
        exp.setText(expofa);
        expectedSalary.setText(expectedSalaryofa);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.setstatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusofemp.setAdapter(adapter);
        setStatusButtonValue();
        setStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinnerCheck() == true){
                     jstatus = statusofemp.getSelectedItem().toString().trim();
                     setStatusBtn.setText(jstatus);
                    setStatusFuntion(jstatus,jobid,eid);
                }
            }
        });
    }
    private void setStatusButtonValue() {
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).applied(jobid, eid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        try {
                            ArrayList<AppliedJobData> ad = new ArrayList<>();

                            ad = response.body();
                            Object o = new Object();

                            for (int i = 0; i < ad.size(); i++) {
                                o = ad.get(i);

                                Gson gson = new Gson();
                                String myjson = gson.toJson(o);
                                AppliedJobData a = gson.fromJson(myjson, AppliedJobData.class);

                                String st = a.getStatus();
                                setStatusBtn.setText(st);
                                progressDialog.dismiss();
                            }
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
    private void setStatusFuntion(String status, String jobid, String eid) {
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).setStatus(status,jobid,eid)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            Log.d(TAG, "onResponse: "+response.errorBody());
                            progressDialog.dismiss();
                        }
                        try {
                            progressDialog.dismiss();
                            Log.d(TAG, "onResponse: "+response.message());
                        }catch (Exception e){
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    }
                });
    }
    private boolean spinnerCheck(){
        if (statusofemp.getSelectedItem().equals("Select Status")){
            Toast.makeText(context, "Select Status", Toast.LENGTH_SHORT).show();
            statusofemp.requestFocus();
            return false;
        }
        return true;
    }
}