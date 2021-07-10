package com.finalyearproject.replicarozeepk.quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.model.QuestionData;
import com.finalyearproject.replicarozeepk.model.QuizData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizQuestions extends AppCompatActivity {

    private static final String TAG = "QuizQuestions";
    private EditText questionStatement, option1, opt2, option3, opt4, ans;
    private Button addQuestionBtn;
    private Toolbar toolbar;
    private Context context;
    private ProgressDialog progressDialog;
    private String questionid,mquizid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing...");

        initView();
        getquizid();
        addQuestionBtn.setOnClickListener(view -> {
            addnewQuestion();
        });
    }
    private void addentry() {
        ApiClient.getClient().create(ApiInterface.class).add(mquizid,questionid)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(context, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "onResponse: "+response.message());
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    }
                });
    }
    private void addnewQuestion() {
        if(emptychk() == true){
           createQuestion();
        }
    }
    private void EmptyAllFields() {
        questionStatement.setText(null);
        option1.setText(null);
        opt2.setText(null);
        option3.setText(null);
        opt4.setText(null);
        ans.setText(null);
    }
    private void createQuestion() {
        progressDialog.show();
        final String question = questionStatement.getText().toString().trim();
        final String opt1 = option1.getText().toString().trim();
        final String option2 = opt2.getText().toString().trim();
        final String opt3 =option3.getText().toString().trim();
        final String option4 = opt4.getText().toString().trim();
        final String answer = ans.getText().toString().trim();

        QuestionData data = new QuestionData("",question,opt1,option2,opt3,option4,answer,mquizid);
        ApiClient.getClient().create(ApiInterface.class).addquestion(data)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        if (!response.isSuccessful()){
                            Log.d(TAG, "onResponse: "+response.errorBody());
                            progressDialog.dismiss();
                        }
                        EmptyAllFields();
                        getquestionid();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                        progressDialog.dismiss();
                    }
                });
    }
    private void getquestionid() {
        ApiClient.getClient().create(ApiInterface.class).getquestionid(mquizid)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if (response.isSuccessful() && !response.body().isEmpty()) {
                            Object object = new Object();
                            ArrayList<QuestionData> data = new ArrayList<>();
                            data = response.body();

                            for (int i = 0; i < data.size(); i++) {
                                object = data.get(i);

                                Gson gson = new Gson();
                                String myJson = gson.toJson(object);
                                QuestionData questionData = gson.fromJson(myJson, QuestionData.class);
                                QuestionData qData = new QuestionData();

                                qData.setId(questionData.questionid);
                                questionid = qData.questionid.substring(0, 2);
                            }
                            addentry();
                        }
                        Log.d(TAG, "onResponse: it's empty");
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    }
                });
    }
    private void getquizid() {
        progressDialog.show();
        JobIdSession jxid = new JobIdSession(context);
        HashMap<String, String> Jobid = jxid.getJobid();
       String id = Jobid.get(JobIdSession.KEY_JID);

        ApiClient.getClient().create(ApiInterface.class).getquizid(id)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        ArrayList<QuizData> list = new ArrayList<>();

                        Object obj = new Object();
                        list = response.body();
                        for(int i =0;i<list.size();i++) {
                            obj = list.get(i);

                            Gson gson = new Gson();
                            String mystring = gson.toJson(obj);
                            QuizData data = gson.fromJson(mystring, QuizData.class);

                            QuizData quizData = new QuizData();
                            quizData.setQuizid(data.qid);
                            mquizid = quizData.qid.substring(0, 2);
                        }
                         progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                    }
                });
    }
    private boolean emptychk(){
        if(questionStatement.getText().toString().equals("")){
        questionStatement.requestFocus();
        questionStatement.setError("Enter question statement");
        return false;
    }
        if (option1.getText().toString().trim().isEmpty()){
            option1.setError("Add option A");
            option1.requestFocus();
            return false;
        }
        if(opt2.getText().toString().trim().isEmpty()){
            opt2.setError("Add option B");
            opt2.requestFocus();
            return false;
        }
        if (option3.getText().toString().trim().isEmpty()){
            option3.setError("Add option C");
            option3.requestFocus();
            return false;
        }
        if (opt4.getText().toString().trim().isEmpty()){
            opt4.setError("Add option D");
            opt4.requestFocus();
            return false;
        }
        if (ans.getText().toString().trim().isEmpty()){
            ans.setError("Add Answer");
            ans.requestFocus();
            return false;
        }
        return true;
    }
    private void initView() {
        toolbar = findViewById(R.id.toolbar_add_question);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Question");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        questionStatement = (EditText) findViewById(R.id.question_statement);
        option1 = (EditText) findViewById(R.id.option_1);
        opt2 = (EditText) findViewById(R.id.opt_2);
        option3 = (EditText) findViewById(R.id.option_3);
        opt4 = (EditText) findViewById(R.id.opt_4);
        ans = (EditText) findViewById(R.id.ans);
        addQuestionBtn = (Button) findViewById(R.id.add_question_btn);
    }
}