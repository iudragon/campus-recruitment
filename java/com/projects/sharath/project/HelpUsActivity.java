package com.projects.sharath.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.HashMap;

public class HelpUsActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private TextView years;
    private CardView cardView, cardView1;
    private Button button;

    private RadioButton yes, no;
    int placements = 1;
    String designations = "student";
    String rounds = "not defined";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_us);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        years = findViewById(R.id.help_year);

        yes = findViewById(R.id.radio_pirates2);
        no = findViewById(R.id.radio_ninjas2);
        cardView = findViewById(R.id.card1);
        cardView1 = findViewById(R.id.card2);
        button = findViewById(R.id.help_submit);

        if (mAuth.getCurrentUser() != null) {
            final FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            final String user_id = current_user.getUid();
            firebaseFirestore.collection("Details").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    String year = documentSnapshot.getString("About");
                    years.setText("Your Passout year : " + year);
                    helping(year);
                }
            });
        }
    }

    public void onRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_pirates2:
                if (checked)
                    Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
                placements = 1;
                cardView.setVisibility(View.GONE);
                cardView1.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_ninjas2:
                if (checked)
                    Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                cardView.setVisibility(View.VISIBLE);
                cardView1.setVisibility(View.GONE);
                placements = 0;
                break;
        }
    }

    public void onRadioButtonClicked3(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_r1:
                if (checked)
                    Toast.makeText(this, "Round 1", Toast.LENGTH_SHORT).show();
                rounds = "Aptitude and GD";
                button.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_r2:
                if (checked)
                    Toast.makeText(this, "Round 2", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                rounds = "Technical";
                break;
            case R.id.radio_r3:
                if (checked)
                    Toast.makeText(this, "Round 3", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                rounds = "HR Round";
                break;
        }
    }

    public void onRadioButtonClicked4(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_d1:
                if (checked)
                    Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                designations = "Developer";
                break;
            case R.id.radio_d2:
                if (checked)
                    Toast.makeText(this, "Sales", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                designations = "Sales";
                break;
            case R.id.radio_d3:
                if (checked)
                    Toast.makeText(this, "Business Analyst", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                designations = "Business Analyst";
                break;
            case R.id.radio_d4:
                if (checked)
                    Toast.makeText(this, "Networking", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                designations = "Networking";
                break;
            case R.id.radio_d5:
                if (checked)
                    Toast.makeText(this, "Others", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                designations = "Others";
                break;
        }
    }

    private void helping(final String year) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                final String user_id = current_user.getUid();
                HashMap<String, Object> help_map = new HashMap<>();
                help_map.put("success", placements);
                help_map.put("year", year);
                help_map.put("designations",designations);
                help_map.put("rounds",rounds);
                if (placements == 1) {
                    firebaseFirestore.collection("Placements").document("Counts").collection("Placed").document(user_id).set(help_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(HelpUsActivity.this, "Thanks for helping", Toast.LENGTH_SHORT).show();
                                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intenth);
                                finish();
                            } else {
                                Toast.makeText(HelpUsActivity.this, "There was some problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    firebaseFirestore.collection("Placements").document("Counts").collection("Notplaced").document(user_id).set(help_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(HelpUsActivity.this, "Thanks for helping", Toast.LENGTH_SHORT).show();
                                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intenth);
                                finish();
                            } else {
                                Toast.makeText(HelpUsActivity.this, "There was some problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
