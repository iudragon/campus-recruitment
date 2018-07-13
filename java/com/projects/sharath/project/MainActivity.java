package com.projects.sharath.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabLayout;

    private FirebaseFirestore firebaseFirestore;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.main_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Project");
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            FirebaseMessaging.getInstance().subscribeToTopic("jobs");
            viewPager = (ViewPager) findViewById(R.id.vp);
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout = (TabLayout) findViewById(R.id.main_tab);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_business_center_white_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_rss_feed_black_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_person_white_24dp);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        } else if (!currentUser.isEmailVerified()) {
            Toast.makeText(this, "Email is not verified", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
        } else {
            user_id = currentUser.getUid();
            firebaseFirestore.collection("Details").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            startActivity(new Intent(getApplicationContext(), DetailActivity.class));
                            finish();
                        } else {
                            try {
                                String errormsg = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, "Error :" + errormsg, Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException ex) {
                                Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            });
        }
      /* if (!currentUser.isEmailVerified())
        {

        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.post) {
            Intent post = new Intent(getApplicationContext(), PostActivity.class);
            startActivity(post);
            return true;
        }
        if (id == R.id.help_us) {
            Intent help = new Intent(getApplicationContext(), HelpUsActivity.class);
            startActivity(help);
        }
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(logout);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
