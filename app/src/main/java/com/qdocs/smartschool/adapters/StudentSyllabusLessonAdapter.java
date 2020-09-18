package com.qdocs.smartschool.adapters;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.students.StudentSyllabuslesson;
import com.qdocs.smartschool.utils.Constants;
import com.qdocs.smartschool.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StudentSyllabusLessonAdapter extends BaseAdapter {

    private StudentSyllabuslesson context;
    private ArrayList<String> NameList;
    private ArrayList<String> total_completeList;
    private ArrayList<String> totalList;
    ArrayList<String> topicArray;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String>  headers = new HashMap<String, String>();
    public StudentSyllabusLessonAdapter(StudentSyllabuslesson studentTransportRoutes,
                                        ArrayList<String> NameList, ArrayList<String> topicArray,ArrayList<String> total_completeList,ArrayList<String> totalList) {

        this.context = studentTransportRoutes;
        this.NameList = NameList;
        this.total_completeList = total_completeList;
        this.totalList = totalList;
        this.topicArray = topicArray;
    }
    @Override
    public int getCount() {
        return NameList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        String defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext().getApplicationContext(), "dateFormat");
        if(null==view) {

            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.adapter_student_lesson, viewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.nameHeader = view.findViewById(R.id.studentAdapter_nameHeader);
            viewHolder.vehicleTable = view.findViewById(R.id.studentAdapter_vehicleTable);
            viewHolder.routeNameTV = (TextView) view.findViewById(R.id.studentAdapter_routeNameTV);
            viewHolder.statusTV = (TextView) view.findViewById(R.id.studentAdapter_status);
            viewHolder.count = (TextView) view.findViewById(R.id.count);
            viewHolder.routeNameTV.setTag(position);

        }else{
            viewHolder  = (ViewHolder) view.getTag();
        }

        viewHolder.routeNameTV.setText(NameList.get(position));
        viewHolder.count.setText(String.valueOf(position+1));

        if (totalList.get(position).equals("0")) {
            viewHolder.statusTV.setText("No Status");
        }else {
            Float total_comp = Float.parseFloat(total_completeList.get(position));
            Float total = Float.parseFloat(totalList.get(position));
            Float complete = (total_comp / total);
            Float complete_per = (complete * 100);
            System.out.println("total_comp== " + total_comp + " total== " + total + " complete==" + complete + " complete_per==" + complete_per);
            viewHolder.statusTV.setText(String.valueOf(Math.round(complete_per)) + "% Completed");
        }

        viewHolder.nameHeader.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));

        Log.e("DATA==", String.valueOf(topicArray.get(position).length()));

        try {
            final JSONArray dataArray = new JSONArray(topicArray.get(position));

            for(int i=0; i<dataArray.length(); i++) {

                final TableRow tr = (TableRow) context.getLayoutInflater().inflate(R.layout.adapter_student_lesson_topics, null);

                TextView vehicleTV,status,topic_count;
                LinearLayout viewBtn;

                viewBtn = tr.findViewById(R.id.studentTransportAdapter_detailsBtn);
                vehicleTV = (TextView) tr.findViewById(R.id.studentTransportAdapter_vehicleTV);
                status = (TextView) tr.findViewById(R.id.status);
                topic_count = (TextView) tr.findViewById(R.id.topic_count);

                vehicleTV.setText(dataArray.getJSONObject(i).getString("name"));
                topic_count.setText((String.valueOf(position+1)+"."+String.valueOf(i+1)));
                if(dataArray.getJSONObject(i).getString("status").equals("1")){
                    status.setText("Complete"+" ("+(Utility.parseDate("yyyy-MM-dd", defaultDateFormat, dataArray.getJSONObject(i).getString("complete_date")))+")");
                }else{
                    status.setText("Incomplete");
                }

                viewHolder.vehicleTable.addView(tr);
                context.registerForContextMenu(tr);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder {
        private TextView routeNameTV,statusTV,count;
        private LinearLayout nameHeader;
        private TableLayout vehicleTable;
    }

}