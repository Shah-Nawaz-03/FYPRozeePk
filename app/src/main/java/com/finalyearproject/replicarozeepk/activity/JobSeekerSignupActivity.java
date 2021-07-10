package com.finalyearproject.replicarozeepk.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.UserData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobSeekerSignupActivity extends AppCompatActivity {

    private Bitmap bitmap;
    String enc=null;
    private Context context;
    Button imageGet;
    public Spinner cgpa, gender, expsalary, careerLevel, Experience, Education,uni;
    public EditText name, email, password,confirmPassword;
    public MultiAutoCompleteTextView skill;
    Button CreateAccount;
    public UserData userData;
    public static final String MyPreferences = "MyPref";
    public ProgressDialog mdialog;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_signup);
        toolbar = findViewById(R.id.toolbar_jobseekerSignUp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        name = findViewById(R.id.name_edittext_js);
        email = findViewById(R.id.email_edittext_js);
        password = findViewById(R.id.password_edittext_js);

        Education = findViewById(R.id.education_spinner);
        uni = findViewById(R.id.uni_spinner_js);
        cgpa = findViewById(R.id.cgpa_spinner_js);

        imageGet = findViewById(R.id.image_js);
        skill = findViewById(R.id.skills_textview_js);
        careerLevel = findViewById(R.id.careerlevel_spinner);
        Experience = findViewById(R.id.experience_spinner);

        confirmPassword = findViewById(R.id.confirmpassword_edittext_js);
        spinners();
        listeners();
        autocompletejobtitle();
        CreateAccount = findViewById(R.id.signup_js);
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdialog = new ProgressDialog(context);
                if(checkEmpty() == false){
                   if(spinnerCheck() == true){
                       if (emailValidation() == true){
                           createAccount();
                       }
                   }
                }

            }
        });

    }
    private void createAccount() {

        final String jsname = name.getText().toString().trim();
        final String jsemail = email.getText().toString().trim();
        final String jspass = password.getText().toString().trim();
        final String jsskills = skill.getText().toString().trim().substring(0, skill.length() - 2);
        final String jsuni = uni.getSelectedItem().toString().trim();
        final String jseexpectedsalary = expsalary.getSelectedItem().toString().trim();
        final String jscgpa = cgpa.getSelectedItem().toString().trim();
        final String jsgender = gender.getSelectedItem().toString().trim();
        final String jsexp = Experience.getSelectedItem().toString().trim();
        final String jscl = careerLevel.getSelectedItem().toString().trim();
        final String jsedu = Education.getSelectedItem().toString().trim();

        UserData data = new UserData("",jsname,jsemail,jspass,
                jsgender,"Job Seeker","desig",jsexp,jseexpectedsalary
                ,jscl,"city",enc,jsskills,jsedu,jscgpa,jsuni);

        mdialog.setMessage("Processing...");
        mdialog.show();

        ApiClient.getClient().create(ApiInterface.class)
                .signupjs(data).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (!response.isSuccessful()) {
                    mdialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Log.d("not seccessful", "onResponse: " + response.message());
                    return;
                }
                try {
                    mdialog.dismiss();

                    Log.d("userName", "onResponse: username "+ data.name);

                startActivity(new Intent(context, MainActivity.class));
                    Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                finish();
                }catch (Exception ex){
                    mdialog.dismiss();
                    View v = LayoutInflater.from(context).inflate(R.layout.error,null);
                    AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                            .create();
                    dialog.show();
                    Toast.makeText(context, "Catch"+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mdialog.dismiss();
                View v = LayoutInflater.from(context).inflate(R.layout.connectionissue,null);
                AlertDialog dialog = new AlertDialog.Builder(context).setView(v)
                        .create();
                dialog.show();
                Log.d("Try again", "onFailure: Try again with" + t.getLocalizedMessage());

            }
        });
    }
    private boolean emailValidation(){
        String emailv = email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!emailv.matches(emailPattern)){
            email.setError("Invalid Email Pattern");
            email.requestFocus();
            return false;
        }

        return true;
    }

    private boolean checkEmpty()
    {
        if(name.getText().toString().trim().equalsIgnoreCase("")){
            name.setError("Name is required");
            name.requestFocus();
            return true;
        }
        if(skill.getText().toString().trim().equalsIgnoreCase("")){
            skill.setError("Skills are required");
            skill.requestFocus();
            return true;
        }
        if (email.getText().toString().trim().equalsIgnoreCase("") && Patterns.EMAIL_ADDRESS.matcher("email").matches()){
            email.setError("Email is required");
            email.requestFocus();
            return  true;
        }
        if(password.getText().toString().trim().equalsIgnoreCase("")) {
            {
                password.setError("Password is required");
                password.requestFocus();
                return true;
            }
        }
        if(password.getText().toString().length() < 6){
            Toast.makeText(context, "Password must be of minimum 6 characters length", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return true;
        }
        if(!confirmPassword.getText().toString().equalsIgnoreCase(password.getText().toString())){
            confirmPassword.setError("Password did not match");
            confirmPassword.requestFocus();
            return true;
        }
        return false;
    }

    private boolean spinnerCheck() {
        if(cgpa.getSelectedItem().equals("Select CGPA")){
            Toast.makeText(context, "Select CGPA",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(uni.getSelectedItem().equals("Select Institute")){
            Toast.makeText(context, "Select Institute", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender.getSelectedItem().equals("Select Gender")){
            Toast.makeText(context, "Select Gender",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (expsalary.getSelectedItem().equals("Expected Salary")){
            Toast.makeText(context, "Select Expected Salary",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Education.getSelectedItem().equals("Qualification")){
            Toast.makeText(context,"Select Qualification",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (careerLevel.getSelectedItem().equals("Select Career Level")){
            Toast.makeText(context, "Select Career Level", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Experience.getSelectedItem().equals("Select Experience")){
            Toast.makeText(context, "Work Experience", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void spinners() {

        Experience = findViewById(R.id.experience_spinner);
        ArrayAdapter<CharSequence> adapterexp = ArrayAdapter
                .createFromResource(this, R.array.experience,
                        android.R.layout.simple_spinner_item);

        adapterexp.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
       Experience.setAdapter(adapterexp);

        Education = findViewById(R.id.education_spinner);
        ArrayAdapter<CharSequence> adapteredu = ArrayAdapter
                .createFromResource(this, R.array.jseducation,
                        android.R.layout.simple_spinner_item);

        adapteredu.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        Education.setAdapter(adapteredu);

        careerLevel = findViewById(R.id.careerlevel_spinner);
        ArrayAdapter<CharSequence> adaptercareer = ArrayAdapter
                .createFromResource(this, R.array.career_level,
                android.R.layout.simple_spinner_item);

        adaptercareer.setDropDownViewResource(android.R.layout
        .simple_spinner_dropdown_item);
        careerLevel.setAdapter(adaptercareer);


        gender = findViewById(R.id.gender_spinner_js);
        ArrayAdapter<CharSequence> adaptergender = ArrayAdapter
                .createFromResource(this, R.array.gender_array
                        , android.R.layout.simple_spinner_item);

        adaptergender.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        gender.setAdapter(adaptergender);

        cgpa = findViewById(R.id.cgpa_spinner_js);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                .createFromResource(this
                        , R.array.cgpa_array
                        , android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        cgpa.setAdapter(adapter1);

        uni = findViewById(R.id.uni_spinner_js);
        ArrayAdapter<CharSequence> uniAdapter = ArrayAdapter
                .createFromResource(context
                        ,R.array.uni_array
                        , android.R.layout.simple_spinner_item);
        uniAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        uni.setAdapter(uniAdapter);


        expsalary = findViewById(R.id.salary_textview_js);
        ArrayAdapter<CharSequence> expAdapter = ArrayAdapter
                .createFromResource(context
                ,R.array.expsalary
                , android.R.layout.simple_spinner_item);
        expAdapter.setDropDownViewResource(android.R.layout
        .simple_spinner_dropdown_item);
        expsalary.setAdapter(expAdapter);


    }


    private void listeners() {

        imageGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = LayoutInflater.from(JobSeekerSignupActivity.this)
                        .inflate(R.layout.cameragallerychoice, null);

                TextView camera = v.findViewById(R.id.tvcamera);
                TextView gallery = v.findViewById(R.id.tvgallery);

                AlertDialog dialog = new AlertDialog
                        .Builder(JobSeekerSignupActivity.this)
                        .setView(v)
                        .create();
                dialog.show();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (checkAndRequestPermissions()) {
                        }
                        takePictureformCamera();
                        dialog.dismiss();
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        takePictureformGallery();
                        dialog.dismiss();

                    }
                });
            }

        });

    }

    private void takePictureformGallery() {
        Intent galleryintent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryintent, 1);

    }
    private void takePictureformCamera() {
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraintent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraintent, 2);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri filepath=data.getData();
                    assert filepath != null;

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(filepath);
                        bitmap = BitmapFactory.decodeStream(inputStream);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] imgb = byteArrayOutputStream.toByteArray();
                        enc = android.util.Base64.encodeToString(imgb, Base64.DEFAULT);
                        Toast.makeText(context, "Image is Saved", Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {

                    }

                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    Toast.makeText(context, "Image is Saved"
                            , Toast.LENGTH_SHORT).show();
                    Bitmap bitmapcamera = (Bitmap) bundle.get("data");

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmapcamera.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imgb = byteArrayOutputStream.toByteArray();
                    enc = android.util.Base64.encodeToString(imgb, Base64.DEFAULT);
                }
                break;
        }
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {

            int cameraPermission = ActivityCompat.checkSelfPermission(JobSeekerSignupActivity
                    .this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(JobSeekerSignupActivity.this,
                        new String[]{Manifest.permission.CAMERA}, 13);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 13 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureformCamera();
        } else {
            Toast.makeText(JobSeekerSignupActivity.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void autocompletejobtitle() {

//        String[] jobtitle = new String[]
//                {"WEB DEVELOPER","iOS DEVELOPER", "UX DESIGNER", "ANDROID DEVELOPER"};
//        ArrayAdapter<String> jobadapter = new
//                ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, jobtitle);
//        AutoCompleteTextView tvjobs = findViewById(R.id.job_textview_js);
//        tvjobs.setAdapter(jobadapter);

        String[] skill = new String[]{"Kotlin","Java","Swift","CSS","C#","PHP",
        "Adobe","Java Script"};
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, skill);

        MultiAutoCompleteTextView _skillsACTV = findViewById(R.id.skills_textview_js);
        _skillsACTV.setAdapter(skillAdapter);
        _skillsACTV.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}







