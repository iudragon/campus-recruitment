package com.projects.sharath.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logouts;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        logouts = findViewById(R.id.after_verify);
        if(firebaseUser.isEmailVerified())
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "Your email is not verified yet.", Toast.LENGTH_SHORT).show();
        }
        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent logout1 = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(logout1);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        if(mAuth.getCurrentUser().isEmailVerified())
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "Your email is not verified yet.", Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }
}
