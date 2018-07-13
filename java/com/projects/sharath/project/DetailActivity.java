package com.projects.sharath.project;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "details";
    private EditText be, pu, sslc;
    private EditText first, last, usn, linked, about, backs, interns, aptis;
    private AutoCompleteTextView one, two, three, four, five, six, branch;
    private Button sbutton;
    private ProgressDialog progressDialog;
    private Toolbar mtool;
    private TextView shareddata, cts;
    private TextView s11, s21, s31, s41, s51, s61;
    int counts = 0;
    private static final String DEF = "NA";

    private Button skills_act;

    private FirebaseFirestore firebaseFirestore;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mtool = (Toolbar) findViewById(R.id.details);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Enter Details");
        getSupportActionBar().show();
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseFirestore = FirebaseFirestore.getInstance();
        skills_act = findViewById(R.id.skillone);
        shareddata = findViewById(R.id.shareddata);
        s11 = findViewById(R.id.s11);
        s21 = findViewById(R.id.s21);
        s31 = findViewById(R.id.s31);
        s41 = findViewById(R.id.s41);
        s51 = findViewById(R.id.s51);
        s61 = findViewById(R.id.s61);
        backs = findViewById(R.id.enter_backs);
        interns = findViewById(R.id.enter_interns);
        cts = findViewById(R.id.counters);
        aptis = findViewById(R.id.enter_apti);
        skills_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counts = counts + 1;
                Intent intent = new Intent(getApplicationContext(), EnterSkils.class);
                startActivity(intent);
                skills_act.setVisibility(View.GONE);
            }
        });
        //others
        progressDialog = new ProgressDialog(this);

        first = (EditText) findViewById(R.id.first);
        last = (EditText) findViewById(R.id.last);
        usn = (EditText) findViewById(R.id.usn);
        linked = (EditText) findViewById(R.id.linkedin);
        about = (EditText) findViewById(R.id.about);

        be = findViewById(R.id.be);
        pu = findViewById(R.id.puc);
        sslc = findViewById(R.id.sslc);

        branch = findViewById(R.id.branch);


        sbutton = (Button) findViewById(R.id.det_submit);

        String[] branches = getResources().getStringArray(R.array.branches);
        ArrayAdapter<String> badap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branches);
        branch.setAdapter(badap);

        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;

                //getting values and converting it to string so that we can upload it to firebase.
                final String fname = first.getText().toString();
                final String lname = last.getText().toString();
                final String uni = usn.getText().toString();
                final String linkedin = linked.getText().toString();
                final String ab = about.getText().toString();
                final String sone = s11.getText().toString();
                final String stwo = s21.getText().toString();
                final String sthree = s31.getText().toString();
                final String sfour = s41.getText().toString();
                final String sfive = s51.getText().toString();
                final String ssix = s61.getText().toString();
                final String course = branch.getText().toString();
                final String ct = cts.getText().toString();
                final String backlog = backs.getText().toString();
                final String internship = interns.getText().toString();
                final String aptitudes = aptis.getText().toString();

                final String bep = be.getText().toString();
                final String pup = pu.getText().toString();
                final String sslcp = sslc.getText().toString();

                /*calculation starts*/
                Double d_counter = Double.parseDouble(ct);
                Double backlogs = Double.parseDouble(backlog);
                Double tenth = Double.parseDouble(sslcp);
                Double twevlth = Double.parseDouble(pup);
                Double degree = Double.parseDouble(bep);
                Double inter = Double.parseDouble(internship);
                Double apt = Double.parseDouble(aptitudes);

                //number of skills
                Double val1;
                if (d_counter > 2) {
                    val1 = d_counter * 10;
                } else {
                    val1 = d_counter * 5;
                }

                //number of backlogs
                Double val2;
                if (backlogs > 0) {
                    val2 = backlogs * 25;
                } else {
                    val2 = 0.0;
                }

                //tenth marks
                Double val3;
                if (tenth > 85) {
                    val3 = 10.0;
                } else if (tenth > 75) {
                    val3 = 7.0;
                } else if (tenth > 60) {
                    val3 = 5.0;
                } else {
                    val3 = 2.0;
                }

                //twevlth marks
                Double val4;
                if (twevlth > 75) {
                    val4 = 10.0;
                } else if (twevlth > 60) {
                    val4 = 8.0;
                } else {
                    val4 = 5.0;
                }

                //be marks
                Double val5;
                if (degree > 70) {
                    val5 = 10.0;
                } else if (degree > 60) {
                    val5 = 8.0;
                } else if (degree > 55) {
                    val5 = 6.0;
                } else {
                    val5 = 4.0;
                }

                //internship marks
                Double val6;
                if (inter >= 1) {
                    val6 = inter * 10;
                } else {
                    val6 = 0.0;
                }

                //aptis
                Double val7;
                if (apt >= 8) {
                    val7 = apt * 5;
                } else if (apt >= 5) {
                    val7 = apt * 3;
                } else {
                    val7 = apt * 1;
                }

               /* if (TextUtils.isEmpty(backlog) || TextUtils.isEmpty(sslcp) || TextUtils.isEmpty(pup) || TextUtils.isEmpty(bep)
                        || TextUtils.isEmpty(aptitudes) || TextUtils.isEmpty(internship) || TextUtils.isEmpty(backlog)) {
                    backs.setError("Please fill this");
                    sslc.setError("Please fill this");
                    pu.setError("Please fill this");
                    be.setError("Please fill this");
                    aptis.setError("Please fill this");
                    interns.setError("Please fill this");
                    backs.setError("Please fill this");
                } else {

                }*/
                //validations
                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname)) {
                    first.setError("Please enter First Name");
                    last.setError("Please enter Last Name");
                    valid = false;
                } else {
                    first.setError(null);
                    last.setError(null);
                }

                if (TextUtils.isEmpty(uni) || TextUtils.isEmpty(ab)) {
                    usn.setError("Please enter USN");
                    about.setError("Please enter about what you do");
                    valid = false;
                } else {
                    usn.setError(null);
                    about.setError(null);
                }

                if (TextUtils.isEmpty(uni) || TextUtils.isEmpty(ab)) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG);
                } else {
                    progressDialog.setMessage("Submitting the Details");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Skills", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(DetailActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    Double results = val1 + val3 + val4 + val5 + val6 + val7 - val2;
                    Double predict;
                    if (results >= 112) {
                        predict = 95.0;
                    } else if (results >= 98) {
                        predict = 85.0;
                    } else if (results >= 84) {
                        predict = 75.0;
                    } else if (results >= 68) {
                        predict = 65.0;
                    } else if (results >= 54) {
                        predict = 55.0;
                    } else {
                        predict = 35.0;
                    }

                    // Firebase Code to store
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String user_id = current_user.getUid();

                    HashMap<String, Object> detailsmap = new HashMap<>();
                    detailsmap.put("first", fname);
                    detailsmap.put("Last", lname);
                    detailsmap.put("usn", uni);

                    detailsmap.put("LinkedinID", linkedin);
                    detailsmap.put("About", ab);
                    detailsmap.put("SkillOne", sone);
                    detailsmap.put("SkillTwo", stwo);
                    detailsmap.put("SkillThree", sthree);
                    detailsmap.put("SkillFour", sfour);
                    detailsmap.put("SkillFive", sfive);
                    detailsmap.put("SkillSix", ssix);
                    detailsmap.put("degree", bep);
                    detailsmap.put("PUC", pup);
                    detailsmap.put("SSLC", sslcp);
                    detailsmap.put("branch", course);
                    detailsmap.put("counter", ct);

                    detailsmap.put("internships", internship);
                    detailsmap.put("aptitude", aptitudes);
                    detailsmap.put("predict", predict);
                    detailsmap.put("backlog",backlog);

                    if (mAuth.getCurrentUser() != null) {

                        firebaseFirestore.collection("Details").document(user_id).set(detailsmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Details added Successfully.", Toast.LENGTH_SHORT).show();
                                    Intent intentd = new Intent(getApplicationContext(), MainActivity.class);
                                    intentd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentd);
                                    finish();
                                } else {
                                    progressDialog.cancel();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(DetailActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (counts > 0) {
            Toast.makeText(this, "You have successfully saved your data : " + counts + " time.", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("Skills", Context.MODE_PRIVATE);
            String s1 = preferences.getString("skill1", DEF);
            String s2 = preferences.getString("skill2", DEF);
            String s3 = preferences.getString("skill3", DEF);
            String s4 = preferences.getString("skill4", DEF);
            String s5 = preferences.getString("skill5", DEF);
            String s6 = preferences.getString("skill6", DEF);
            String s7 = preferences.getString("counter", DEF);
            cts.setText(s7);
            shareddata.setVisibility(View.VISIBLE);
            shareddata.setText("Selected Skills are : ");

            if (s1.equals(DEF)) {
                s11.setVisibility(View.GONE);
            } else {
                s11.setVisibility(View.VISIBLE);
                s11.setText(s1);
            }

            if (s2.equals(DEF)) {
                s21.setVisibility(View.GONE);
            } else {
                s21.setVisibility(View.VISIBLE);
                s21.setText(s2);
            }
            if (s3.equals(DEF)) {
                s31.setVisibility(View.GONE);
            } else {
                s31.setVisibility(View.VISIBLE);
                s31.setText(s3);
            }
            if (s4.equals(DEF)) {
                s41.setVisibility(View.GONE);
            } else {
                s41.setVisibility(View.VISIBLE);
                s41.setText(s4);
            }
            if (s5.equals(DEF)) {
                s51.setVisibility(View.GONE);
            } else {
                s51.setVisibility(View.VISIBLE);
                s51.setText(s5);
            }
            if (s6.equals(DEF)) {
                s61.setVisibility(View.GONE);
            } else {
                s61.setVisibility(View.VISIBLE);
                s61.setText(s6);
            }
        }
    }
}

/* sslc, puc, be, skills[counter], backlogs
ifskills > 3 then mul = skills*10 */