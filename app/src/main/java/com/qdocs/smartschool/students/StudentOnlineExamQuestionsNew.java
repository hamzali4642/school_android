package com.qdocs.smartschool.students;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.adapters.StudentQuestionsListAdapter;
import com.qdocs.smartschool.utils.Constants;
import com.qdocs.smartschool.utils.Utility;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StudentOnlineExamQuestionsNew extends AppCompatActivity {
    String Online_exam_id,durationList,onlineexam_student_idlist;
    public ImageView backBtn;
    public String defaultDateFormat, currency;
    RecyclerView recyclerView;
    boolean doubleBackToExitPressedOnce = false;
    StudentQuestionsListAdapter adapter;
    List<String> finallist = new ArrayList<String>();
    TextView name,sno;
    RadioButton option1,option2,option3,option4,option5;
    LinearLayout previous,next,submit,option5_layout;
    int position=1,hold;
    LinearLayout linear;
    public Map<String, String> params = new Hashtable<String, String>();
   // public Map<String, Integer> result_param = new HashMap<String, Integer>();
    JSONObject result_param=null;
   WebView questions,option_a_value,option_b_value,option_c_value,option_d_value,option_e_value;
    public Map<String,String> headers = new HashMap<String, String>();
    SwipeRefreshLayout pullToRefresh;
    public TextView titleTV,timer;
    protected FrameLayout mDrawerLayout;
    ArrayList<String> result_statusList = new ArrayList<String>();
    ArrayList<String> attempt_statusList = new ArrayList<String>();
    ArrayList<String> questionList = new ArrayList<String>();
    ArrayList<String> question_idList = new ArrayList<String>();
    ArrayList<String> onlineexam_idList = new ArrayList<String>();
    ArrayList<String> opt_aList = new ArrayList<String>();
    ArrayList<String> opt_bList = new ArrayList<String>();
    ArrayList<String> opt_cList = new ArrayList<String>();
    ArrayList<String> opt_dList = new ArrayList<String>();
    ArrayList<String> opt_eList = new ArrayList<String>();
    ArrayList<String> correctlist = new ArrayList<String>();
    JSONArray answerlist = new JSONArray();
    RadioGroup radiogroup;
    int TimeCounter=0;
    JSONArray dataArray=new JSONArray();
    int I=60;
    ImageView profileImageview;
    private long mTimeLeftInMillis;
    JSONArray dlist=new JSONArray();
    JSONArray ARRAYLIST=new JSONArray();
    String selected_answer;
    JSONObject jsonObject = null;
    JSONObject datanew = null;
    JSONObject newlist=null;
    String question_id;
    LinearLayout question_layout,nodata_layout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wise_layout_new);
        Online_exam_id = getIntent().getExtras().getString("Online_Exam_Id");
        durationList = getIntent().getExtras().getString("durationList");
        onlineexam_student_idlist = getIntent().getExtras().getString("onlineexam_student_idlist");
        profileImageview = findViewById(R.id.patientProfile_profileImageview);
        name = findViewById(R.id.name);
        submit = findViewById(R.id.submit);
        radiogroup = findViewById(R.id.radiogroup);
        sno = findViewById(R.id.sno);
        option5_layout = findViewById(R.id.option5_layout);
        questions = findViewById(R.id.questions);
        questions.getSettings().setJavaScriptEnabled(true);
        questions.getSettings().setBuiltInZoomControls(true);
        questions.getSettings().setLoadWithOverviewMode(true);
        questions.getSettings().setUseWideViewPort(true);
        questions.getSettings().setDefaultFontSize(40);

        option_a_value = findViewById(R.id.option_a_value);
        option_a_value.getSettings().setJavaScriptEnabled(true);
        option_a_value.getSettings().setBuiltInZoomControls(true);
        option_a_value.getSettings().setLoadWithOverviewMode(true);
        option_a_value.getSettings().setUseWideViewPort(true);
        option_a_value.getSettings().setDefaultFontSize(40);

        option_b_value = findViewById(R.id.option_b_value);
        option_b_value.getSettings().setJavaScriptEnabled(true);
        option_b_value.getSettings().setBuiltInZoomControls(true);
        option_b_value.getSettings().setLoadWithOverviewMode(true);
        option_b_value.getSettings().setUseWideViewPort(true);
        option_b_value.getSettings().setDefaultFontSize(40);

        option_c_value = findViewById(R.id.option_c_value);
        option_c_value.getSettings().setJavaScriptEnabled(true);
        option_c_value.getSettings().setBuiltInZoomControls(true);
        option_c_value.getSettings().setLoadWithOverviewMode(true);
        option_c_value.getSettings().setUseWideViewPort(true);
        option_c_value.getSettings().setDefaultFontSize(40);

        option_d_value = findViewById(R.id.option_d_value);
        option_d_value.getSettings().setJavaScriptEnabled(true);
        option_d_value.getSettings().setBuiltInZoomControls(true);
        option_d_value.getSettings().setLoadWithOverviewMode(true);
        option_d_value.getSettings().setUseWideViewPort(true);
        option_d_value.getSettings().setDefaultFontSize(40);

        option_e_value = findViewById(R.id.option_e_value);
        option_e_value.getSettings().setJavaScriptEnabled(true);
        option_e_value.getSettings().setBuiltInZoomControls(true);
        option_e_value.getSettings().setLoadWithOverviewMode(true);
        option_e_value.getSettings().setUseWideViewPort(true);
        option_e_value.getSettings().setDefaultFontSize(40);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option5 = findViewById(R.id.option5);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        timer = findViewById(R.id.timer);
        linear = findViewById(R.id.linear);
        question_layout = findViewById(R.id.question_layout);
        nodata_layout = findViewById(R.id.nodata_layout);
        timer.setText(String.valueOf(TimeCounter));
        String time =durationList;
        LocalTime localTime = LocalTime.parse(time);
        int millis = localTime.toSecondOfDay() * 1000;
        System.out.println("Time in Milliseconds : "+millis);
        mTimeLeftInMillis = millis;

        new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                System.out.println("%02d:%02d:%02d"+hms);
                timer.setText(hms);
            }
            public void onFinish() {
                dlist.put(jsonObject);
                System.out.println("dlist== " + dlist);
                Set<String> stationCodes=new HashSet<String>();
                JSONArray tempArray=new JSONArray();
                for(int i=0;i<dlist.length();i++){
                    try {
                        String stationCode = dlist.getJSONObject(i).getString("onlineexam_question_id");
                        System.out.println("stationCode== " + stationCode);
                        if(stationCodes.contains(stationCode)){
                            continue;

                        }
                        else{
                            stationCodes.add(stationCode);
                            tempArray.put(dlist.getJSONObject(i));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dlist= tempArray; //assign temp to original
                System.out.println("tempArray== " + tempArray);
                result_param=new JSONObject();
                try {
                    result_param.put("onlineexam_student_id", onlineexam_student_idlist);
                    result_param.put("rows",dlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // JSONObject obj=new JSONObject(result_param);
                System.out.println("Result==" + result_param.toString());
                submitExam(result_param.toString());
                finish();
            }
        }.start();

        defaultDateFormat = Utility.getSharedPreferences(getApplicationContext(), "dateFormat");
        currency = Utility.getSharedPreferences(getApplicationContext(), Constants.currency);
        decorate();
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));

        loaddata();

        if(sno.getText().toString().equals("1")){
            previous.setEnabled(false);
        }else{
            previous.setEnabled(true);
        }

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_answer = "opt_a";
               option2.setChecked(false);
               option3.setChecked(false);
               option4.setChecked(false);
               option5.setChecked(false);
                JSONArray jsonArray = new JSONArray();
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("onlineexam_student_id", onlineexam_student_idlist);
                    jsonObject.put("onlineexam_question_id",question_id);
                    jsonObject.put("select_option", selected_answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println("JSONArray== " + jsonArray.toString());

                try {
                    newlist=new JSONObject();
                    newlist.put("question",sno.getText().toString());
                    newlist.put("selected_answer",selected_answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("newlist==" + newlist.toString());

            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_answer = "opt_b";
                option1.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
                option5.setChecked(false);
                JSONArray jsonArray = new JSONArray();
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("onlineexam_student_id", onlineexam_student_idlist);
                    jsonObject.put("onlineexam_question_id",question_id);
                    jsonObject.put("select_option", selected_answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println("JSONArray==" + jsonArray.toString());

                try {
                    newlist=new JSONObject();
                    newlist.put("question",sno.getText().toString());
                    newlist.put("selected_answer",selected_answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("newlist==" + newlist.toString());

            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_answer = "opt_c";
                option2.setChecked(false);
                option1.setChecked(false);
                option4.setChecked(false);
                option5.setChecked(false);
                JSONArray jsonArray = new JSONArray();
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("onlineexam_student_id", onlineexam_student_idlist);
                    jsonObject.put("onlineexam_question_id",question_id);
                    jsonObject.put("select_option", selected_answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println("JSONArray== " + jsonArray.toString());

                try {
                    newlist=new JSONObject();
                    newlist.put("question",sno.getText().toString());
                    newlist.put("selected_answer",selected_answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("newlist==" + newlist.toString());
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_answer = "opt_d";
                option2.setChecked(false);
                option3.setChecked(false);
                option1.setChecked(false);
                option5.setChecked(false);
                JSONArray jsonArray = new JSONArray();
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("onlineexam_student_id", onlineexam_student_idlist);
                    jsonObject.put("onlineexam_question_id",question_id);
                    jsonObject.put("select_option", selected_answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println("JSONArray== " + jsonArray.toString());

                try {
                    newlist=new JSONObject();
                    newlist.put("question",sno.getText().toString());
                    newlist.put("selected_answer",selected_answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("newlist==" + newlist.toString());
            }
        });

        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_answer = "opt_e";
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
                option1.setChecked(false);
                JSONArray jsonArray = new JSONArray();
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("onlineexam_student_id", onlineexam_student_idlist);
                    jsonObject.put("onlineexam_question_id",question_id);
                    jsonObject.put("select_option", selected_answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println("JSONArray== " + jsonArray.toString());

                try {
                    newlist=new JSONObject();
                    newlist.put("question",sno.getText().toString());
                    newlist.put("selected_answer",selected_answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("newlist==" + newlist.toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(StudentOnlineExamQuestionsNew.this)
                        .setIcon(R.drawable.ic_access_time_black_24dp)
                        .setTitle("Submit")
                        .setMessage("Are you sure,you want to submit your exam?")
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dlist.put(jsonObject);
                                System.out.println("dlist== " + dlist);

                                Set<String> stationCodes=new HashSet<String>();
                                JSONArray tempArray=new JSONArray();
                                for(int i=0;i<dlist.length();i++){
                                    try {
                                        String stationCode = dlist.getJSONObject(i).getString("onlineexam_question_id");
                                        System.out.println("stationCode== " + stationCode);
                                        if(stationCodes.contains(stationCode)){
                                          continue;

                                        }
                                    else{
                                            stationCodes.add(stationCode);
                                            tempArray.put(dlist.getJSONObject(i));
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                dlist= tempArray; //assign temp to original
                                System.out.println("tempArray== " + tempArray);

                                result_param=new JSONObject();
                                try {
                                    result_param.put("onlineexam_student_id", onlineexam_student_idlist);
                                    result_param.put("rows",dlist);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("Result==" + result_param.toString());
                                submitExam(result_param.toString());
                                Intent intent=new Intent(StudentOnlineExamQuestionsNew.this,StudentOnlineExam.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radiogroup.getCheckedRadioButtonId() == -1) {
                } else {
                    dlist.put(jsonObject);
                }

                System.out.println("dlist== " + dlist.toString());
                JSONObject data=new JSONObject();
                try {
                    data.put("onlineexam_student_id", onlineexam_student_idlist);
                    data.put("rows",dlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("dataArray Size=="+dataArray.length());
                System.out.println("question Position=="+position+"  question hold=="+hold+"  selected answer=="+selected_answer);
                if(dataArray.length() != 0) {
                    if(hold >= 1){
                        sno.setText(""+hold);
                    }else{
                    }
                    if(hold==1){
                        previous.setEnabled(false);
                        next.setBackgroundColor(Color.parseColor("#B0DD38"));
                        previous.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                        next.setEnabled(true);
                    }else{
                        previous.setBackgroundColor(Color.parseColor("#B0DD38"));
                        previous.setEnabled(true);
                    }
                    if(dataArray.length()<=(hold)){
                        next.setEnabled(false);
                        next.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                    }else{
                        next.setEnabled(true);
                        next.setBackgroundColor(Color.parseColor("#B0DD38"));
                    }

                    if(position==dataArray.length()){
                        position--;
                    }else{

                    }
                    try {
                        questions.loadData(dataArray.getJSONObject(hold-1).getString("question"), "text/html; charset=utf-8", "UTF-8");
                        question_idList.add(dataArray.getJSONObject(hold-1).getString("question_id"));
                        onlineexam_idList.add(dataArray.getJSONObject(hold-1).getString("onlineexam_id"));


                        option_a_value.loadData(dataArray.getJSONObject(hold-1).getString("opt_a"), "text/html; charset=utf-8", "UTF-8");
                        option_b_value.loadData(dataArray.getJSONObject(hold-1).getString("opt_b"), "text/html; charset=utf-8", "UTF-8");
                        option_c_value.loadData(dataArray.getJSONObject(hold-1).getString("opt_c"), "text/html; charset=utf-8", "UTF-8");
                        option_d_value.loadData(dataArray.getJSONObject(hold-1).getString("opt_d"), "text/html; charset=utf-8", "UTF-8");

                        if(dataArray.getJSONObject(hold-1).getString("opt_e").equals("")){
                            option5_layout.setVisibility(View.GONE);
                        }else{
                            option5_layout.setVisibility(View.VISIBLE);
                            option_e_value.loadData(dataArray.getJSONObject(hold-1).getString("opt_e"), "text/html; charset=utf-8", "UTF-8");

                        }
                        correctlist.add(dataArray.getJSONObject(hold-1).getString("correct"));
                        question_id=dataArray.getJSONObject(hold-1).getString("id");

                        position=hold;
                        hold--;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < answerlist.length(); i++) {
                        try {
                            JSONObject  currObject = answerlist.getJSONObject(i);
                            String question = currObject.getString("question");
                             Integer serial= Integer.valueOf(sno.getText().toString());

                            if(Integer.valueOf(question) == (serial)) {
                                JSONObject prev_answer = currObject;
                                String myoption = prev_answer.getString("selected_answer");
                                if(myoption.equals("opt_a")){
                                    option1.setChecked(true);
                                    option2.setChecked(false);
                                    option3.setChecked(false);
                                    option4.setChecked(false);
                                    option5.setChecked(false);
                                }else if(myoption.equals("opt_b")){
                                    option2.setChecked(true);
                                    option1.setChecked(false);
                                    option3.setChecked(false);
                                    option4.setChecked(false);
                                    option5.setChecked(false);
                                }else if(myoption.equals("opt_c")){
                                    option3.setChecked(true);
                                    option2.setChecked(false);
                                    option1.setChecked(false);
                                    option4.setChecked(false);
                                    option5.setChecked(false);
                                }else if(myoption.equals("opt_d")){
                                    option4.setChecked(true);
                                    option2.setChecked(false);
                                    option3.setChecked(false);
                                    option1.setChecked(false);
                                    option5.setChecked(false);
                                }else if(myoption.equals("opt_e")){
                                    option5.setChecked(true);
                                    option2.setChecked(false);
                                    option3.setChecked(false);
                                    option4.setChecked(false);
                                    option1.setChecked(false);
                                }
                            }else{
                                System.out.println("hiiiiii....");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(StudentOnlineExamQuestionsNew.this, ""+sno.getText().toString(), Toast.LENGTH_SHORT).show();

                    dlist.put(jsonObject);
                    System.out.println("dlist== " + dlist.toString());

              option1.setChecked(false);
              option2.setChecked(false);
              option3.setChecked(false);
              option4.setChecked(false);
              option5.setChecked(false);

                    System.out.println("dataArray Size=="+dataArray.length());
                    System.out.println("question Position=="+position+"  question hold=="+hold);
                    if(dataArray.length() != 0) {
                        if(dataArray.length()>=(position+1)){
                            sno.setText(""+(position+1));
                        }else{
                        }
                        if(position>0){
                            previous.setEnabled(true);
                            previous.setBackgroundColor(Color.parseColor("#B0DD38"));

                        }else{
                            previous.setEnabled(false);
                            previous.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                        }

                        if(dataArray.length()<=(position+1)){
                            next.setEnabled(false);
                            next.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                        }else{
                            next.setEnabled(true);
                            next.setBackgroundColor(Color.parseColor("#B0DD38"));
                        }

                        try {
                            questions.loadData(dataArray.getJSONObject(position).getString("question"), "text/html; charset=utf-8", "UTF-8");
                            question_idList.add(dataArray.getJSONObject(position).getString("question_id"));
                            onlineexam_idList.add(dataArray.getJSONObject(position).getString("onlineexam_id"));

                            option_a_value.loadData(dataArray.getJSONObject(position).getString("opt_a"), "text/html; charset=utf-8", "UTF-8");
                            option_b_value.loadData(dataArray.getJSONObject(position).getString("opt_b"), "text/html; charset=utf-8", "UTF-8");
                            option_c_value.loadData(dataArray.getJSONObject(position).getString("opt_c"), "text/html; charset=utf-8", "UTF-8");
                            option_d_value.loadData(dataArray.getJSONObject(position).getString("opt_d"), "text/html; charset=utf-8", "UTF-8");

                            if(dataArray.getJSONObject(position).getString("opt_e").equals("")){
                                option5_layout.setVisibility(View.GONE);
                            }else{
                                option5_layout.setVisibility(View.VISIBLE);
                                option_e_value.loadData(dataArray.getJSONObject(position).getString("opt_e"), "text/html; charset=utf-8", "UTF-8");
                            }
                            correctlist.add(dataArray.getJSONObject(position).getString("correct"));
                            question_id=dataArray.getJSONObject(position).getString("id");
                           // Toast.makeText(StudentOnlineExamQuestionsNew.this, ""+question_id, Toast.LENGTH_SHORT).show();
                            hold=position;
                            position++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      answerlist.put(newlist);
                       System.out.println("answerlist=="+answerlist);

                        for (int i = 0; i < answerlist.length(); i++) {
                            try {
                                JSONObject  currObject = answerlist.getJSONObject(i);
                                String question = currObject.getString("question");
                                Integer serial= Integer.valueOf(sno.getText().toString());

                                if(Integer.valueOf(question) == (serial)) {
                                    JSONObject prev_answer = currObject;
                                    String myoption = prev_answer.getString("selected_answer");
                                    System.out.println("hello hii ......  "+myoption);
                                    if(myoption.equals("opt_a")){
                                        option1.setChecked(true);
                                        option2.setChecked(false);
                                        option3.setChecked(false);
                                        option4.setChecked(false);
                                        option5.setChecked(false);
                                    }else if(myoption.equals("opt_b")){
                                        option2.setChecked(true);
                                        option1.setChecked(false);
                                        option3.setChecked(false);
                                        option4.setChecked(false);
                                        option5.setChecked(false);
                                    }else if(myoption.equals("opt_c")){
                                        option3.setChecked(true);
                                        option2.setChecked(false);
                                        option1.setChecked(false);
                                        option4.setChecked(false);
                                        option5.setChecked(false);
                                    }else if(myoption.equals("opt_d")){
                                        option4.setChecked(true);
                                        option2.setChecked(false);
                                        option3.setChecked(false);
                                        option1.setChecked(false);
                                        option5.setChecked(false);
                                    }else if(myoption.equals("opt_e")){
                                        option5.setChecked(true);
                                        option2.setChecked(false);
                                        option3.setChecked(false);
                                        option4.setChecked(false);
                                        option1.setChecked(false);
                                    }
                                }else{
                                    System.out.println("hiiiiii....");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void decorate() {
        String appLogo = Utility.getSharedPreferences(this, Constants.appLogo)+"?"+new Random().nextInt(11);
        Picasso.with(getApplicationContext()).load(appLogo).fit().centerInside().placeholder(null).into(profileImageview);
       // Picasso.with(getApplicationContext()).load(Utility.getSharedPreferences(this, "userImage")).placeholder(R.drawable.placeholder_user).into(profileImageview);
        linear.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        }
    }
    public  void  loaddata(){
         params.put("student_id", Utility.getSharedPreferences(getApplicationContext(), Constants.studentId));
         params.put("online_exam_id",Online_exam_id);
        JSONObject obj=new JSONObject(params);
        Log.e("params ", obj.toString());
        getDataFromApi(obj.toString());
    }

    private void submitExam (String bodyParams) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        if(pd != null && pd.isShowing()){
            pd.show();}

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.saveOnlineExamUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Exam Questions", result);
                        System.out.println("Hiii........"+result);
                        JSONObject obj = new JSONObject(result);

                        Toast.makeText(StudentOnlineExamQuestionsNew.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(StudentOnlineExamQuestionsNew.this, R.string.slowInternetMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(getApplicationContext(), "accessToken"));
                Log.e("Headers", headers.toString());
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(StudentOnlineExamQuestionsNew.this); //Creating a Request Queue
        requestQueue.add(stringRequest);  //Adding request to the queue
    }

    private void getDataFromApi (String bodyParams) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.getOnlineExamQuestionUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Exam Questions", result);
                        JSONObject obj = new JSONObject(result);
                        JSONObject dataObject = obj.getJSONObject("exam");
                        question_idList.clear();
                        onlineexam_idList.clear();
                        name.setText(dataObject.getString("exam"));
                        JSONObject statusObject = dataObject.getJSONObject("status");
                        result_statusList.add(statusObject.getString("exam_result_publish_status"));
                        attempt_statusList.add(statusObject.getString("exam_attempt_status"));

                         dataArray = dataObject.getJSONArray("questions");
                         System.out.println("dataArray.length()=="+dataArray.length());
                        if(dataArray.length()>1) {
                            question_layout.setVisibility(View.VISIBLE);
                            nodata_layout.setVisibility(View.GONE);
                            sno.setText("1");

                            questions.loadData(dataArray.getJSONObject(0).getString("question"), "text/html; charset=utf-8", "UTF-8");
                            question_idList.add(dataArray.getJSONObject(0).getString("id"));
                            onlineexam_idList.add(dataArray.getJSONObject(0).getString("onlineexam_id"));
                            option_a_value.loadData(dataArray.getJSONObject(0).getString("opt_a"), "text/html; charset=utf-8", "UTF-8");
                            option_b_value.loadData(dataArray.getJSONObject(0).getString("opt_b"), "text/html; charset=utf-8", "UTF-8");
                            option_c_value.loadData(dataArray.getJSONObject(0).getString("opt_c"), "text/html; charset=utf-8", "UTF-8");
                            option_d_value.loadData(dataArray.getJSONObject(0).getString("opt_d"), "text/html; charset=utf-8", "UTF-8");


                            if(dataArray.getJSONObject(0).getString("opt_e").equals("")){
                                option5_layout.setVisibility(View.GONE);

                            }else{
                                option5_layout.setVisibility(View.VISIBLE);
                                option_e_value.loadData(dataArray.getJSONObject(0).getString("opt_e"), "text/html; charset=utf-8", "UTF-8");
                            }
                            correctlist.add(dataArray.getJSONObject(0).getString("correct"));
                            question_id=dataArray.getJSONObject(0).getString("id");
                         //   Toast.makeText(StudentOnlineExamQuestionsNew.this, ""+question_id, Toast.LENGTH_SHORT).show();
                        } else if(dataArray.length()==1){
                            question_layout.setVisibility(View.VISIBLE);
                            nodata_layout.setVisibility(View.GONE);
                            next.setEnabled(false);
                            next.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                            previous.setBackgroundColor(Color.parseColor("#D8D6D3D3"));
                            previous.setEnabled(false);
                            sno.setText("1");

                            questions.loadData(dataArray.getJSONObject(0).getString("question"), "text/html; charset=utf-8", "UTF-8");
                            question_idList.add(dataArray.getJSONObject(0).getString("id"));
                            onlineexam_idList.add(dataArray.getJSONObject(0).getString("onlineexam_id"));


                            option_a_value.loadData(dataArray.getJSONObject(0).getString("opt_a"), "text/html; charset=utf-8", "UTF-8");
                            option_b_value.loadData(dataArray.getJSONObject(0).getString("opt_b"), "text/html; charset=utf-8", "UTF-8");
                            option_c_value.loadData(dataArray.getJSONObject(0).getString("opt_c"), "text/html; charset=utf-8", "UTF-8");
                            option_d_value.loadData(dataArray.getJSONObject(0).getString("opt_d"), "text/html; charset=utf-8", "UTF-8");

                            if(dataArray.getJSONObject(0).getString("opt_e").equals("")){
                                option5_layout.setVisibility(View.GONE);
                            }else{
                                option5_layout.setVisibility(View.VISIBLE);
                                option_e_value.loadData(dataArray.getJSONObject(0).getString("opt_e"), "text/html; charset=utf-8", "UTF-8");
                            }
                            correctlist.add(dataArray.getJSONObject(0).getString("correct"));
                            question_id=dataArray.getJSONObject(0).getString("id");
                           //Toast.makeText(StudentOnlineExamQuestionsNew.this, ""+question_id, Toast.LENGTH_SHORT).show();
                        }else {
                            nodata_layout.setVisibility(View.VISIBLE);
                            question_layout.setVisibility(View.GONE);
                            next.setEnabled(false);
                            submit.setEnabled(false);
                            previous.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(StudentOnlineExamQuestionsNew.this, R.string.slowInternetMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(getApplicationContext(), "accessToken"));
                Log.e("Headers", headers.toString());
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(StudentOnlineExamQuestionsNew.this); //Creating a Request Queue
        requestQueue.add(stringRequest);  //Adding request to the queue
    }
}
