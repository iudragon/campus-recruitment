package com.projects.sharath.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EnterSkils extends AppCompatActivity {

    private TextView s1, s2, s3, s4, s5, s6, settexts;
    private AutoCompleteTextView enter_skills;
    private Button btn;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_skils);

        enter_skills = findViewById(R.id.skilltwo);

        s1 = findViewById(R.id.s1);
        s2 = findViewById(R.id.s2);
        s3 = findViewById(R.id.s3);
        s4 = findViewById(R.id.s4);
        s5 = findViewById(R.id.s5);
        s6 = findViewById(R.id.s6);
        settexts = findViewById(R.id.st);

        btn = findViewById(R.id.done);

        final String skills[] = getResources().getStringArray(R.array.skills);
        final ArrayAdapter<String> skills_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, skills);
        enter_skills.setAdapter(skills_adapter);
        enter_skills.setThreshold(1);

        enter_skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                counter = counter + 1;
                SharedPreferences sharedPreferences = getSharedPreferences("Skills", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (counter == 1) {
                    String first = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill1", first); //two.setText(first);
                    enter_skills.setText("");
                    settexts.setVisibility(View.VISIBLE);
                    s1.setVisibility(View.VISIBLE);
                    s1.setText(first);
                } else if (counter == 2) {
                    String second = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill2", second); //two.setText(first);
                    enter_skills.setText("");
                    s2.setText(second);
                    s2.setVisibility(View.VISIBLE);
                } else if (counter == 3) {
                    String third = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill3", third); //two.setText(first);
                    enter_skills.setText("");
                    s3.setVisibility(View.VISIBLE);
                    s3.setText(third);
                } else if (counter == 4) {
                    String fourth = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill4", fourth); //two.setText(first);
                    enter_skills.setText("");
                    s4.setVisibility(View.VISIBLE);
                    s4.setText(fourth);
                } else if (counter == 5) {
                    String five = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill5", five); //two.setText(first);
                    enter_skills.setText("");
                    s5.setVisibility(View.VISIBLE);
                    s5.setText(five);
                } else if (counter == 6) {
                    String six = enter_skills.getText().toString();
                    skills_adapter.remove(enter_skills.getText().toString());
                    editor.putString("skill6", six); //two.setText(first);
                    enter_skills.setText("");
                    s6.setVisibility(View.VISIBLE);
                    s6.setText(six);
                    enter_skills.setVisibility(View.GONE);
                }
                editor.putString("counter", String.valueOf(counter));
                editor.commit();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
