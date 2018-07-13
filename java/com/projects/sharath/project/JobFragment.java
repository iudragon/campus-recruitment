package com.projects.sharath.project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment {

    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private List<JobListModel> jobListModels;
    private FirebaseFirestore firebaseFirestore;
    private JobListAdapter jobListAdapter;
    private DocumentSnapshot lastvisible;
    private Boolean isfirstpage = true;


    public JobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View job = inflater.inflate(R.layout.fragment_job, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        jobListModels = new ArrayList<>();

        recyclerView = job.findViewById(R.id.rec_two);
        jobListAdapter = new JobListAdapter(jobListModels,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(jobListAdapter);

        if (mAuth.getCurrentUser() != null) {

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean reachedbottom = !recyclerView.canScrollVertically(1);
                    if (reachedbottom) {
                        loadmorepostss();
                    }
                }
            });
            Query firstqueryy = firebaseFirestore.collection("Jobs").orderBy("timestamps", Query.Direction.DESCENDING).limit(3);
            firstqueryy.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (isfirstpage) {
                        lastvisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                    }
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            JobListModel jobListModel = doc.getDocument().toObject(JobListModel.class);
                            if (isfirstpage) {
                                jobListModels.add(jobListModel);
                            } else {
                                jobListModels.add(0, jobListModel);
                            }
                            jobListAdapter.notifyDataSetChanged();
                        }
                    }
                    isfirstpage = false;
                }
            });
        }

        return job;
    }

    public void loadmorepostss() {
        Query nextqueryy = firebaseFirestore.collection("Jobs")
                .orderBy("timestamps", Query.Direction.DESCENDING)
                .startAfter(lastvisible)
                .limit(3);

        nextqueryy.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    lastvisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            JobListModel jobListModel = doc.getDocument().toObject(JobListModel.class);
                            jobListModels.add(jobListModel);
                            jobListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
