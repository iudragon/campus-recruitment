package com.projects.sharath.project;

import java.util.Date;

/**
 * Created by Sharath on 22-Mar-18.
 */

public class JobListModel {
    public String compname;
    public String venue;
    public String ptime;
    public String designation;
    public String salaryrange;
    public String linkweb, compweb;
    public String roundone, roundtwo, roundthree, skills, rounds;


    public String days;
    public Date timestamps;

    public JobListModel() {
    }

    public JobListModel(String compname, String venue, String ptime, String designation, String salaryrange, Date timestamps, String days, String linkweb,
                        String compweb, String roundone, String roundtwo, String roundthree, String skills, String rounds) {
        this.compname = compname;
        this.venue = venue;
        this.ptime = ptime;
        this.designation = designation;
        this.salaryrange = salaryrange;
        this.timestamps = timestamps;
        this.days = days;
        this.linkweb = linkweb;
        this.compweb = compweb;
        this.roundone = roundone;
        this.roundtwo = roundtwo;
        this.roundthree = roundthree;
        this.skills = skills;
        this.rounds = rounds;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getRounds() {
        return rounds;
    }

    public void setRounds(String rounds) {
        this.rounds = rounds;
    }

    public String getRoundtwo() {
        return roundtwo;
    }

    public void setRoundtwo(String roundtwo) {
        this.roundtwo = roundtwo;
    }

    public String getRoundthree() {
        return roundthree;
    }

    public void setRoundthree(String roundthree) {
        this.roundthree = roundthree;
    }

    public String getRoundone() {
        return roundone;
    }

    public void setRoundone(String roundone) {
        this.roundone = roundone;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Date timestamps) {
        this.timestamps = timestamps;
    }

    public String getSalaryrange() {
        return salaryrange;
    }

    public void setSalaryrange(String salaryrange) {
        this.salaryrange = salaryrange;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLinkweb() {
        return linkweb;
    }

    public void setLinkweb(String linkweb) {
        this.linkweb = linkweb;
    }

    public String getCompweb() {
        return compweb;
    }

    public void setCompweb(String compweb) {
        this.compweb = compweb;
    }
}
