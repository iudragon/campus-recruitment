package com.projects.sharath.project;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

/**
 * Created by Sharath on 25-Apr-18.
 */

public class BlogPostId {

    @Exclude
    public String BlogPostId;

    public <T extends BlogPostId> T withId(@NonNull final  String id)
    {
        this.BlogPostId = id;
        return (T) this;
    }
 }
