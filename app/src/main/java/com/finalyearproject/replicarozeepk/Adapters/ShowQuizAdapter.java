package com.finalyearproject.replicarozeepk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.finalyearproject.replicarozeepk.R;
import com.finalyearproject.replicarozeepk.model.QuestionData;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;
public class ShowQuizAdapter extends Adapter<ShowQuizAdapter.ViewHolder>{

    public int Score = 0, SelectedId;
    public Context context;
    public List<QuestionData> data;
    public ShowQuizAdapter(Context context, List<QuestionData> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quizrow_emp,
                parent,false);
        return new ViewHolder(view, context);
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {


        QuestionData questionData = data.get(position);
        holder.statement.setText(MessageFormat.format("Question: {0}",questionData.questionstatement));
        holder.opt1.setText(MessageFormat.format("option A: {0}",questionData.opt1));
        holder.opt2.setText(MessageFormat.format("option B: {0}",questionData.opt2));
        holder.opt3.setText(MessageFormat.format("option C: {0}",questionData.opt3));
        holder.opt4.setText(MessageFormat.format("option D: {0}",questionData.opt4));;
        holder.answer = questionData.correct;

        int selectedId = holder.group.getCheckedRadioButtonId();



    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public RadioButton opt1,opt2,opt3,opt4,check;
        public RadioGroup group;
        public String answer;
        public TextView statement;
        public ViewHolder(@NonNull @NotNull View itemView, Context context1) {
            super(itemView);
            context = context1;
            statement = itemView.findViewById(R.id.question_emp);
            group = itemView.findViewById(R.id.group);
            opt1 = itemView.findViewById(R.id.opt1_emp);
            opt2 = itemView.findViewById(R.id.opt2_emp);
            opt3 = itemView.findViewById(R.id.opt3_emp);
            opt4 = itemView.findViewById(R.id.opt4_emp);
        }
    }
}
