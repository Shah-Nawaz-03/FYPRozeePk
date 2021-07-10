package com.finalyearproject.replicarozeepk.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerSignup {
    SharedPreferences signUpSessionJs;
    SharedPreferences.Editor editorSignupJs;
    Context contextSignUpJs;

    public static final String KEY_ID = "companyid";
    public static final String KEY_NAME = "name";
    public static final String KEY_CITY = "city";
    public static final String KEY_CGPA = "cgpa";
    public static final String KEY_UNI = "uni";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_SKILLS = "skills";
    public static final String KEY_JOBTITLE = "jobTitle";
    public static final String KEY_EXPECTEDSALARY = "expectedSalary";
    public static final String KEY_IMAGE = "image";
    public SessionManagerSignup(Context _context) {
        contextSignUpJs = _context;
        signUpSessionJs = contextSignUpJs.getSharedPreferences("userSignUpSession", Context.MODE_PRIVATE);
        editorSignupJs = signUpSessionJs.edit();
    }

    public void empSignUpSession(String id,String name,String email, String city, String image) {

        editorSignupJs.putString(KEY_ID,id);
        editorSignupJs.putString(KEY_NAME, name);
        editorSignupJs.putString(KEY_EMAIL, email);
        editorSignupJs.putString(KEY_CITY, city);
        editorSignupJs.putString(KEY_IMAGE, image);

        editorSignupJs.commit();
    }
    public void createSignUpSession(String companyid,String name, String cgpa,
                                    String skills, String uni,
                                    String expectedSalary, String image) {
        editorSignupJs.putString(KEY_ID, companyid);
        editorSignupJs.putString(KEY_NAME, name);
        editorSignupJs.putString(KEY_CGPA, cgpa);
        editorSignupJs.putString(KEY_SKILLS, skills);
        editorSignupJs.putString(KEY_UNI, uni);
        editorSignupJs.putString(KEY_EXPECTEDSALARY, expectedSalary);
        editorSignupJs.putString(KEY_IMAGE, image);

        editorSignupJs.apply();
    }
    public HashMap<String, String> getEmpData() {
        HashMap<String, String> empData = new HashMap<String, String>();

        empData.put(KEY_ID,signUpSessionJs.getString(KEY_ID,null));
        empData.put(KEY_NAME, signUpSessionJs.getString(KEY_NAME, null));
        empData.put(KEY_EMAIL, signUpSessionJs.getString(KEY_EMAIL, null));
        empData.put(KEY_CITY, signUpSessionJs.getString(KEY_CITY, null));
        empData.put(KEY_IMAGE, signUpSessionJs.getString(KEY_IMAGE, null));
        return empData;
    }
    public HashMap<String, String> getUsersData() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_ID, signUpSessionJs.getString(KEY_ID, null));
        userData.put(KEY_NAME, signUpSessionJs.getString(KEY_NAME, null));
        userData.put(KEY_CGPA, signUpSessionJs.getString(KEY_CGPA, null));
        userData.put(KEY_UNI, signUpSessionJs.getString(KEY_UNI,null));
        userData.put(KEY_SKILLS, signUpSessionJs.getString(KEY_SKILLS, null));
        userData.put(KEY_JOBTITLE, signUpSessionJs.getString(KEY_JOBTITLE, null));
        userData.put(KEY_EXPECTEDSALARY, signUpSessionJs.getString(KEY_EXPECTEDSALARY, null));
        userData.put(KEY_IMAGE, signUpSessionJs.getString(KEY_IMAGE, null));
        return userData;
    }
}
