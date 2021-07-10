package com.finalyearproject.replicarozeepk.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.databinding.ActivityMainBinding;
import com.finalyearproject.replicarozeepk.menu.Employermenu;
import com.finalyearproject.replicarozeepk.menu.Jobseekermenu;
import com.finalyearproject.replicarozeepk.model.UserData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Context context;
    List<UserData> userDataList = new ArrayList<>();
    ActivityMainBinding binding;
    Spinner userTypeSpinner;
    String selectedUserType;
    private Toolbar toolbar;
    ProgressDialog mdialog;
    public ArrayList<Object> myList;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=this;
        toolbar = findViewById(R.id.toolbar_mainActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rozee.pk");

        userTypeSpinner = binding.userTypeSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,R.array.appusertype
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        listners();
    }
    private void listners() {
        binding.signupTextviewLogin.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.choicedialog, null);
                TextView cancel = v.findViewById(R.id.cancel_textview_choice);
                TextView jobseeker = v.findViewById(R.id
                        .jobseeker_textview_choice);
                TextView employer = v.findViewById(R.id
                        .employer_textview_choice);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setView(v)
                        .setCancelable(false)
                        .create();
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                jobseeker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentjs = new Intent(MainActivity
                                .this
                                , JobSeekerSignupActivity.class);
                        startActivity(intentjs);
                        dialog.dismiss();
                    }
                });
                employer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentemp = new Intent(MainActivity.this
                                , EmployerSignupActivity.class);
                        startActivity(intentemp);
                        dialog.dismiss();
                    }
                });
            }
        });
        binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdialog = new ProgressDialog(context);
                login();
            }
        });
    }
    private void login() {
        String email=binding.emailTextviewLogin.getText().toString();
        String pass=binding.passwordEdittextLogin.getText().toString();
       if( userTypeSpinner.getSelectedItem().equals("Sign in as")){
           Toast.makeText(context, "Select user Type", Toast.LENGTH_SHORT).show();
           return;
        }
        if(email.trim().isEmpty()||pass.trim().isEmpty())
        {
            Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
        return;
        }

        mdialog.setMessage("Processing...");
        mdialog.show();
        ApiClient.getClient().create(ApiInterface.class).login(email,pass)
                .enqueue(new Callback<ArrayList>() {
   @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                        if(response.isSuccessful())
                        {
                            try {
                                mdialog.dismiss();
                                ArrayList<UserData> arrayList = new ArrayList<>();
                                arrayList = response.body();
                                Object userObject = new Object();
                                for(int i = 0;i<arrayList.size();i++){
                                    userObject = arrayList.get(i);
                                }
                                Gson gson =new Gson();
                                String myJson = gson.toJson(userObject);
                                UserData userData = gson.fromJson(myJson,UserData.class);

                                SessionManagerSignup sessionManagerSignup =
                                        new SessionManagerSignup(context);

                                sessionManagerSignup.createSignUpSession(userData.companyid,userData.name,
                                        userData.cgpa,userData.skills,
                                        userData.uni,userData.expectedsalary, userData.userimage);

                                SessionManagerSignup emp = new SessionManagerSignup(context);
                                emp.empSignUpSession(userData.companyid,userData.name,userData.email,
                                        userData.city, userData.userimage);
                                Log.d("Response", "onResponse: Data  "+userData.name);
                                    if(userData.usertype.equalsIgnoreCase("job seeker")) {
                                        startActivity(new Intent(context, Jobseekermenu.class));
                                        finish();
                                    }
                                    else{
                                        String id = userData.companyid.substring(0,2);
                                        Intent intent = new Intent(context, Employermenu.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                        finish();
                                    }
                            }catch (Exception ex){
                                mdialog.dismiss();
                            }
                        }
                        else{
                            mdialog.dismiss();
                            View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                            AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                    .create();
                            dialog.show();
                            Log.d("TAG", "onResponse: "+"Not successful");
                            Toast.makeText(context,"Invalid user name or password", Toast.LENGTH_SHORT).show();
                        }
                }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {
                        mdialog.dismiss();
                        View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                        AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                                .create();
                        dialog.show();
                        Log.d("ERROR", "onFailure: "+t.getLocalizedMessage());
                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        selectedUserType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(context, "Please Select User Type", Toast.LENGTH_SHORT).show();
    }
}