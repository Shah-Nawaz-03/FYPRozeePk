package com.finalyearproject.replicarozeepk.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;
import com.finalyearproject.replicarozeepk.model.UserData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class JobseekerProfile extends AppCompatActivity {

    private TextView name,skills,expectedSalary;
    private Button update;
    private UserData data;
    private ImageView userProfile;
    Context context;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker_profile);
        context =this;
        toolbar = findViewById(R.id.toolbar_js_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Seeker Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.name_dummy);
        skills = findViewById(R.id.skills_dummy);
        expectedSalary = findViewById(R.id.expsalary_dummy);
        userProfile = findViewById(R.id.userProfile);

        SessionManagerSignup sessionManagerSignup = new SessionManagerSignup(context);
        HashMap<String,String> userDetails = sessionManagerSignup.getUsersData();

        String namejs = userDetails.get(SessionManagerSignup.KEY_NAME);
        String skillsjs = userDetails.get(SessionManagerSignup.KEY_SKILLS);
        String expSalaryjs = userDetails.get(SessionManagerSignup.KEY_EXPECTEDSALARY);
        String imagejs = userDetails.get(SessionManagerSignup.KEY_IMAGE);
        byte [] encodeByte= Base64.decode(imagejs,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        name.setText(namejs);
        skills.setText(skillsjs);
        expectedSalary.setText(expSalaryjs);
        userProfile.setImageBitmap(bitmap);
    }
}