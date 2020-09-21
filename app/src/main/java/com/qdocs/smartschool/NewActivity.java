package com.qdocs.smartschool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qdocs.smartschool.fragments.StudentDashboardAttendance;
import com.qdocs.smartschool.fragments.StudentDashboardFees;
import com.qdocs.smartschool.fragments.StudentDashboardFragment;
import com.qdocs.smartschool.fragments.StudentDashboardHomeWork;
import com.qdocs.smartschool.fragments.StudentDashboardReportCard;
import com.qdocs.smartschool.fragments.StudentProfilePersonalFragment;
import com.qdocs.smartschool.students.StudentAttendance;

public class NewActivity extends AppCompatActivity {

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);




        i=getIntent().getIntExtra("i",0);

        if (i==1){
            loadFragment(new StudentDashboardHomeWork());
        }
        else if (i==2){
            loadFragment(new StudentDashboardFragment());
        }
        else if (i==3){
            loadFragment(new StudentDashboardFees());
        }
//        else if (i==4){
//            loadFragment(new StudentDashboardReportCard());
//        }
        else if (i==5){
            loadFragment(new StudentDashboardFragment());
        }
        else if (i==6){
            loadFragment(new StudentDashboardAttendance());
        }
        else if (i==7){
            loadFragment(new StudentDashboardAttendance());
        }
//        else if (i==8){
//            loadFragment(new StudentProfilePersonalFragment());
//        }
        else if (i==9){
            loadFragment(new StudentDashboardAttendance());
        }





    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragid, fragment);
        transaction.commit();
    }
}