package com.projects.sharath.project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class JobFullActivity extends AppCompatActivity {
    private static final String TAG = "jobs";
    private TextView name, link1, job_venue, job_role, job_salary, job_date, job_rounds, r1, r2, r3, job_skills, post_time, link2;
    private Toolbar mtool;
    private LinearLayout l1, l2, l3, l4, l5, l6;
    private RelativeLayout rl1;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_full);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mtool = (Toolbar) findViewById(R.id.jobstool);
        setSupportActionBar(mtool);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //linkweb
        name = findViewById(R.id.comp_name1);
        link1 = findViewById(R.id.comp_web1);
        link2 = findViewById(R.id.apply_link);
        job_venue = findViewById(R.id.venue1);
        job_role = findViewById(R.id.designation1);
        job_salary = findViewById(R.id.salaries1);
        job_date = findViewById(R.id.ret_date1);
        job_rounds = findViewById(R.id.ret_rounds);
        r1 = findViewById(R.id.ret_round1);
        r2 = findViewById(R.id.ret_round2);
        r3 = findViewById(R.id.ret_round3);
        l1 = findViewById(R.id.one);
        l2 = findViewById(R.id.three);
        l3 = findViewById(R.id.four);
        l4 = findViewById(R.id.five);
        l5 = findViewById(R.id.six);
        l6 = findViewById(R.id.seven);
        job_skills = findViewById(R.id.ret_skills);
        rl1 = findViewById(R.id.two);
        post_time = findViewById(R.id.officer_time1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //1
            String names = (String) bundle.get("company");
            name.setText(names);
            getSupportActionBar().setTitle(names);
            String ven, rol, sal, dat, tim, rou;
            //2
            ven = (String) bundle.get("venue");
            job_venue.setText(ven);
            //3
            rol = (String) bundle.get("role");
            job_role.setText(rol);
            //4
            sal = (String) bundle.get("salary");
            job_salary.setText("₹" + " " + sal);
            //5
            dat = bundle.getString("date");
            tim = bundle.getString("timings");
            job_date.setText(dat + " " + "," + " " + tim);
            //6
            String times = bundle.getString("timestamp");
            post_time.setText(times);
            //7
            String linkwebs = bundle.getString("linkweb");
            link2.setText(linkwebs);
            Linkify.addLinks(link2, Linkify.ALL);
            l5.setVisibility(View.VISIBLE);
            //8
            String compwebs = bundle.getString("compweb");
            link1.setText(compwebs);
            Linkify.addLinks(link1, Linkify.ALL);
            link1.setVisibility(View.VISIBLE);
            //9
            String rou1 = bundle.getString("roundone");
            r1.setText(rou1);
            l1.setVisibility(View.VISIBLE);
            //10
            String rou2, rou3;
            rou2 = bundle.getString("roundtwo");
            rou3 = bundle.getString("roundthree");
            r2.setText(rou2);
            r3.setText(rou3);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.VISIBLE);
            //11
            String rounds = bundle.getString("rounds");
            job_rounds.setText(rounds);

            l4.setVisibility(View.VISIBLE);
            l6.setVisibility(View.VISIBLE);
            rl1.setVisibility(View.VISIBLE);
            //12
            String skills;
            skills = bundle.getString("skills");
            job_skills.setText(skills);

        } else {
            Toast.makeText(this, "Loading Failed", Toast.LENGTH_SHORT).show();
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            rl1.setVisibility(View.GONE);
        }
    }
}