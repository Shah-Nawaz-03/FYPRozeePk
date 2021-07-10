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

import com.finalyearproject.replicarozeepk.AppliedJobs.AppliedJobStatus;
import com.finalyearproject.replicarozeepk.Jobs.MatchedJobs;
import com.finalyearproject.replicarozeepk.Jobs.SearchJobs;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
public class Jobseekermenu extends AppCompatActivity {
    private static final String TAG = "Jobseekermenu";
    Context context;
    private Button profile;
    private Button searchbtnJsmenu;
    private Button appliedjobbtnJsmenu;
    private Button matchjobbtnJsmenu;
    private Toolbar toolbar;
    public String skills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekermenu);
        context = this;
        toolbar = findViewById(R.id.toolbar_js_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Seeker Menu");

        SessionManagerSignup skillSession = new SessionManagerSignup(context);
        HashMap<String, String> myskill = skillSession.getUsersData();
        skills = myskill.get(SessionManagerSignup.KEY_SKILLS);
        initView();
        Listeners();
    }
    private void Listeners() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, JobseekerProfile.class));
            }
        });
        searchbtnJsmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SearchJobs.class));
            }
        });

        matchjobbtnJsmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatchedJobs.class);
                startActivity(intent);
            }
        });
        appliedjobbtnJsmenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppliedJobStatus.class);
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
        switch (item.getItemId())
        {
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
        View view = LayoutInflater.from(context).inflate(R.layout.exitdialog,null);
        TextView cancel;
        TextView exit;
        cancel = view.findViewById(R.id.cancel_view);
        exit = view.findViewById(R.id.exit_view);
        AlertDialog dialog = new AlertDialog
                .Builder(context)
                .setView(view)
                .create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
    private void initView() {
        profile = (Button) findViewById(R.id.menuprofilebtn);
        searchbtnJsmenu = (Button) findViewById(R.id.searchbtn_jsmenu);
        appliedjobbtnJsmenu = (Button) findViewById(R.id.appliedjobbtn_jsmenu);
        matchjobbtnJsmenu = (Button) findViewById(R.id.matchjobbtn_jsmenu);
    }
}