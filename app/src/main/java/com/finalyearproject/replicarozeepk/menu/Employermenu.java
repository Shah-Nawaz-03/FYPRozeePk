package com.finalyearproject.replicarozeepk.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.Jobs.PostaJob;
import com.finalyearproject.replicarozeepk.Jobs.PostedJobs;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Employermenu extends AppCompatActivity {
    private Context context;
    private Button CompanyProfile;
    private Button PostAjob;
    private Button viewPostedJobs;
    private String companyId;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employermenu);
        context = this;
        initView();
        toolbar = findViewById(R.id.toolbar_empMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Employer Menu");
        SessionManagerSignup empManager = new SessionManagerSignup(context);
        HashMap<String, String> empDetail = empManager.getEmpData();


        companyId = empDetail.get(SessionManagerSignup.KEY_ID);
        viewPostedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostedJobs.class);
                intent.putExtra("Id", companyId);
                startActivity(intent);
            }
        });
        CompanyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EmployerProfile.class);
                startActivity(intent);
            }
        });
        PostAjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostaJob.class);
                intent.putExtra("Id", companyId.substring(0,2));
                startActivity(intent);
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
    @Override
    public void onBackPressed() {
        View view = LayoutInflater.from(Employermenu.this)
                .inflate(R.layout.exitdialog, null);

        TextView cancelView;
        TextView exitView;
        cancelView = view.findViewById(R.id.cancel_view);
        exitView = view.findViewById(R.id.exit_view);
        AlertDialog dialog = new AlertDialog
                .Builder(context)
                .setView(view)
                .create();
        dialog.show();
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        exitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView() {
        CompanyProfile = findViewById(R.id.company_profile_btn);
        PostAjob = findViewById(R.id.btnpostjob);
        viewPostedJobs = (Button) findViewById(R.id.viewPostedJobs);
    }
}