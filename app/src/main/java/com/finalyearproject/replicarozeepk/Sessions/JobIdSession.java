package com.finalyearproject.replicarozeepk.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class JobIdSession {
    SharedPreferences jobid;
    SharedPreferences.Editor jobidEditor;
    Context contextJobid;
    public static final String KEY_JID = "JobId";
    public JobIdSession(Context context){
        contextJobid = context;
        jobid = contextJobid.getSharedPreferences("jobIdSession", Context.MODE_PRIVATE);
        jobidEditor = jobid.edit();
    }
    public void createJidSession(String jid){
        jobidEditor.putString(KEY_JID, jid);
        jobidEditor.apply();
    }
    public HashMap<String, String> getJobid(){
        HashMap<String, String> Jobid = new HashMap<>();
        Jobid.put(KEY_JID, jobid.getString(KEY_JID, null));
        return Jobid;
    }
}
