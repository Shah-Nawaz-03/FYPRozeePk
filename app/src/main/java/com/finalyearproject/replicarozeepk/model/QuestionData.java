package com.finalyearproject.replicarozeepk.model;
public class QuestionData {
        public String questionid;
        public String questionstatement;
        public String opt1;
        public String opt2;
        public String opt3;
        public String opt4;
        public String correct;
        public String qid;

    public QuestionData() {
    }
    public QuestionData(String id, String questionstatement, String option1, String option2, String option3, String option4, String correct, String quizid) {
        this.questionid = id;
        this.questionstatement = questionstatement;
        opt1 = option1;
        opt2 = option2;
        opt3 = option3;
        opt4 = option4;
        this.correct = correct;
        this.qid = quizid;
    }
    @Override
    public String toString() {
        return "QuestionData{" +
                "id='" + questionid + '\'' +
                ", questionstatement='" + questionstatement + '\'' +
                ", option1='" + opt1 + '\'' +
                ", option2='" + opt2 + '\'' +
                ", option3='" + opt3 + '\'' +
                ", option4='" + opt4 + '\'' +
                ", correct='" + correct + '\'' +
                '}';
    }
    public String getId() {
        return questionid;
    }

    public void setId(String id) {
        this.questionid = id;
    }

    public String getQuestionstatement() {
        return questionstatement;
    }

    public void setQuestionstatement(String questionstatement) {
        this.questionstatement = questionstatement;
    }
    public String getOption1() {
        return opt1;
    }

    public void setOption1(String option1) {
        opt1 = option1;
    }

    public String getOption2() {
        return opt2;
    }

    public void setOption2(String option2) {
        opt2 = option2;
    }

    public String getOption3() {
        return opt3;
    }

    public void setOption3(String option3) {
        opt3 = option3;
    }

    public String getOption4() {
        return opt4;
    }

    public void setOption4(String option4) {
        opt4 = option4;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getQuizid() {
        return qid;
    }

    public void setQuizid(String quizid){
        this.qid= quizid;
    }
}
