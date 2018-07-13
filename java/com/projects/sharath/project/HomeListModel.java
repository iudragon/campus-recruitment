package com.projects.sharath.project;

import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Sharath on 19-Mar-18.
 */

public class HomeListModel extends  BlogPostId{
    public String userid, experience, company, result, salary;
    public Date timestamp;

    public HomeListModel() {
    }

    public HomeListModel(String userid, String experience, String company, String result, String salary, Date timestamp) {
        this.userid = userid;
        this.experience = experience;
        this.company = company;
        this.result = result;
        this.salary = salary;
        this.timestamp = timestamp;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
