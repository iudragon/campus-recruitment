package com.projects.sharath.project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharath on 21-Mar-18.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    public List<HomeListModel> homeListModels;
    public Context context;
    ImageButton imageButton;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    public HomeListAdapter(List<HomeListModel> homeListModels, Context context) {
        this.homeListModels = homeListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_items, parent, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        imageButton = view.findViewById(R.id.edit_post);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        final String blog_id = homeListModels.get(position).BlogPostId;
        final String current_user = mAuth.getCurrentUser().getUid();

        String exp_data = homeListModels.get(position).getExperience();
        holder.setexp(exp_data);

        try {
            long milliseconds = homeListModels.get(position).getTimestamp().getTime();
            String dateString = android.text.format.DateFormat.format("dd/MM/yyyy  hh:mm aa", new Date(milliseconds)).toString();
            holder.setTime(dateString);
        } catch (NullPointerException e) {
            Toast.makeText(context, "Post is Loading", Toast.LENGTH_SHORT).show();
        }

        final String comp_data = homeListModels.get(position).getCompany();
        holder.setComp(comp_data);

        String accept_data = homeListModels.get(position).getResult();
        holder.setAccept(accept_data);

        String sal_data = homeListModels.get(position).getSalary();
        holder.setSal(sal_data);


        final String user_data = homeListModels.get(position).getUserid();
        firebaseFirestore.collection("Details").document(user_data).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String First = task.getResult().getString("first");
                    holder.SetName(First);
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //counts
        if (mAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("Posts/" + blog_id + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (!documentSnapshots.isEmpty()) {
                        int counts = documentSnapshots.size();
                        holder.updatelikes(counts);
                    } else {
                        holder.updatelikes(0);
                    }
                }
            });
        }
        if (mAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("Posts/" + blog_id + "/Likes").document(current_user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()) {
                        holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_red_24dp));
                    } else {
                        holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_black_24dp));
                    }
                }
            });
        }
        holder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Posts/" + blog_id + "/Likes").document(current_user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            Map<String, Object> likeMap = new HashMap<>();
                            likeMap.put("timestamp", FieldValue.serverTimestamp());
                            firebaseFirestore.collection("Posts/" + blog_id + "/Likes").document(current_user).set(likeMap);
                        } else {
                            firebaseFirestore.collection("Posts/" + blog_id + "/Likes").document(current_user).delete();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username, exp, accept, company, salary, dates;
        private View mView;
        private ImageView blogLikeBtn;
        private TextView blogLikeCount;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            blogLikeBtn = mView.findViewById(R.id.like);

        }

        public void setexp(String experience) {
            exp = mView.findViewById(R.id.rec_exp);
            exp.setText(experience);
        }

        public void setTime(String date) {
            dates = mView.findViewById(R.id.rec_time);
            dates.setText(date);
        }

        public void setComp(String cname) {
            company = mView.findViewById(R.id.rec_company);
            company.setText(cname);
        }

        public void setAccept(String accepted) {
            accept = mView.findViewById(R.id.rec_accept);
            accept.setText(accepted);
        }

        public void setSal(String salaries) {
            salary = mView.findViewById(R.id.rec_sal);
            salary.setText(salaries);
        }

        public void SetName(String usernames) {
            username = mView.findViewById(R.id.rec_name);
            username.setText(usernames);
        }

        public void updatelikes(int count) {

            blogLikeCount = mView.findViewById(R.id.like_count);
            blogLikeCount.setText(count + " Likes");
        }

    }
}
