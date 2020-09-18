package com.qdocs.smartschool.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.students.StudentSyllabus;
import com.qdocs.smartschool.students.StudentSyllabusTimetable;
import java.util.ArrayList;

public class StudentSyllabusTimetableAdapter extends RecyclerView.Adapter<StudentSyllabusTimetableAdapter.MyViewHolder> {

    private StudentSyllabusTimetable context;
    private ArrayList<String> timeList;
    private ArrayList<String> subjectList;
    private ArrayList<String> roomNoList;
    private ArrayList<String> Subjectid;
    private ArrayList<String> Sectionid;
    private ArrayList<String> timefrom;
    private ArrayList<String> timeto;
    private String date;

    public StudentSyllabusTimetableAdapter(StudentSyllabusTimetable studentClassTimetable, ArrayList<String> Subject, ArrayList<String> mondayTime, ArrayList<String> mondayRoomNo, ArrayList<String> Subjectid
            , ArrayList<String>Sectionid, ArrayList<String> timefrom, ArrayList<String> timeto, String date) {

        this.context = studentClassTimetable;
        this.timeList = mondayTime;
        this.subjectList =Subject;
        this.roomNoList = mondayRoomNo;
        this.Subjectid = Subjectid;
        this.Sectionid = Sectionid;
        this.timefrom =timefrom;
        this.timeto = timeto;
        this.date = date;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView timeTV, subjectTV;
        LinearLayout headlayout,syllabusTV;
        public MyViewHolder(View view) {
            super(view);

            timeTV = (TextView) view.findViewById(R.id.adapter_student_classTimetable_timeTV);
            subjectTV = (TextView) view.findViewById(R.id.adapter_student_classTimetable_subjectTV);
            syllabusTV = (LinearLayout) view.findViewById(R.id.adapter_student_classTimetable_syllabusTV);
            headlayout = (LinearLayout) view.findViewById(R.id.headlayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_lessonplan, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.timeTV.setText(timeList.get(position));
        holder.subjectTV.setText(subjectList.get(position));
        holder.headlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(), StudentSyllabus.class);
                intent.putExtra("Subjectid",Subjectid.get(position));
                intent.putExtra("Sectionid",Sectionid.get(position));
                intent.putExtra("timefrom",timefrom.get(position));
                intent.putExtra("timeto",timeto.get(position));
                intent.putExtra("date",date);
                intent.putExtra("time",timeList.get(position));
                intent.putExtra("subject",holder.subjectTV.getText().toString());
              //  intent.putExtra("date",date);
                context.startActivity(intent);
                System.out.println("Subjectid== "+Subjectid.get(position));
                System.out.println("Sectionid== "+Sectionid.get(position));
                System.out.println("timefrom== "+timefrom.get(position));
                System.out.println("timeto== "+timeto.get(position));
                System.out.println("mydate==  "+date);

            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

}

