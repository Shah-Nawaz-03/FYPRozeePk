package com.finalyearproject.replicarozeepk.retrofit;

import com.finalyearproject.replicarozeepk.model.AppliedJobData;
import com.finalyearproject.replicarozeepk.model.JobData;
import com.finalyearproject.replicarozeepk.model.QuestionData;
import com.finalyearproject.replicarozeepk.model.QuizData;
import com.finalyearproject.replicarozeepk.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("users/get")
    Call<ArrayList> login(@Query("email") String email, @Query("pass") String pass);

    @POST("users/post")
    Call<Object> signupjs(@Body UserData data);

    @POST("users/post")
    Call<Object> signup(@Body UserData data);

    @PUT("appliedjobs/setStatus")
    Call<Void> setStatus(@Query("status")
                                   String status, @Query("jobid")
                                   String jobid, @Query("eid") String eid );

    @GET("applieddata/appliedValidation")
    Call<ArrayList> applied(@Query("jid") String jobid, @Query("eid") String eid);

    @GET("applieddata/status")
    Call<ArrayList> status(@Query("id") String id);

    @POST("AppliedJobs/post")
    Call<Object> applyforJob(@Body AppliedJobData data);

    @GET("applieddata/getjobstatus")
    Call<ArrayList> jobStatus(@Query("companyid") String companyid);

    @POST("jobs/post")
    Call<Object> postJob(@Body JobData jobData);

    @GET("jobs/jobsposted")
    Call<ArrayList> getJobs(@Query("companyid") String companyid);

    @GET("searchjobs/get")
    Call<ArrayList> searchJobs(@Query("jobtitle") String jobtitle, @Query("city") String city);

    @GET("searchjobs/get")
    Call<ArrayList> searchJobswithtitle(@Query("jobtitle")String jobtitle);

    @GET("matchedjobs/get")
    Call<ArrayList> matchedJobs(@Query("skill")String skill);

    @GET("jobs/getjob")
    Call<ArrayList> jobDetail(@Query("id") String id);

    @GET("appliedjobs/getapplicants")
    Call<ArrayList> applicants(@Query("jid") String jid);

    //applicantswithuni
    @GET("appliedjobs/uni")
    Call<ArrayList> applicantsUni(@Query("uni") String uni, @Query("jid") String jid);

    //applicantswithcgpa
    @GET("appliedjobs/cgpa")
    Call<ArrayList> applicantsCgpa(@Query("cgpa") String cgpa, @Query("jid") String jid);

    //withuniandcgpa
    @GET("appliedjobs/cgpaanduni")
    Call<ArrayList> cgpaanduni(@Query("cgpa") String cgpa,
                               @Query("uni") String uni, @Query("jid") String jid);


    //as commitee says

    @GET("matchedjobs/profilematched")
    Call<ArrayList> profileMatched(@Query("skill") String skill,
                                   @Query("cgpa") String cgpa,
                                   @Query("uni") String uni);
    //quiz interfaces...

    @POST("question/addquestion")
    Call<Object> addquestion(@Body QuestionData questionData);

    @POST("quiz/createquiz")
    Call<Object> createquiz(@Body QuizData data);

    @POST("newquiz/add")
    Call<Object> add(@Query("qid") String quizid, @Query("questionid") String questionid);

    //get quizid

    @GET("quiz/getquiz")
    Call<ArrayList> getquizid(@Query("id") String id);

    //get questionid

    @GET("question/getquestion")
    Call<ArrayList> getquestionid(@Query("id") String id);

    @GET("quiz/showquiz")
    Call<ArrayList> showquiz(@Query("id") String id);

//    @Multipart
//    @POST("/api/register")
//    Call<Object> createProfile(@Part MultipartBody.Part file,
//                               @Part ("first_name") RequestBody firstName,
//                               @Part ("last_name") RequestBody lastName,
//                               @Part ("phone_no") RequestBody phoneNumber,
//                               @Part ("dob") RequestBody dateOfBirth,
//                               @Part ("address") RequestBody address,
//                               @Part ("description") RequestBody description,
//                               @Part ("gender") RequestBody gender);
//

//    @FormUrlEncoded
//    @POST("api/login-tailor")
//    Call<Object> loginTailor(@Field("mobile_number") String mobile_number);

//    @Multipart
//    @POST("api/register-tailor")
//    Call<Object> registerTailor(@Part MultipartBody.Part image,
//                                @Part("name") RequestBody name,
//                                @Part("cnic") RequestBody cnic,
//                                @Part("mobile_number") RequestBody mobile_number,
//                                @Part("gender") RequestBody gender,
//                                @Part("address") RequestBody address,
//                                @Part("latitude") RequestBody latitude,
//                                @Part("longitude") RequestBody longitude,
//                                @Part("stitching_type") RequestBody stitching_type,
//                                @Part("shop_name") RequestBody shop_name,
//                                @Part("shop_phone") RequestBody shop_phone);
//
//    @Multipart
//    @POST("api/register-customer")
//    Call<Object> registerCustomer(@Part MultipartBody.Part image,
//                                @Part("name") RequestBody name,
//                                @Part("m_number") RequestBody m_number,
//                                @Part("gender") RequestBody gender,
//                                @Part("address") RequestBody address
//                                  );
//
//    @FormUrlEncoded
//    @POST("api/login-customer")
//    Call<Object> loginCustomer(@Field("m_number") String m_number);
//

}
