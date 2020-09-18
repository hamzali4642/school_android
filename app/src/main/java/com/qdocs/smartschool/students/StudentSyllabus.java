package com.qdocs.smartschool.students;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
import com.qdocs.smartschool.BaseActivity;
import com.qdocs.smartschool.OpenPdf;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.adapters.StudentLessonTopicAdapter;
import com.qdocs.smartschool.adapters.StudentSyllabusStatusAdapter;
import com.qdocs.smartschool.utils.Constants;
import com.qdocs.smartschool.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StudentSyllabus extends BaseActivity {
    RecyclerView recyclerView;
    LinearLayout nodata_layout;
    SwipeRefreshLayout pullToRefresh;
    ArrayList<String> lacture_videolist = new ArrayList<String>();
    ArrayList<String> lacture_youtube_urllist = new ArrayList<String>();
    String subjectid,sectionid,timefrom,timeto,date,time,subjects;
    public Map<String, String>  headers = new HashMap<String, String>();
    public Map<String, String> params = new Hashtable<String, String>();
    TextView classes,subject,dates,lesson,topic,subtopic,generalobjective,teachingMethod,previousknowledge,comprehensive;
    StudentSyllabusStatusAdapter adapter;
    String formatedDate = "";
    LinearLayout data,nodata,presentation;
    String youtube,attachment,lacture_video,pesentation_link;
    ImageView youtubeurl,download_attachment,download_video;
    long downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_student_syllabus, null, false);
        mDrawerLayout.addView(contentView, 0);

        classes=findViewById(R.id.classes);
        classes.setText(Utility.getSharedPreferences(this, Constants.classSection));
        subject=findViewById(R.id.subject);
        dates=findViewById(R.id.dates);
        presentation=findViewById(R.id.presentation);
        presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(StudentSyllabus.this, pesentation_link, Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(StudentSyllabus.this);
                dialog.setContentView(R.layout.presentation_sheet);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                final ProgressDialog progressDialog = new ProgressDialog(StudentSyllabus.this);
                progressDialog.setMessage("Loading Data...");
                progressDialog.setCancelable(false);
                TextView headerTV = dialog.findViewById(R.id.homework_bottomSheet_headerTV);
                ImageView closeBtn = dialog.findViewById(R.id.homework_bottomSheet_crossBtn);
                WebView detailsWebview = dialog.findViewById(R.id.homework_bottomSheet_webview);
                detailsWebview.getSettings().setJavaScriptEnabled(true);
                detailsWebview.getSettings().setBuiltInZoomControls(true);
                detailsWebview.getSettings().setLoadWithOverviewMode(true);
                detailsWebview.getSettings().setUseWideViewPort(true);
                detailsWebview.getSettings().setDefaultFontSize(50);
                detailsWebview.loadData(pesentation_link, "text/html; charset=utf-8", "UTF-8");
                headerTV.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(StudentSyllabus.this, Constants.secondaryColour)));
                headerTV.setText(getString(R.string.presentation));
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                detailsWebview.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100) {
                            progressDialog.show();
                        }
                        if (progress == 100) {
                            progressDialog.dismiss();
                        }
                    } });
                dialog.show();

            }
        });

        youtubeurl=findViewById(R.id.youtubeurl);
       
        download_attachment=findViewById(R.id.download_attachment);
       
        download_video=findViewById(R.id.download_video);

        lesson=findViewById(R.id.lesson);
        topic=findViewById(R.id.topic);
        nodata=findViewById(R.id.nodata);

        data=findViewById(R.id.data);
        subtopic=findViewById(R.id.subtopic);
        generalobjective=findViewById(R.id.generalobjective);
        teachingMethod=findViewById(R.id.teachingMethod);
        previousknowledge=findViewById(R.id.previousknowledge);
        comprehensive=findViewById(R.id.comprehensive);

        subjectid = getIntent().getStringExtra("Subjectid");
        sectionid = getIntent().getStringExtra("Sectionid");
        timefrom = getIntent().getStringExtra("timefrom");
        timeto = getIntent().getStringExtra("timeto");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        subjects = getIntent().getStringExtra("subject");
        subject.setText(subjects);
        dates.setText(date+" "+time);

        String mStringDate = date;
        String oldFormat= "MM/dd/yyyy";
        String newFormat= "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mStringDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);

        titleTV.setText(getApplicationContext().getString(R.string.lessonplan));
       loaddata();
    }

    public  void  loaddata(){
        params.put("subject_group_subject_id",subjectid);
        params.put("subject_group_class_sections_id",sectionid);
        params.put("time_from",timefrom);
        params.put("time_to",timeto);
        params.put("date",formatedDate);
        JSONObject object=new JSONObject(params);
        Log.e("params ", object.toString());
        System.out.println("Syllabus Data== "+object.toString());
        System.out.println("formats1== "+formatedDate);
        getDataFromApi(object.toString());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loaddata();
    }
    public BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.notification_logo)
                                .setContentTitle(context.getApplicationContext().getString(R.string.app_name))
                                .setContentText("All Download completed");
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());
                context.unregisterReceiver(onDownloadComplete);
            }
        }
    };

    private void getDataFromApi (String bodyParams) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.getsyllabusUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                //pullToRefresh.setRefreshing(false);
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        System.out.print("Syllabus data result=="+result);
                        JSONObject obj = new JSONObject(result);
                        JSONArray dataArray = obj.getJSONArray("data");
                        System.out.print("Syllabus data result Length=="+dataArray.length());
                        if(dataArray.length() != 0) {
                                lesson.setText(dataArray.getJSONObject(0).getString("lesson_name"));
                                topic.setText(dataArray.getJSONObject(0).getString("topic_name"));
                                subtopic.setText(dataArray.getJSONObject(0).getString("sub_topic"));
                                generalobjective.setText(dataArray.getJSONObject(0).getString("general_objectives"));
                                teachingMethod.setText(dataArray.getJSONObject(0).getString("teaching_method"));
                                previousknowledge.setText(dataArray.getJSONObject(0).getString("previous_knowledge"));
                                comprehensive.setText(dataArray.getJSONObject(0).getString("comprehensive_questions"));
                                pesentation_link=dataArray.getJSONObject(0).getString("presentation");
                                lacture_videolist.add(dataArray.getJSONObject(0).getString("lacture_video"));
                                lacture_videolist.add(dataArray.getJSONObject(0).getString("attachment"));

                            youtube=  dataArray.getJSONObject(0).getString("lacture_youtube_url");
                            if(youtube.equals("")){
                                youtubeurl.setVisibility(View.GONE);
                            }else{
                                youtubeurl.setVisibility(View.VISIBLE);
                                youtubeurl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setPackage("com.google.android.youtube");
                                        startActivity(intent);
                                    }
                                });  
                            }
                            
                            attachment=  dataArray.getJSONObject(0).getString("attachment");
                            if(attachment.equals("")){
                                download_attachment.setVisibility(View.GONE);
                            }else{
                                download_attachment.setVisibility(View.VISIBLE);
                                download_attachment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String urlStr = Utility.getSharedPreferences(StudentSyllabus.this, Constants.imagesUrl);
                                        urlStr += "uploads/syllabus_attachment/"+attachment;
                                        downloadID = Utility.beginDownload(StudentSyllabus.this, attachment, urlStr);
                                        Intent intent=new Intent(StudentSyllabus.this, OpenPdf.class);
                                        intent.putExtra("imageUrl",urlStr);
                                        startActivity(intent);
                                    }
                                });
                            }
                            
                            lacture_video=  dataArray.getJSONObject(0).getString("lacture_video");
                            if(lacture_video.equals("")){
                                download_video.setVisibility(View.GONE);
                            }else{
                                download_video.setVisibility(View.VISIBLE);
                                download_video.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String urlStr = Utility.getSharedPreferences(StudentSyllabus.this, Constants.imagesUrl);
                                        urlStr += "uploads/syllabus_attachment/lacture_video/"+lacture_video;
                                        downloadID = Utility.beginDownload(StudentSyllabus.this, lacture_video, urlStr);
                                        Intent intent=new Intent(StudentSyllabus.this, OpenPdf.class);
                                        intent.putExtra("imageUrl",urlStr);
                                        startActivity(intent);
                                    }
                                });
                            }
                        } else {
                          //  pullToRefresh.setVisibility(View.GONE);
                           nodata.setVisibility(View.VISIBLE);
                           data.setVisibility(View.GONE);
                            //Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.noData), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(StudentSyllabus.this, R.string.slowInternetMsg, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(StudentSyllabus.this); //Creating a Request Queue
        requestQueue.add(stringRequest);  //Adding request to the queue
    }
}
