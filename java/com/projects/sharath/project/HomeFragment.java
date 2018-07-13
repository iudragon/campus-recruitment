package com.projects.sharath.project;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<HomeListModel> homeListModels;
    private HomeListAdapter homeListAdapter;
    private ImageButton imageButton;


    private DocumentSnapshot lastvisible;
    private Boolean isfirstpage = true;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        homeListModels = new ArrayList<>();

        recyclerView = home.findViewById(R.id.rec_one);
        homeListAdapter = new HomeListAdapter(homeListModels,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(homeListAdapter);
        imageButton = home.findViewById(R.id.edit_post);


        if (mAuth.getCurrentUser() != null) {
            Query firstquery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING);
            firstquery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String blog_id = doc.getDocument().getId();
                            HomeListModel homeListModel = doc.getDocument().toObject(HomeListModel.class).withId(blog_id);
                            homeListModels.add(homeListModel);
                            homeListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        return home;
    }

}
