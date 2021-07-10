package com.finalyearproject.replicarozeepk.AppliedJobs;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.Adapters.ApplicantsAdapter;
import com.finalyearproject.replicarozeepk.Adapters.Both;
import com.finalyearproject.replicarozeepk.Adapters.Uni;
import com.finalyearproject.replicarozeepk.Adapters.cgpaAdapter;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.activity.MainActivity;
import com.finalyearproject.replicarozeepk.model.AppliedJobData;
import com.finalyearproject.replicarozeepk.model.UserData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Applicants extends AppCompatActivity {

    private static final String TAG = "Applicants";
    private Toolbar toolbar;
    private RecyclerView applicants_view;
    private Context context;
    public String jid, acgpa, auni;
    private List<UserData> userDataList;
    private List<UserData> uniData;
    private List<UserData> cgpaData;
    private List<UserData> both;
    private ProgressDialog pg;
    public Spinner Uni,Cgpa;
    private Button filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        context = this;
        toolbar = findViewById(R.id.toolbar_applicants);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applicants");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        pg = new ProgressDialog(context);
        pg.setTitle("Processing");
        applicants_view = findViewById(R.id.applicants_rv);

        Uni = findViewById(R.id.select_uni);
        Cgpa = findViewById(R.id.select_cgpa);
        filter = findViewById(R.id.filter);
        JobIdSession jxid = new JobIdSession(context);
        HashMap<String, String> Jobid = jxid.getJobid();
        jid = Jobid.get(JobIdSession.KEY_JID);
        Spinners();
        userDataList = new ArrayList<>();
        acgpa = Cgpa.getSelectedItem().toString().trim();
        auni = Uni.getSelectedItem().toString().trim();
        getApplicantsData();


       filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auni = Uni.getSelectedItem().toString();
                acgpa = Cgpa.getSelectedItem().toString();

                if (acgpa.equalsIgnoreCase("Select Cgpa")
                        && auni.equalsIgnoreCase("Select Institute")){
                    userDataList = new ArrayList<>();

                    applicants_view.getRecycledViewPool().clear();
                     getApplicantsData();
                } else if(!auni.equalsIgnoreCase("Select Institute")
                        && !acgpa.equalsIgnoreCase("Select Cgpa")){
                    both = new ArrayList<>();
                    getSpecific(acgpa, auni, jid);

                }  else if(!auni.equalsIgnoreCase("Select Institute")
                        && acgpa.equalsIgnoreCase("Select Cgpa")){
                    uniData = new ArrayList<>();
                    getApplicantsUni(auni,jid);
                }else if(auni.equalsIgnoreCase("Select Institute")
                        && !acgpa.equalsIgnoreCase("Select Cgpa")){
                    cgpaData = new ArrayList<>();
                    getApplicantsCgpa(acgpa, jid);
                }
            }
        });
    }
    private void getSpecific(String acgpa, String auni, String jid) {
        pg.show();
        ApiClient.getClient().create(ApiInterface.class).cgpaanduni(acgpa,auni,jid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if(!response.isSuccessful()){

                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+response.errorBody());
                        }
                        try{
                            if (response.body().isEmpty()){
                                pg.dismiss();
                                View v = LayoutInflater.from(context).inflate(R.layout.no_one,null);
                                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                        .create();
                                dialog.show();
                                dialog.setCancelable(false);

                                TextView ok = v.findViewById(R.id.ok_finish);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });
                            }
                            else{
                                ArrayList<UserData> d = new ArrayList<>();

                                d= response.body();
                                Object o = new Object();

                                for(int i= 0;i<d.size();i++) {
                                    o = d.get(i);

                                    Gson gson = new Gson();
                                    String mJson = gson.toJson(o);
                                    UserData user = gson.fromJson(mJson, UserData.class);
                                    AppliedJobData data = gson.fromJson(mJson,AppliedJobData.class);

                                    UserData u = new UserData();

                                    u.setCompanyid(user.companyid);
                                    u.setName(user.name);
                                    u.setEducation(user.education);
                                    u.setExperience(user.experience);
                                    u.setExpectedsalary(user.expectedsalary);
                                    u.setCareerlevel(user.careerlevel);
                                    u.setCgpa(user.cgpa);
                                    u.setUni(user.uni);
                                    both.add(u);
                                }
                                Both adab = new Both(context, both);
                                applicants_view.setLayoutManager(new LinearLayoutManager(context));
                                applicants_view.requestLayout();
                                applicants_view.setAdapter(adab);

                                pg.dismiss();
                           }
                        }catch (Exception e){
                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+e.getLocalizedMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                    }
                });
    }
    private void getApplicantsCgpa(String acgpa, String jid){
        pg.show();
        ApiClient.getClient().create(ApiInterface.class).applicantsCgpa(acgpa, jid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if(!response.isSuccessful()){

                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+response.errorBody());
                        }
                        try{
                            if (response.body().isEmpty()){
                                pg.dismiss();
                                View v = LayoutInflater.from(context).inflate(R.layout.no_one,null);
                                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                        .create();
                                dialog.show();
                                dialog.setCancelable(false);

                                TextView ok = v.findViewById(R.id.ok_finish);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });

                            }
                            else {
                                ArrayList<UserData> d = new ArrayList<>();

                                d = response.body();
                                Object o = new Object();

                                for (int i = 0; i < d.size(); i++) {
                                    o = d.get(i);

                                    Gson gson = new Gson();
                                    String mJson = gson.toJson(o);
                                    UserData user = gson.fromJson(mJson, UserData.class);
                                    AppliedJobData data = gson.fromJson(mJson, AppliedJobData.class);

                                    UserData u = new UserData();

                                    u.setCompanyid(user.companyid);
                                    u.setName(user.name);
                                    u.setEducation(user.education);
                                    u.setExperience(user.experience);
                                    u.setExpectedsalary(user.expectedsalary);
                                    u.setCareerlevel(user.careerlevel);
                                    u.setCgpa(user.cgpa);
                                    u.setUni(user.uni);
                                    cgpaData.add(u);

                                }
                                cgpaAdapter adapter = new cgpaAdapter(context, cgpaData);
                                adapter.onAttachedToRecyclerView(applicants_view);
                                applicants_view.setLayoutManager(new LinearLayoutManager(context));
                                applicants_view.setAdapter(adapter);


                                pg.dismiss();
                            }
                        }catch (Exception e){
                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                    }
                });
    }

    private void getApplicantsUni(String auni, String jid) {
        pg.show();
        ApiClient.getClient().create(ApiInterface.class).applicantsUni(auni, jid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if(!response.isSuccessful()){

                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+response.errorBody());
                        }
                        try{
                            if (response.body().isEmpty()){
                                pg.dismiss();
//                                View v = LayoutInflater.from(context).inflate(R.layout.no_one,null);
//                                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
//                                        .create();
//                                dialog.show();
//                                dialog.setCancelable(false);
//
//                                TextView ok = v.findViewById(R.id.ok_finish);
//                                ok.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        finish();
//                                    }
//                                });

                            }
                            else{
                                ArrayList<UserData> d = new ArrayList<>();

                                d= response.body();
                                Object o = new Object();

                                for(int i= 0;i<d.size();i++) {
                                    o = d.get(i);

                                    Gson gson = new Gson();
                                    String mJson = gson.toJson(o);
                                    UserData user = gson.fromJson(mJson, UserData.class);
                                    AppliedJobData data = gson.fromJson(mJson,AppliedJobData.class);

                                    UserData u = new UserData();

                                    u.setCompanyid(user.companyid);
                                    u.setName(user.name);
                                    u.setEducation(user.education);
                                    u.setExperience(user.experience);
                                    u.setExpectedsalary(user.expectedsalary);
                                    u.setCareerlevel(user.careerlevel);
                                    u.setCgpa(user.cgpa);
                                    u.setUni(user.uni);
                                    uniData.add(u);

                                    com.finalyearproject.replicarozeepk.Adapters.Uni ax = new Uni(context, uniData);
                                    applicants_view.setLayoutManager(new LinearLayoutManager(context));
                                    applicants_view.hasFixedSize();
                                    applicants_view.setAdapter(ax);

                                }


                                pg.dismiss();
                            }
                        }catch (Exception e){
                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                    }
                });
    }

    private void Spinners() {
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                .createFromResource(this
                        , R.array.cgpa_array
                        , android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        Cgpa.setAdapter(adapter1);



        ArrayAdapter<CharSequence> uniAdapter = ArrayAdapter
                .createFromResource(context
                        ,R.array.uni_array
                        , android.R.layout.simple_spinner_item);
        uniAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        Uni.setAdapter(uniAdapter);

    }

    private void getApplicantsData() {
        pg.show();
        ApiClient.getClient().create(ApiInterface.class).applicants(jid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if(!response.isSuccessful()){

                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+response.errorBody());
                        }
                        try{
                            if (response.body().isEmpty()){
                                getApplicantsUni(auni, jid);
                                pg.dismiss();
                                View v = LayoutInflater.from(context).inflate(R.layout.no_one,null);
                                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                        .create();
                                dialog.show();
                                dialog.setCancelable(false);

                                TextView ok = v.findViewById(R.id.ok_finish);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });

                            }
                            else{
                        ArrayList<UserData> d = new ArrayList<>();

                        d= response.body();
                        Object o = new Object();

                        for(int i= 0;i<d.size();i++) {
                            o = d.get(i);

                            Gson gson = new Gson();
                            String mJson = gson.toJson(o);
                            UserData user = gson.fromJson(mJson, UserData.class);
                            AppliedJobData data = gson.fromJson(mJson,AppliedJobData.class);

                            UserData u = new UserData();

                            u.setCompanyid(user.companyid);
                            u.setName(user.name);
                            u.setEducation(user.education);
                            u.setExperience(user.experience);
                            u.setExpectedsalary(user.expectedsalary);
                            u.setCareerlevel(user.careerlevel);
                            u.setCgpa(user.cgpa);
                            u.setUni(user.uni);

                            userDataList.add(u);

                        }

                                ApplicantsAdapter a = new ApplicantsAdapter(context, userDataList);
                                applicants_view.setLayoutManager(new LinearLayoutManager(context));
                                applicants_view.clearOnChildAttachStateChangeListeners();
                                applicants_view.getBaseline();

                                applicants_view.new Recycler().clear();
                                applicants_view.hasFixedSize();
                                applicants_view.requestLayout();
                                applicants_view.clearOnScrollListeners();
                                  a.notifyDataSetChanged();
                                applicants_view.setAdapter(a);


                                pg.dismiss();
                       }
                        }catch (Exception e){
                            pg.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d(TAG, "onResponse: "+e.getLocalizedMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        pg.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    }
                });
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

                AlertDialog dialog = new AlertDialog.Builder(context)
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