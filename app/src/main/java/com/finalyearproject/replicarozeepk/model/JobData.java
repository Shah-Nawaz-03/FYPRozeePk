package com.finalyearproject.replicarozeepk.model;
public class JobData {
    public String id;
    public String jobtitle;
    public String experience;
    public String company;
    public String jobdate;
    public String jobdescription;
    public String joblastdate;
    public String city;
    public String skills;
    public String careerlevel;
    public String salaryrange;
    public String jobtype;
    public String requirededucation;
    public String gender;
    public String companyid;
    public String userImage;
    public String jstatus;
    public String cgpa;
    public String uni;

    public JobData(String id, String jobtitle, String experience, String company, String jobdate, String jobdescription, String joblastdate, String city, String skills, String careerlevel, String salaryrange, String jobtype, String requirededucation, String gender, String companyid, String userImage, String jstatus, String cgpa, String uni) {
        this.id = id;
        this.jobtitle = jobtitle;
        this.experience = experience;
        this.company = company;
        this.jobdate = jobdate;
        this.jobdescription = jobdescription;
        this.joblastdate = joblastdate;
        this.city = city;
        this.skills = skills;
        this.careerlevel = careerlevel;
        this.salaryrange = salaryrange;
        this.jobtype = jobtype;
        this.requirededucation = requirededucation;
        this.gender = gender;
        this.companyid = companyid;
        this.userImage = userImage;
        this.jstatus = jstatus;
        this.cgpa = cgpa;
        this.uni = uni;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public JobData(){
    }
    public JobData(String id, String jobtitle, String experience, String company, String jobdate, String jobdescription, String joblastdate, String city, String skills, String careerlevel, String salaryrange, String jobtype, String requirededucation, String gender, String companyid, String userImage, String jstatus) {
        this.id = id;
        this.jobtitle = jobtitle;
        this.experience = experience;
        this.company = company;
        this.jobdate = jobdate;
        this.jobdescription = jobdescription;
        this.joblastdate = joblastdate;
        this.city = city;
        this.skills = skills;
        this.careerlevel = careerlevel;
        this.salaryrange = salaryrange;
        this.jobtype = jobtype;
        this.requirededucation = requirededucation;
        this.gender = gender;
        this.companyid = companyid;
        this.userImage = userImage;
        this.jstatus = jstatus;
    }
    @Override
    public String toString() {
        return "JobData{" +
                "id='" + id + '\'' +
                ", jobtitle='" + jobtitle + '\'' +
                ", experience='" + experience + '\'' +
                ", company='" + company + '\'' +
                ", jobdate='" + jobdate + '\'' +
                ", jobdescription='" + jobdescription + '\'' +
                ", joblastdate='" + joblastdate + '\'' +
                ", city='" + city + '\'' +
                ", skills='" + skills + '\'' +
                ", careerlevel='" + careerlevel + '\'' +
                ", salaryrange='" + salaryrange + '\'' +
                ", jobtype='" + jobtype + '\'' +
                ", requirededucation='" + requirededucation + '\'' +
                ", gender='" + gender + '\'' +
                ", companyid='" + companyid + '\'' +
                ", userImage='" + userImage + '\'' +
                ", jstatus='" + jstatus + '\'' +
                '}';
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
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

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getJoblastdate() {
        return joblastdate;
    }

    public void setJoblastdate(String joblastdate) {
        this.joblastdate = joblastdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCareerlevel() {
        return careerlevel;
    }

    public void setCareerlevel(String careerlevel) {
        this.careerlevel = careerlevel;
    }

    public String getSalaryrange() {
        return salaryrange;
    }

    public void setSalaryrange(String salaryrange) {
        this.salaryrange = salaryrange;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getRequirededucation() {
        return requirededucation;
    }

    public void setRequirededucation(String requirededucation) {
        this.requirededucation = requirededucation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getJstatus() {
        return jstatus;
    }

    public void setJstatus(String jstatus) {
        this.jstatus = jstatus;
    }
}

