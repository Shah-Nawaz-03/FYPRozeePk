package com.finalyearproject.replicarozeepk.model;

public class AppliedJobData {
    public String id;
    public String jobid;
    public String companyid;
    public String employeid;
    public String status;
    public AppliedJobData(){
    }
    public AppliedJobData(String jobid, String companyid, String employeid, String status) {
        this.jobid = jobid;
        this.companyid = companyid;
        this.employeid = employeid;
        this.status = status;
    }
    @Override
    public String toString() {
        return "AppliedJobData{" +
                "jobid=" + jobid +
                ", companyid=" + companyid +
                ", employeid=" + employeid +
                ", status='" + status + '\'' +
                '}';
    }
    public String getId() {return id; }
    public void setId(String id){ this.id = id; }
    public String getJobid() {
        return jobid;
    }
    public void setJobid(String  jobid) {
        this.jobid = jobid;
    }
    public String getCompanyid() {
        return companyid;
    }
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
    public String getEmployeid() {
        return employeid;
    }
    public void setEmployeid(String employeid) {
        this.employeid = employeid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
