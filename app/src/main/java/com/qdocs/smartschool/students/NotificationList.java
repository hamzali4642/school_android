package com.qdocs.smartschool.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.qdocs.smartschool.BaseActivity;
import com.qdocs.smartschool.R;
import com.qdocs.smartschool.utils.DatabaseHelperCopy;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notification_list, null, false);
        mDrawerLayout.addView(contentView, 0);
        titleTV.setText(getApplicationContext().getString(R.string.notification));

        DatabaseHelperCopy db = new DatabaseHelperCopy(this);
        ArrayList<HashMap<String, String>> userList = db.GetUsers();
        ListView lv = (ListView) findViewById(R.id.user_list);
        System.out.println("userList== "+userList);
        ListAdapter adapter = new SimpleAdapter(NotificationList.this, userList, R.layout.list_row,new String[]{"name","location","description"}, new int[]{R.id.name, R.id.location, R.id.date_time});
        lv.setAdapter(adapter);
    }
}

