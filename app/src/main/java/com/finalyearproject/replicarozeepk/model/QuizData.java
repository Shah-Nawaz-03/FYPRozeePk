package com.finalyearproject.replicarozeepk.model;
public class QuizData {
    public String qid;
    public String jobid;
    public String date;

    public QuizData(String quizid, String jobid, String date) {
        this.qid = quizid;
        this.jobid = jobid;
        this.date = date;
    }
    public QuizData() {
    }
    public String getQuizid() {
        return qid;
    }

    public void setQuizid(String quizid) {
        this.qid = quizid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
