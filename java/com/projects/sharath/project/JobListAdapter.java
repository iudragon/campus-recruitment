package com.projects.sharath.project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by Sharath on 22-Mar-18.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    public List<JobListModel> jobListModels;
    public Context context;
    private FirebaseFirestore firebaseFirestore;

    public JobListAdapter(List<JobListModel> jobListModels, Context context) {
        this.jobListModels = jobListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_items, parent, false);
        context = view.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String comp_data = jobListModels.get(position).getCompname();
        holder.SetName(comp_data);

        final String poster_salary = jobListModels.get(position).getSalaryrange();
        holder.SetSalary("₹" + "  " + poster_salary);

        long milliseconds = jobListModels.get(position).getTimestamps().getTime();
        final String dateString = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm aa", new Date(milliseconds)).toString();
        holder.SetPostTime(dateString);

        final String designation_data = jobListModels.get(position).getDesignation();
        holder.SetDesignation(designation_data);

        final String venue_data = jobListModels.get(position).getVenue();
        holder.SetVenue(venue_data);

        final String timings_data = jobListModels.get(position).getPtime();
        holder.SetTimings(timings_data);

        final String event_date = jobListModels.get(position).getDays();
        holder.SetDate(event_date);

        final String job_apply = jobListModels.get(position).getLinkweb();
        holder.SetLink(job_apply);

        final String job_comp = jobListModels.get(position).getCompweb();
        holder.SetLink(job_comp);

        final String job_r1 = jobListModels.get(position).getRoundone();
        holder.SetRoundo(job_r1);

        final String job_r2 = jobListModels.get(position).getRoundtwo();
        holder.SetRoundt(job_r2);

        final String job_r3 = jobListModels.get(position).getRoundthree();
        holder.SetRoundth(job_r3);

        final String job_r = jobListModels.get(position).getRounds();
        holder.Setround(job_r);

        final String job_skills = jobListModels.get(position).getSkills();
        holder.Setskills(job_skills);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), JobFullActivity.class);
                intent.putExtra("company", comp_data);
                intent.putExtra("role", designation_data);
                intent.putExtra("salary", poster_salary);
                intent.putExtra("venue", venue_data);
                intent.putExtra("date", event_date);
                intent.putExtra("timings", timings_data);
                intent.putExtra("timestamp", dateString);
                intent.putExtra("linkweb", job_apply);
                intent.putExtra("compweb", job_comp);
                intent.putExtra("roundone", job_r1);
                intent.putExtra("roundtwo", job_r2);
                intent.putExtra("roundthree", job_r3);
                intent.putExtra("rounds", job_r);
                intent.putExtra("skills", job_skills);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView company, design, timings, venue, salaryrange, posttime, dates, linkweb2, linkweb1;
        private TextView r1, r2, r3, r, req;
        private RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            relativeLayout = mView.findViewById(R.id.rell1);
        }

        public void Setround(String rs) {
            r = mView.findViewById(R.id.rounds);
            r.setText(rs);
        }

        public void Setskills(String reqs) {
            req = mView.findViewById(R.id.req_skills);
            req.setText(reqs);
        }


        public void SetRoundo(String rr1) {
            r1 = mView.findViewById(R.id.rounds1);
            r1.setText(rr1);
        }

        public void SetRoundt(String rr2) {
            r2 = mView.findViewById(R.id.rounds2);
            r2.setText(rr2);
        }

        public void SetRoundth(String rr3) {
            r3 = mView.findViewById(R.id.rounds3);
            r3.setText(rr3);
        }

        public void SetName(String companyname) {
            company = mView.findViewById(R.id.comp_name);
            company.setText(companyname);
        }

        public void SetSalary(String salary) {
            salaryrange = mView.findViewById(R.id.salaries);
            salaryrange.setText(salary);
        }

        public void SetPostTime(String poststime) {
            posttime = mView.findViewById(R.id.officer_time);
            posttime.setText(poststime);
        }

        public void SetDesignation(String desig) {
            design = mView.findViewById(R.id.designation);
            design.setText(desig);
        }

        public void SetVenue(String place) {
            venue = mView.findViewById(R.id.venue);
            venue.setText(place);
        }

        public void SetTimings(String timing) {
            timings = mView.findViewById(R.id.timings);
            timings.setText(timing);
        }

        public void SetDate(String date) {
            dates = mView.findViewById(R.id.ret_date);
            dates.setText(date);
        }

        public void SetLink(String linkt) {
            linkweb2 = mView.findViewById(R.id.linkweb2);
            linkweb2.setText(linkt);
        }

        public void SetCLink(String linkc) {
            linkweb1 = mView.findViewById(R.id.compweb2);
            linkweb1.setText(linkc);
        }
    }
}
