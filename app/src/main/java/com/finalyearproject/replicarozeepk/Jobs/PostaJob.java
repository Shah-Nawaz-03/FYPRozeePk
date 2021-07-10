package com.finalyearproject.replicarozeepk.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostaJob extends AppCompatActivity {
    public String companyid; 
    Button postJob;
    EditText jobDescription;
    private AutoCompleteTextView jobTitle;
    private MultiAutoCompleteTextView skills;
    private Toolbar toolbar;
    private Spinner salaryRange, gender, education, careerLevel, experience,cgpa,uni, jobType;
    private String City, Company, Image;
    private ProgressDialog progressDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posta_job);
        toolbar = findViewById(R.id.toolbar_PostJobs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Detail");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        jobTitle = findViewById(R.id.JobTitle_et);
        skills = findViewById(R.id.skills_et);
        careerLevel = findViewById(R.id.CreerLevel_et);
        experience = findViewById(R.id.Experience_et);
        postJob = findViewById(R.id.button_postJob);
        jobType = findViewById(R.id.spinner_jobType);
        gender = findViewById(R.id.spinner_gender);
        jobDescription = findViewById(R.id.Description_met);
        salaryRange = findViewById(R.id.Salary_et);
        education = findViewById(R.id.Education_et);
        cgpa = findViewById(R.id.cgpa_et);
        uni = findViewById(R.id.uni_et);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing");
        companyid = getIntent().getStringExtra("Id");
        AutoComplete();
        Adapters();
        SessionManagerSignup emp = new SessionManagerSignup(this);
        HashMap<String, String> empData = emp.getEmpData();
        City = empData.get(SessionManagerSignup.KEY_CITY);
        Company = empData.get(SessionManagerSignup.KEY_NAME);
        Image = empData.get(SessionManagerSignup.KEY_IMAGE);
        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkempty() == true){
                    if(spinnersCheck() == true){
                    PostAnewJob();}
                }
            }
        });
    }
    private void PostAnewJob(){
        final String jobtitle = jobTitle.getText().toString().trim();
        final String skill = skills.getText().toString().trim().substring(0, skills.length() - 2);
        final String CareerL = careerLevel.getSelectedItem().toString().trim();
        final String exp = experience.getSelectedItem().toString().trim();
        final String jbDescription = jobDescription.getText().toString().trim();
        final String genderj = gender.getSelectedItem().toString().trim();
        final String S_Range = salaryRange.getSelectedItem().toString().trim();
        final String edu = education.getSelectedItem().toString().trim();
        final String cgpaA = cgpa.getSelectedItem().toString().trim();
        final String uniA = uni.getSelectedItem().toString().trim();
        final String jType = jobType.getSelectedItem().toString().trim();
        Calendar calendar = Calendar.getInstance();
        String jobPostingDate = DateFormat.getDateInstance()
                .format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
       String applyBy = DateFormat.getDateInstance().
                format(calendar.getTime());
        JobData jobData = new JobData("",jobtitle,exp,Company,jobPostingDate,jbDescription,applyBy,
                City,skill,CareerL,S_Range,jType,edu,genderj,companyid,Image,
                "active",cgpaA,uniA);

        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class)
                .postJob(jobData).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(!response.isSuccessful()){
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Log.d("fail","onResponse: "+response.message());
                    return;
                }
                try {
                    Intent intent = new Intent(context,
                            Jobs.class);
                    intent.putExtra("compid",companyid);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                    Log.d("JOb posted", "onResponse: "+response.body());
                }catch (Exception ex){
                    progressDialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Log.d("On Catch", "onResponse: "+ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(v)
                        .create();
                dialog.show();
                Log.d("JNP", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private boolean spinnersCheck() {
        if(careerLevel.getSelectedItem().equals("Select Career Level")){
            Toast.makeText(this, "Select Career Level",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (jobType.getSelectedItem().equals("Select Job Type")){
            Toast.makeText(this, "Select Job Type",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(gender.getSelectedItem().equals("Select Gender")){
            Toast.makeText(this, "Select Gender",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (experience.getSelectedItem().equals("Work Experience")){
            Toast.makeText(this, "Select Work Experience",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(education.getSelectedItem().equals("Minimum Qualification")){
            Toast.makeText(this, "Select Minimum Qualification",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (experience.getSelectedItem().equals("Work Experience")){
            Toast.makeText(this, "Select Work Experience",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(cgpa.getSelectedItem().equals("Select Cgpa")) {
            Toast.makeText(this, "Select Cgpa",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (uni.getSelectedItem().equals("Select Institute")){
            Toast.makeText(this, "Select Institute",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean chkempty(){
        if (TextUtils.isEmpty(jobTitle.getText())){
            jobTitle.setError("Job Title is Required");
            jobTitle.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(skills.getText())){
            skills.setError("Skill is Required");
            skills.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(jobDescription.getText())){
            jobDescription.setError("Job Description is Required");
            jobDescription.requestFocus();
            return false;
        }
        return true;
    }


    private void AutoComplete(){
        String[] jobtitle = new String[]
                {"WEB DEVELOPER","iOS DEVELOPER", "UX DESIGNER",
                        "ANDROID DEVELOPER"};
        ArrayAdapter<String> jobadapter = new
                ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, jobtitle);
        jobTitle.setAdapter(jobadapter);
        String[] skill = new String[]{"Kotlin","Java","Swift","C#","PHP","CSS",
                "Adobe","Java Script"};
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, skill);
        MultiAutoCompleteTextView _skillsACTV = findViewById(R.id.skills_et);
        _skillsACTV.setAdapter(skillAdapter);
        _skillsACTV.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }
    private void Adapters() {
        ArrayAdapter<CharSequence> jobtype = ArrayAdapter.createFromResource(this,
                R.array.job_type, android.R.layout.simple_spinner_item);
        jobtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobType.setAdapter(jobtype);

        ArrayAdapter<CharSequence> expAdapter =
                ArrayAdapter.createFromResource(this,R.array.experience,
                        android.R.layout.simple_spinner_item);
        expAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        experience.setAdapter(expAdapter);

        ArrayAdapter<CharSequence> careerAdapter = ArrayAdapter
                .createFromResource(this,R.array.career_level,
                        android.R.layout.simple_spinner_item);
        careerAdapter.setDropDownViewResource(android.R
                .layout.simple_spinner_dropdown_item);
        careerLevel.setAdapter(careerAdapter);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.gender_array_forJob,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android
                .R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        ArrayAdapter<CharSequence> rangeAdapter = ArrayAdapter
                .createFromResource(this,R.array.salaryRange,
                        android.R.layout.simple_spinner_item);
        rangeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        salaryRange.setAdapter(rangeAdapter);

        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter
                .createFromResource(this,R.array.education,
                        android.R.layout.simple_spinner_item);
        eduAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        education.setAdapter(eduAdapter);

        ArrayAdapter<CharSequence> cgpaAdapter = ArrayAdapter
                .createFromResource(this,R.array.cgpa_array,
                        android.R.layout.simple_spinner_item);
        cgpaAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        cgpa.setAdapter(cgpaAdapter);

        ArrayAdapter<CharSequence> uniAdapter = ArrayAdapter
                .createFromResource(this,R.array.uni_array,
                        android.R.layout.simple_spinner_item);
       uniAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        uni.setAdapter(uniAdapter);
    }
}