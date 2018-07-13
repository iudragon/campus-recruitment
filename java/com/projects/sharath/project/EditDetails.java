package com.projects.sharath.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditDetails extends AppCompatActivity {

    private Toolbar mtool;
    private EditText efirst, elast, eusn, elid, eyear, esslc, epuc, edegree;
    private AutoCompleteTextView ebranch, es1, es2, es3, es4, es5, es6;
    private Button clickme;
    private ProgressDialog progressDialog;
    private EditText apti, backs, interns;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mtool = (Toolbar) findViewById(R.id.details1);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Edit Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();

        //others
        progressDialog = new ProgressDialog(this);

        efirst = findViewById(R.id.edit_first);
        elast = findViewById(R.id.edit_last);
        eusn = findViewById(R.id.edit_usn);
        eyear = findViewById(R.id.edit_about);
        esslc = findViewById(R.id.edit_sslc);
        epuc = findViewById(R.id.edit_puc);
        edegree = findViewById(R.id.edit_be);
        clickme = findViewById(R.id.edit_submit);
        interns = findViewById(R.id.edit_interns);
        backs = findViewById(R.id.edit_backs);
        apti = findViewById(R.id.edit_apti);


        ebranch = findViewById(R.id.edit_branch);
        String[] branches = getResources().getStringArray(R.array.branches);
        ArrayAdapter<String> badap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branches);
        ebranch.setAdapter(badap);

        elid = findViewById(R.id.edit_linkedin);


        es1 = findViewById(R.id.edit_skillone);
        es2 = findViewById(R.id.edit_skilltwo);
        es3 = findViewById(R.id.edit_skillthree);
        es4 = findViewById(R.id.edit_skillfour);
        es5 = findViewById(R.id.edit_skillfive);
        es6 = findViewById(R.id.edit_skillsix);
        String[] skills = getResources().getStringArray(R.array.skills);
        ArrayAdapter<String> skillsadap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, skills);
        es1.setAdapter(skillsadap);
        es2.setAdapter(skillsadap);
        es3.setAdapter(skillsadap);
        es4.setAdapter(skillsadap);
        es5.setAdapter(skillsadap);
        es6.setAdapter(skillsadap);
        es1.setThreshold(1);
        es2.setThreshold(1);
        es3.setThreshold(1);
        es4.setThreshold(1);
        es5.setThreshold(1);
        es6.setThreshold(1);


        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {

            String first_name = bundle1.getString("First");
            efirst.setText(first_name);
            //2
            String last_name = bundle1.getString("Last");
            elast.setText(last_name);
            //3
            String usns, branchess, lids;
            usns = bundle1.getString("USN");
            branchess = bundle1.getString("Branch");
            lids = bundle1.getString("LinkedIn");
            eusn.setText(usns);
            ebranch.setText(branchess);
            elid.setText(lids);
            //4
            String ss1, ss2, ss3, ss4, ss5, ss6;
            ss1 = bundle1.getString("Sk1");
            ss2 = bundle1.getString("Sk2");
            ss3 = bundle1.getString("Sk3");
            ss4 = bundle1.getString("Sk4");
            ss5 = bundle1.getString("Sk5");
            ss6 = bundle1.getString("Sk6");
            es1.setText(ss1);
            es2.setText(ss2);
            es3.setText(ss3);
            es4.setText(ss4);
            es5.setText(ss5);
            es6.setText(ss6);
            //5
            String dd, pp, ss, yy;
            dd = bundle1.getString("Degree");
            pp = bundle1.getString("PUC");
            ss = bundle1.getString("SSLC");
            yy = bundle1.getString("PASSOUT");
            edegree.setText(dd);
            epuc.setText(pp);
            esslc.setText(ss);
            eyear.setText(yy);

            String aa, ii, bb;
            aa = bundle1.getString("my_apti");
            apti.setText(aa);
            ii = bundle1.getString("my_interns");
            interns.setText(ii);
            bb = bundle1.getString("my_backs");
            backs.setText(bb);

            editing();
        }
    }

    private void editing() {
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editone = efirst.getText().toString();
                String edittwo = elast.getText().toString();
                String editthree = eusn.getText().toString();
                String edit_four, edit_five, edit_six, edit_seven, edit_eight, edit_nine, edit_ten, edit_eleven, edit_twelve, edit_thirteen, edit_fourteen;
                edit_four = elid.getText().toString();
                edit_five = eyear.getText().toString();
                edit_six = es1.getText().toString();
                edit_seven = es2.getText().toString();
                edit_eight = es3.getText().toString();
                edit_nine = es4.getText().toString();
                edit_ten = es5.getText().toString();
                edit_eleven = es6.getText().toString();
                edit_twelve = edegree.getText().toString();
                edit_thirteen = epuc.getText().toString();
                edit_fourteen = esslc.getText().toString();
                String edit_fifteen = ebranch.getText().toString();

                String edit_sixteen, edit_seventeen, edit_eighteen;
                edit_sixteen = backs.getText().toString(); //bb
                edit_seventeen = interns.getText().toString(); //ii
                edit_eighteen = apti.getText().toString();

                if (!TextUtils.isEmpty(editone) || !TextUtils.isEmpty(edittwo) || !TextUtils.isEmpty(editthree) || !TextUtils.isEmpty(edit_five) || !TextUtils.isEmpty(edit_six) ||
                        !TextUtils.isEmpty(edit_seven) || !TextUtils.isEmpty(edit_eight) || !TextUtils.isEmpty(edit_nine) || !TextUtils.isEmpty(edit_ten) || !TextUtils.isEmpty(edit_eleven) ||
                        !TextUtils.isEmpty(edit_twelve) || !TextUtils.isEmpty(edit_thirteen) || !TextUtils.isEmpty(edit_fourteen) || !TextUtils.isEmpty(edit_fifteen)
                        || !TextUtils.isEmpty(edit_sixteen) || !TextUtils.isEmpty(edit_seventeen) || !TextUtils.isEmpty(edit_eighteen)) {

                    progressDialog.setMessage("Updating the Details");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String user_id = current_user.getUid();
                    HashMap<String, Object> detailsmap1 = new HashMap<>();
                    detailsmap1.put("first", editone);
                    detailsmap1.put("Last", edittwo);
                    detailsmap1.put("usn", editthree);
                    detailsmap1.put("LinkedinID", edit_four);
                    detailsmap1.put("About", edit_five);
                    detailsmap1.put("SkillOne", edit_six);
                    detailsmap1.put("SkillTwo", edit_seven);
                    detailsmap1.put("SkillThree", edit_eight);
                    detailsmap1.put("SkillFour", edit_nine);
                    detailsmap1.put("SkillFive", edit_ten);
                    detailsmap1.put("SkillSix", edit_eleven);
                    detailsmap1.put("degree", edit_twelve);
                    detailsmap1.put("PUC", edit_thirteen);
                    detailsmap1.put("SSLC", edit_fourteen);
                    detailsmap1.put("branch", edit_fifteen);

                    detailsmap1.put("internships", edit_seventeen);
                    detailsmap1.put("aptitude", edit_eighteen);
                    detailsmap1.put("backlog", edit_sixteen);

                    if (mAuth.getCurrentUser() != null) {
                        firebaseFirestore.collection("Details").document(user_id).update(detailsmap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(EditDetails.this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
                                    Intent intentdd = new Intent(getApplicationContext(), MainActivity.class);
                                    intentdd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentdd);
                                    finish();
                                } else {
                                    progressDialog.cancel();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(EditDetails.this, "All Fields are mandatory.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
