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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv;
    private static final String TAG = "register";
    private TextInputLayout email, password;
    private AppCompatButton registerme;
    private ProgressDialog progressDialog;
    //private SharedPreferencesOne sharedPreferencesOne;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*sharedPreferencesOne = new SharedPreferencesOne(this);
        if (!sharedPreferencesOne.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }*/

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //link xml to java
        tv = (TextView) findViewById(R.id.login);
        email = (TextInputLayout) findViewById(R.id.reg_email);
        password = (TextInputLayout) findViewById(R.id.reg_password);
        registerme = (AppCompatButton) findViewById(R.id.reg_btn);

        //set on click to button
        registerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = true;
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Enter a valid email address");
                    valid = false;
                } else {
                    email.setError(null);
                }

                if (pass.length() < 6 || pass.length() > 15) {
                    password.setError("Min of 6 characters");
                    valid = false;
                } else {
                    password.setError(null);
                }


                if (mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.setMessage("Authenticating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    registering(mail, pass);
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenti = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intenti);

            }
        });
    }

   /* private void launchHomeScreen() {
        Intent intentfirst = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentfirst);
    }*/

    private void registering(String mail, String pass) {
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String user_id = current_user.getUid();
                            sendVerificationEmail();

                            Log.d(TAG, "createUserWithEmail:success");
                            progressDialog.dismiss();
                            Intent register = new Intent(getApplicationContext(), DetailActivity.class);
                            register.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(register);
                            finish();
                        } else {
                            progressDialog.hide();
                            {
                                String error = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    error = "Weak Password";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    error = "Invalid Email";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    error = "Existing Account";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
     private void sendVerificationEmail() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), " Verification email sent to " + user.getEmail(), Toast.LENGTH_LONG).show();

                    } else {
                        Log.e("EmailVerification", "sendEmailVerification", task.getException());
                        Toast.makeText(getApplicationContext(),
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
