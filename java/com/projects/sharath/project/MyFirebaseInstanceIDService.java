package com.projects.sharath.project;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Sharath on 31-Mar-18.
 */

public class MyFirebaseInstanceIDService extends com.google.firebase.iid.FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", refreshedToken);

    }

}
