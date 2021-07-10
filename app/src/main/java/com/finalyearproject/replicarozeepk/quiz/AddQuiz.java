package com.finalyearproject.replicarozeepk.quiz;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.Sessions.JobIdSession;
import com.finalyearproject.replicarozeepk.model.QuestionData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddQuiz extends AppCompatActivity {

    private static final String TAG = "AddQuiz";
    private Context context;
    private RecyclerView quizView;
    private Toolbar toolbar;
    private String id;
    private ArrayList<QuestionData> dataArrayList;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        context = this;
        JobIdSession jxid = new JobIdSession(context);
        HashMap<String, String> Jobid = jxid.getJobid();
        id = Jobid.get(JobIdSession.KEY_JID);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing...");

        initView();
        showQuiz();
    }
    private void showQuiz() {
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).showquiz(id)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if (response.isSuccessful() && !response.body().isEmpty()){

                            Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();

//                            ArrayList<QuestionData> data = new ArrayList<>();
//                            data = response.body();
//                            Object object = new Object();
//
//                            for (int i = 0;i < data.size();i++){
//                                object = data.get(i);
//
//                                Gson gson = new Gson();
//                                String myJson = gson.toJson(object);
//                                QuestionData qd  = gson.fromJson(myJson,QuestionData.class);
//
//                                QuestionData questionData = new QuestionData();
//
//                                questionData.setQuestionstatement(qd.questionstatement);
//                                questionData.setOption1(qd.opt1);
//                                questionData.setOption2(qd.opt2);
//                                questionData.setOption3(qd.opt3);
//                                questionData.setOption4(qd.opt4);
//                                questionData.setCorrect(qd.correct);
//
//                                dataArrayList.add(questionData);
//                            }
//
//                            ShowQuizAdapter adapter = new ShowQuizAdapter(context, dataArrayList);
//                            adapter.notifyDataSetChanged();
//                            quizView.setAdapter(adapter);
                            progressDialog.dismiss();

                        }else{
                            Toast.makeText(context, "There is some issue", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                    }
                });
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar_quiz_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quizView = (RecyclerView) findViewById(R.id.quiz_view);
        quizView.setLayoutManager(new LinearLayoutManager(context));
        quizView.setHasFixedSize(true);
        quizView.hasFixedSize();
    }
}