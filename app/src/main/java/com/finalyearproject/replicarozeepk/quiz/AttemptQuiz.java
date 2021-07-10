package com.finalyearproject.replicarozeepk.quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyearproject.replicarozeepk.Adapters.ShowQuizAdapter;
import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.QuestionData;
import com.finalyearproject.replicarozeepk.retrofit.ApiClient;
import com.finalyearproject.replicarozeepk.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AttemptQuiz extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressDialog dialog;
    public List<QuestionData> dataArrayList;
    private String id;
    private RecyclerView questions;
    private Context context;
    public static final long COUNTDOWN_IN_MILLIS = 31000;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;
    private TextView timeDown;
    private int quizScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_quiz);
        context = this;
        dialog = new ProgressDialog(context);
        dialog.setTitle("Processing...");
        toolbar = findViewById(R.id.toolbar_attempt_quiz);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Attempt Quiz");
        getSupportActionBar().setHomeButtonEnabled(false);
        dataArrayList = new ArrayList<>();
        timeDown = findViewById(R.id.time_down);

        questions = findViewById(R.id.questions);
        questions.hasFixedSize();
        questions.setLayoutManager(new LinearLayoutManager(context));

        id = getIntent().getStringExtra("jjobid");
        timeLeftinMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
        dialog.show();
        ApiClient.getClient().create(ApiInterface.class).showquiz(id)
                .enqueue(new Callback<ArrayList>() {
                    @Override
                    public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                        if (response.isSuccessful() && !response.body().isEmpty()) {
                            ArrayList<QuizQuestions> data = new ArrayList<>();
                            data = response.body();
                            Object object = new Object();

                            for (int i = 0;i < data.size(); i++){
                                object = data.get(i);

                                Gson gson = new Gson();
                                String myJson = gson.toJson(object);
                                QuestionData questionData = gson.fromJson(myJson, QuestionData.class);

                                QuestionData qd = new QuestionData();
                                qd.setQuestionstatement(questionData.questionstatement);
                                qd.setOption1(questionData.opt1);
                                qd.setOption2(questionData.opt2);
                                qd.setOption3(questionData.opt3);
                                qd.setOption4(questionData.opt4);
                                qd.setCorrect(questionData.correct);



                               dataArrayList.add(qd);
                            }
                            ShowQuizAdapter adapter = new ShowQuizAdapter(context,dataArrayList);
                            questions.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ArrayList> call, Throwable t) {

                        dialog.dismiss();
                        Toast.makeText(context, "on Failure"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftinMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftinMillis = l;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timeLeftinMillis = 0;
                updateCountDownText();
                View v = LayoutInflater.from(context)
                        .inflate(R.layout.quizattemptd,null);
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(v)
                        .create();
                dialog.show();
                dialog.setCancelable(false);

                TextView ok = v.findViewById(R.id.ok_done);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

            }
        }.start();
    }

    private void updateCountDownText() {
    int minutes =(int)(timeLeftinMillis / 1000) / 60;
    int seconds = (int) (timeLeftinMillis / 1000) % 60;

    String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
    timeDown.setText(timeFormatted);
    }
}