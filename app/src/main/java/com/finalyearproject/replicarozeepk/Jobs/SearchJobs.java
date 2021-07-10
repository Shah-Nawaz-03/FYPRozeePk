package com.finalyearproject.replicarozeepk.Jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;

public class SearchJobs extends AppCompatActivity {

    private AutoCompleteTextView jobTitleEditText;
    private Spinner spinnerCity;
    private Button buttonSearch;
    private Context context;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);
        initView();
        toolbar = findViewById(R.id.toolbar_jobsearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Your Dream Job");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        autoCompleteJobTitle();
        spinnercity();
        listeners();
    }
    private void listeners() {
     buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String JobTitledata = jobTitleEditText.getText().toString().trim();
                final String cityData = spinnerCity.getSelectedItem().toString().trim();
                if(checkEmpty() == true) {
                    Intent jobsearch = new Intent(context, JobSearchResult.class);
                    jobsearch.putExtra("jobtitle", JobTitledata);
                    jobsearch.putExtra("citydata", cityData);
                    startActivity(jobsearch);
                }
            }
        });
    }
    private boolean checkEmpty() {
        if (TextUtils.isEmpty(jobTitleEditText.getText())){
            jobTitleEditText.setError("Enter Job Title");
            jobTitleEditText.requestFocus();
            return false;
        }
        if(spinnerCity.getSelectedItem().equals("Select City")){
            spinnerCity.requestFocus();
            Toast.makeText(context, "Select City", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void autoCompleteJobTitle() {
        String[] jobTitle = new String[]{"WEB DEVELOPER","iOS DEVELOPER",
                "ANDROID DEVELOPER",  "UX DESIGNER"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, jobTitle);
        jobTitleEditText.setAdapter(arrayAdapter);
    }
    private void spinnercity() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.array_forsearch,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
    }
    private void initView() {
        jobTitleEditText = (AutoCompleteTextView) findViewById(R.id.jobtitle_edittext);
        spinnerCity = (Spinner) findViewById(R.id.spinner);
        buttonSearch = (Button) findViewById(R.id.button_search);
    }
}