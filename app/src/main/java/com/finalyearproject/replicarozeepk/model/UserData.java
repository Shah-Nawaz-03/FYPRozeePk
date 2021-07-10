package com.finalyearproject.replicarozeepk.model;
public class UserData {
    public String companyid;
    public String name;
    public String email;
    public String pass;
    public String gender;
    public String usertype;
    public String experience;
    public String expectedsalary;
    public String city;
    public String careerlevel;
    public String userimage;
    public String skills;
    public String designation;
    public String education;
    public String cgpa;
    public String uni;

    public UserData() {
    }

    public UserData(String companyid, String name, String email,
                    String pass, String gender, String usertype,
                    String designation,String experience, String expectedsalary,String careerlevel, String city,
                     String userimage, String skills, String education,
                    String cgpa, String uni) {

        this.companyid = companyid;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.gender = gender;
        this.usertype = usertype;
        this.experience = experience;
        this.expectedsalary = expectedsalary;
        this.city = city;
        this.careerlevel = careerlevel;
        this.userimage = userimage;
        this.skills = skills;
        this.designation = designation;
        this.education = education;
        this.cgpa = cgpa;
        this.uni = uni;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "companyid='" + companyid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", gender='" + gender + '\'' +
                ", usertype='" + usertype + '\'' +
                ", experience='" + experience + '\'' +
                ", expectedsalary='" + expectedsalary + '\'' +
                ", city='" + city + '\'' +
                ", careerlevel='" + careerlevel + '\'' +
                ", userimage='" + userimage + '\'' +
                ", skills='" + skills + '\'' +
                ", designation='" + designation + '\'' +
                ", education='" + education + '\'' +
                ", cgpa='" + cgpa + '\'' +
                ", uni='" + uni + '\'' +
                '}';
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(String expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCareerlevel() {
        return careerlevel;
    }

    public void setCareerlevel(String careerlevel) {
        this.careerlevel = careerlevel;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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
}