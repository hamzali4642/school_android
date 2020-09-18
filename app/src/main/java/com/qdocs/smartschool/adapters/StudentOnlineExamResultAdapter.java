package com.qdocs.smartschool.adapters;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.students.StudentOnlineExamQuestionsNew;
import com.qdocs.smartschool.students.StudentOnlineExamResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StudentOnlineExamResultAdapter extends RecyclerView.Adapter<StudentOnlineExamResultAdapter.MyViewHolder> {

    private FragmentActivity context;
    private ArrayList<String> questionlist;
    private ArrayList<String> subject_namelist;
    private ArrayList<String> select_optionlist;
    private ArrayList<String> correctlist;
    private ArrayList<String> idlist;
    private ArrayList<String> option_a;
    private ArrayList<String> option_b;
    private ArrayList<String> option_c;
    private ArrayList<String> option_d;
    private ArrayList<String> option_e;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();

    public StudentOnlineExamResultAdapter(FragmentActivity studentonlineexam, ArrayList<String> questionlist, ArrayList<String> subject_namelist,
                                          ArrayList<String> select_optionlist, ArrayList<String> correctlist, ArrayList<String> idlist,
                                          ArrayList<String> option_a, ArrayList<String> option_b, ArrayList<String> option_c, ArrayList<String> option_d, ArrayList<String> option_e){
        this.context = studentonlineexam;
        this.questionlist = questionlist;
        this.subject_namelist = subject_namelist;
        this.select_optionlist = select_optionlist;
        this.correctlist = correctlist;
        this.idlist = idlist;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.option_e = option_e;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject,answer,attempted,duration,status;
        ImageView wrong,right;
        WebView question,Correct;

        public MyViewHolder(View view) {
            super(view);

            question = view.findViewById(R.id.question);
            question.getSettings().setJavaScriptEnabled(true);
            question.getSettings().setBuiltInZoomControls(true);
            question.getSettings().setLoadWithOverviewMode(true);
            question.getSettings().setUseWideViewPort(true);
            question.getSettings().setDefaultFontSize(40);

            subject = view.findViewById(R.id.subject);
            answer = view.findViewById(R.id.answer);

            Correct = view.findViewById(R.id.Correct);
            Correct.getSettings().setJavaScriptEnabled(true);
            Correct.getSettings().setBuiltInZoomControls(true);
            Correct.getSettings().setLoadWithOverviewMode(true);
            Correct.getSettings().setUseWideViewPort(true);
            Correct.getSettings().setDefaultFontSize(37);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_exam_result, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.question.loadData(questionlist.get(position), "text/html; charset=utf-8", "UTF-8");
        holder.subject.setText(subject_namelist.get(position));
       // holder.Correct.setText(correctlist.get(position));

        if(correctlist.get(position).equals("opt_a")){
            holder.Correct.loadData(option_a.get(position), "text/html; charset=utf-8", "UTF-8");
        }else if(correctlist.get(position).equals("opt_b")){
            holder.Correct.loadData(option_b.get(position), "text/html; charset=utf-8", "UTF-8");
        }else if(correctlist.get(position).equals("opt_c")){
            holder.Correct.loadData(option_c.get(position), "text/html; charset=utf-8", "UTF-8");
        }else if(correctlist.get(position).equals("opt_d")){
            holder.Correct.loadData(option_d.get(position), "text/html; charset=utf-8", "UTF-8");
        }else if(correctlist.get(position).equals("opt_e")){
            holder.Correct.loadData(option_e.get(position), "text/html; charset=utf-8", "UTF-8");
        }

       if(select_optionlist.get(position).equals("null") || select_optionlist.get(position).equals("")){
           holder.answer.setText(R.string.not_attempt);
           holder.answer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yellow_border));
       }else{
           if(select_optionlist.get(position).equals(correctlist.get(position))){
               holder.answer.setText(R.string.correct);
               holder.answer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_border));
           }else{
               holder.answer.setText(R.string.incorrect);
               holder.answer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_border));}
       }
    }

    @Override
    public int getItemCount() {
        return questionlist.size();
    }
}
