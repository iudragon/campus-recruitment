package com.projects.sharath.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView forgot;
    private static final String TAG = "login";
    private AppCompatButton loginme;
    private TextInputLayout email1, password;
    private ProgressDialog progressDialog;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        email1 = (TextInputLayout) findViewById(R.id.log_email);
        password = (TextInputLayout) findViewById(R.id.log_password);
        loginme = (AppCompatButton) findViewById(R.id.log_btn);

        forgot = (TextView) findViewById(R.id.forgot);

        loginme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email1.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();

                if (mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.setMessage("Validating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    logging(mail, pass);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentii = new Intent(getApplicationContext(), PassForgotActivity.class);
                startActivity(intentii);
            }
        });
    }

    private void logging(String mail, String pass)
    {
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            Intent register = new Intent(getApplicationContext(), MainActivity.class);
                            register.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(register);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.hide();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Please Check the Details Filled",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
