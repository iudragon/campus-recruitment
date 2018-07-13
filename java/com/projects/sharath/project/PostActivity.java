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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    private Toolbar mtool;
    private EditText exp, name, rev;
    private Button submit;
    private Spinner salary;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private RadioButton yes,no;
    private RadioButton yes1,no1;

    private int count = 0;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mtool = findViewById(R.id.post_tab);
        setSupportActionBar(mtool);
        getSupportActionBar().setTitle("Interview Experience");
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        exp = (EditText) findViewById(R.id.exp_field);
        name = (EditText) findViewById(R.id.comp_field);
        rev = (EditText) findViewById(R.id.rev_field);
        salary = (Spinner) findViewById(R.id.sal);
        submit = (Button) findViewById(R.id.submit_btn);

        yes1 = findViewById(R.id.radio_pirates1);
        no1 = findViewById(R.id.radio_ninjas1);

        ArrayAdapter<String> salaries = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.salary));
        salaries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        salary.setAdapter(salaries);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;
                final String experience = exp.getText().toString();
                final String company = name.getText().toString();
                final String accept = rev.getText().toString();
                final String sal = salary.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(experience) || TextUtils.isEmpty(company) || TextUtils.isEmpty(accept)) {
                    exp.setError("Please enter your experience");
                    name.setError("Please enter company name");
                    rev.setError("Please fill it");
                    valid = false;
                } else {
                    exp.setError(null);
                    name.setError(null);
                    rev.setError(null);
                    progressDialog.setMessage("Posting the details");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    submit_post(experience, company, accept, sal);
                }
            }
        });
    }


    public void onRadioButtonClicked1(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates1:
                if (checked) {
                   result = "Accepted Offer";
                }
                break;
            case R.id.radio_ninjas1:
                if (checked)
                {
                    result = "No Offer";
                }
                break;
        }

    }

    private void submit_post(final String experience, final String company, final String accept, final String sal) {

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        final String user_id = current_user.getUid();

        Map<String, Object> postmap = new HashMap<>();
        postmap.put("experience", experience);
        postmap.put("company", company);
        postmap.put("role", accept);
        postmap.put("salary", sal);
        postmap.put("userid", user_id);
        postmap.put("timestamp", FieldValue.serverTimestamp());
        postmap.put("count",count);
        postmap.put("result",result);

        if(mAuth.getCurrentUser() != null) {

            firebaseFirestore.collection("Posts").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Post added Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intentp = new Intent(getApplicationContext(), MainActivity.class);
                        intentp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentp);
                        finish();
                    } else {
                        progressDialog.cancel();
                    }
                }
            });
        }

    }


}
