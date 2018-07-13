package com.projects.sharath.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentListenOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView fname, lname, pred, sp, pp, dp, user_usn, user_branch, user_linkedin;
    private Button imageView;
    private TextView user_email, update_p, user_s1, user_s2, user_s3, user_s4, user_s5, user_s6;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    private ProgressBar progressBar, pbs, pbp, pbd;

    private TextView ret_apt, ret_int , ret_back;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profile = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = profile.findViewById(R.id.prof_prog);
        pbs = profile.findViewById(R.id.prof_sslc);
        pbp = profile.findViewById(R.id.prof_puc);
        pbd = profile.findViewById(R.id.prof_degree);
        imageView = profile.findViewById(R.id.edit_profile);

        fname = profile.findViewById(R.id.rname);
        lname = profile.findViewById(R.id.rnamet);
        pred = profile.findViewById(R.id.rpredict);
        sp = profile.findViewById(R.id.profsp);
        pp = profile.findViewById(R.id.profpp);
        dp = profile.findViewById(R.id.profdp);
        user_usn = profile.findViewById(R.id.ret_usn);
        user_branch = profile.findViewById(R.id.ret_branch);
        user_linkedin = profile.findViewById(R.id.ret_lid);
        user_email = profile.findViewById(R.id.user_email);
        update_p = profile.findViewById(R.id.update_pre);

        user_s1 = profile.findViewById(R.id.ret_sone);
        user_s2 = profile.findViewById(R.id.ret_stwo);
        user_s3 = profile.findViewById(R.id.ret_sthree);
        user_s4 = profile.findViewById(R.id.ret_sfour);
        user_s5 = profile.findViewById(R.id.ret_sfive);
        user_s6 = profile.findViewById(R.id.ret_ssix);

        ret_apt = profile.findViewById(R.id.apti_val);
        ret_back = profile.findViewById(R.id.back_val);
        ret_int = profile.findViewById(R.id.intern_val);
        return profile;
    }


    @Override
    public void onStart() {
        super.onStart();

        //DocumentListenOptions documentListenOptions = new DocumentListenOptions();
        //documentListenOptions.includeMetadataChanges();
        if (mAuth.getCurrentUser() != null) {
            final FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            final String user_id = current_user.getUid();
            firebaseFirestore.collection("Details").document(user_id).addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if (documentSnapshot.exists() || documentSnapshot != null) {

                        user_email.setText(current_user.getEmail());
                        String predictions = documentSnapshot.get("predict").toString();
                        String sslc, puc, be;
                        sslc = documentSnapshot.get("SSLC").toString();
                        puc = documentSnapshot.get("PUC").toString();
                        be = documentSnapshot.get("degree").toString();
                        pred.setText(predictions + "%");
                        sp.setText(sslc + "%");
                        pp.setText(puc + "%");
                        dp.setText(be + "%");
                        try {
                            progressBar.setProgress((int) Double.parseDouble(predictions));
                            pbs.setProgress((int) Double.parseDouble(sslc));
                            pbp.setProgress((int) Double.parseDouble(puc));
                            pbd.setProgress((int) Double.parseDouble(be));
                        } catch (NullPointerException el) {
                            Toast.makeText(getActivity(), "Error : " + el, Toast.LENGTH_SHORT).show();
                        }


                        String f = documentSnapshot.getString("first");
                        String l, my_usn, my_branch, my_lid, s1, s2, s3, s4, s5, s6;
                        l = documentSnapshot.getString("Last");
                        fname.setText(f);
                        fname.setAllCaps(true);
                        lname.setText(l);
                        lname.setAllCaps(true);

                        my_usn = documentSnapshot.getString("usn");
                        my_branch = documentSnapshot.getString("branch");
                        user_usn.setText(my_usn);
                        user_branch.setText("Department of" + " " + my_branch);

                        my_lid = documentSnapshot.getString("LinkedinID");
                        user_linkedin.setText("https://www.linkedin.com/in/" + my_lid);
                        Linkify.addLinks(user_linkedin, Linkify.ALL);

                        Toast.makeText(getActivity(), "Loading Skills", Toast.LENGTH_SHORT).show();

                        s1 = documentSnapshot.getString("SkillOne");
                        if (TextUtils.isEmpty(s1)) {
                            user_s1.setVisibility(View.GONE);
                        } else {
                            user_s1.setVisibility(View.VISIBLE);
                            user_s1.setText(s1);
                        }
                        s2 = documentSnapshot.getString("SkillTwo");
                        if (TextUtils.isEmpty(s2)) {
                            user_s2.setVisibility(View.GONE);

                        } else {

                            user_s2.setVisibility(View.VISIBLE);
                            user_s2.setText(s2);
                        }

                        s3 = documentSnapshot.getString("SkillThree");
                        if (TextUtils.isEmpty(s3)) {
                            user_s3.setVisibility(View.GONE);

                        } else {
                            user_s3.setVisibility(View.VISIBLE);
                            user_s3.setText(s3);
                        }

                        s4 = documentSnapshot.getString("SkillFour");
                        if (TextUtils.isEmpty(s4)) {
                            user_s4.setVisibility(View.GONE);

                        } else {
                            user_s4.setVisibility(View.VISIBLE);
                            user_s4.setText(s4);
                        }

                        s5 = documentSnapshot.getString("SkillFive");
                        if (TextUtils.isEmpty(s5)) {
                            user_s5.setVisibility(View.GONE);
                        } else {
                            user_s5.setVisibility(View.VISIBLE);
                            user_s5.setText(s5);
                        }

                        s6 = documentSnapshot.getString("SkillSix");
                        if (TextUtils.isEmpty(s6)) {
                            user_s6.setVisibility(View.GONE);
                        } else {
                            user_s6.setVisibility(View.VISIBLE);
                            user_s6.setText(s6);
                        }

                        String de, pu, ten, passout;
                        de = documentSnapshot.getString("degree");
                        pu = documentSnapshot.getString("PUC");
                        ten = documentSnapshot.getString("SSLC");
                        passout = documentSnapshot.getString("About");

                        String aptitudes = documentSnapshot.getString("aptitude");
                        if(aptitudes.isEmpty())
                        {
                            ret_apt.setText("00");
                        }
                        else
                        {
                            ret_apt.setText(String.valueOf(aptitudes));
                        }

                        String internships = documentSnapshot.getString("internships");
                        if(internships.isEmpty())
                        {
                            ret_int.setText("00");
                        }
                        else
                        {
                            ret_int.setText(String.valueOf(internships));
                        }

                        String backlogs = documentSnapshot.getString("backlog");
                        if(backlogs.isEmpty())
                        {
                            ret_back.setText("00");
                        }
                        else
                        {
                            ret_back.setText(String.valueOf(backlogs));
                        }

                        send(f, l, my_usn, my_branch, my_lid, de, pu, ten, passout, s1,s2,s3,s4,s5,s6,aptitudes,internships,backlogs);

                    } else {
                        user_linkedin.setText("Id not provided.");
                        Toast.makeText(getActivity(), "Some Details are missing.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), DetailActivity.class));
                    }
                }
            });
        }
    }

    private void send(final String f, final String l, final String my_usn, final String my_branch, final String my_lid, final String de, final String pu,
                      final String ten, final String passout, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6, final String aptitudes, final String internships, final String backlogs) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditDetails.class);
                intent.putExtra("First", f);
                intent.putExtra("Last", l);
                intent.putExtra("USN", my_usn);
                intent.putExtra("Branch", my_branch);
                intent.putExtra("LinkedIn", my_lid);
               intent.putExtra("Sk1", s1);
                intent.putExtra("Sk2", s2);
                intent.putExtra("Sk3", s3);
                intent.putExtra("Sk4", s4);
                intent.putExtra("Sk5", s5);
                intent.putExtra("Sk6", s6);
                intent.putExtra("Degree", de);
                intent.putExtra("PUC", pu);
                intent.putExtra("SSLC", ten);
                intent.putExtra("PASSOUT", passout);
                intent.putExtra("my_backs",backlogs);
                intent.putExtra("my_interns",internships);
                intent.putExtra("my_apti",aptitudes);
                startActivity(intent);
            }
        });
    }
}
