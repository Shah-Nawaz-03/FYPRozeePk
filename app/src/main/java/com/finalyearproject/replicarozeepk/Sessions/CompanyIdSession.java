package com.finalyearproject.replicarozeepk.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class CompanyIdSession {
    SharedPreferences CompanyIdshare;
    SharedPreferences.Editor editorCompanyId;
    Context contextCid;

    public static final String KEY_NAME = "id";
    public CompanyIdSession(Context _context){
        contextCid = _context;
        CompanyIdshare = contextCid.getSharedPreferences("CompanyIdSession",Context.MODE_PRIVATE);
        editorCompanyId = CompanyIdshare.edit();
    }
    public void createIdsession(String id){
           editorCompanyId.putString(KEY_NAME,id);
           editorCompanyId.apply();
    }
    public HashMap<String, String> getcompanyId(){
        HashMap<String, String> CompanyId = new HashMap<>();
        CompanyId.put(KEY_NAME,CompanyIdshare.getString(KEY_NAME,null));
        return CompanyId;
    }
}
