package com.finalyearproject.replicarozeepk.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.SessionManagerSignup;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class EmployerProfile extends AppCompatActivity {
    private TextView name, email, city;
    Context context;
    private Toolbar toolbar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
        context = this;
        toolbar = findViewById(R.id.toolbar_emp_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Employer Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.name_dummy);
        email = findViewById(R.id.city_dummy);
        city = findViewById(R.id.cityemp_dummy);
        imageView = findViewById(R.id.companyProfileEmp);

        SessionManagerSignup empManager = new SessionManagerSignup(context);
        HashMap<String, String> empDetail = empManager.getEmpData();

        String ename = empDetail.get(SessionManagerSignup.KEY_NAME);
        String eemail = empDetail.get(SessionManagerSignup.KEY_EMAIL);
        String ecity = empDetail.get(SessionManagerSignup.KEY_CITY);
        String eimage = empDetail.get(SessionManagerSignup.KEY_IMAGE);
        byte [] encodeByte= Base64.decode(eimage,Base64.DEFAULT);

        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        imageView.setImageBitmap(bitmap);
        name.setText(ename);
        email.setText(eemail);
        city.setText(ecity);
    }
}