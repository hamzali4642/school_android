package com.qdocs.smartschool.adapters;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.qdocs.smartschool.students.StudentSyllabuslesson;
import com.qdocs.smartschool.utils.Constants;
import com.qdocs.smartschool.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StudentSyllabusStatusAdapter extends RecyclerView.Adapter<StudentSyllabusStatusAdapter.MyViewHolder> {

    private FragmentActivity context;
    private ArrayList<String> subject_nameList;
    private ArrayList<String> total_completeList;
    private ArrayList<String> classList;
    private ArrayList<String> idList;
    private ArrayList<String> subjectidList;
    private ArrayList<String> totalList;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();
    ArrayList<String> lessonidList = new ArrayList<String>();
    ArrayList<String> lesson_total_completeList = new ArrayList<String>();
    ArrayList<String> lesson_nameList = new ArrayList<String>();
    ArrayList<String> topicnameList = new ArrayList<String>();
    ArrayList<String> topicidList = new ArrayList<String>();
    ArrayList<String> statusList = new ArrayList<String>();
    StudentLessonTopicAdapter adapter;

    public StudentSyllabusStatusAdapter(FragmentActivity studentonlineexam, ArrayList<String> subject_nameList, ArrayList<String> total_completeList,
                                        ArrayList<String> idList ,ArrayList<String> subjectidList ,ArrayList<String> totalList) {

        this.context = studentonlineexam;
        this.subject_nameList = subject_nameList;
        this.total_completeList = total_completeList;
        this.idList = idList;
        this.subjectidList = subjectidList;
        this.totalList = totalList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject,status,count;
        LinearLayout lesson_topic;

        public MyViewHolder(View view) {
            super(view);
            subject = view.findViewById(R.id.adapter_student_subject_nameTV);
            status = view.findViewById(R.id.adapter_student_status_nameTV);
            lesson_topic = view.findViewById(R.id.adapter_student_lesson);
           // count = view.findViewById(R.id.count);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_syllabs_status, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.subject.setText(subject_nameList.get(position));
       // holder.count.setText(String.valueOf(position+1));

        if (totalList.get(position).equals("0")) {
            holder.status.setText(totalList.get(position)+"% Completed");
        }else {
            Float total_comp = Float.parseFloat(total_completeList.get(position));
            Float total = Float.parseFloat(totalList.get(position));
            Float complete = (total_comp / total);
            Float complete_per = (complete * 100);
            System.out.println("total_comp== " + total_comp + " total== " + total + " complete==" + complete + " complete_per==" + complete_per);
            holder.status.setText(String.valueOf(Math.round(complete_per)) + "% Completed");
        }
                holder.lesson_topic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context.getApplicationContext(), StudentSyllabuslesson.class);
                        intent.putExtra("SubjectList",subjectidList.get(position));
                        intent.putExtra("SectionIdlist",idList.get(position));
                        context.startActivity(intent);
                        //showAddDialog(subjectidList.get(position),idList.get(position));
                    }
                });
    }
    @Override
    public int getItemCount() {
        return idList.size();
    }

    private void showAddDialog(String subjectid,String sectionid) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.lesson_topic_dialoug);
        RelativeLayout headerLay = (RelativeLayout) dialog.findViewById(R.id.addTask_dialog_header);
        RecyclerView recyclerview = (RecyclerView) dialog.findViewById(R.id.recyclerview);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.addTask_dialog_crossIcon);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        params.put("subject_group_subject_id",subjectid);
        params.put("subject_group_class_sections_id",sectionid);
        JSONObject obj=new JSONObject(params);
        System.out.println("params=="+ obj.toString());
        Log.e("params ", obj.toString());
        getConsolidateDataFromApi(obj.toString());

        adapter = new StudentLessonTopicAdapter(context.getApplicationContext(),lesson_nameList,lesson_total_completeList,lessonidList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.primaryColour)));
        dialog.show();
    }

    private void getConsolidateDataFromApi (String bodyParams) {

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context.getApplicationContext(), "apiUrl")+Constants.getSubjectsLessonsUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        JSONArray dataArray = object.getJSONArray("lessons");

                        lesson_nameList.clear();
                        lesson_total_completeList.clear();
                        lessonidList.clear();

                        if(dataArray.length() != 0) {
                            for(int i = 0; i < dataArray.length(); i++) {
                                lesson_nameList.add(dataArray.getJSONObject(i).getString("name"));
                                lesson_total_completeList.add(dataArray.getJSONObject(i).getString("total_complete")+"% Completes");
                                lessonidList.add(dataArray.getJSONObject(i).getString("id"));
                               // JSONArray topicarray=dataArray.getJSONObject(i).getJSONArray("topic");

                              /*  if(dataArray.length() != 0) {
                                    for (int j = 0; j < topicarray.length(); j++) {
                                        topicnameList.add(topicarray.getJSONObject(j).getString("name"));
                                        topicidList.add(topicarray.getJSONObject(j).getString("id"));
                                        statusList.add(topicarray.getJSONObject(j).getString("status") + "% Completes");
                                    }
                                }else{
                                }*/
                            }
                            adapter.notifyDataSetChanged();
                        } else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(context.getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(context.getApplicationContext(), R.string.slowInternetMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(context.getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(context.getApplicationContext(), "accessToken"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext()); //Creating a Request Queue
        requestQueue.add(stringRequest);//Adding request to the queue
    }
}
