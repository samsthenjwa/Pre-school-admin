package com.example.sambulo.administrative;

import java.util.Date;



public class Learner {

    private String learnerName;
    private String learnerSurname;
    private String gender;
    private int learnerId;
    private String race;
    private String shift;
    private String className;
    private String dateOfBirth;
    private Date dateCreated;
    private Date dateUpdated;
    private String learnerAddress;
    private String motherName;
    private String motherEmail;
    private String motherPhone;
    private String fatherName;
    private String fatherEmail;
    private String fatherPhone;
    private String docName;
    private String docPhone;
    private String medName;
    private String medPlan;
    private String medNumber;
    private String allergies;
    private double tuckBalance;
    private String objectId;



    private double currentTransaction;
    private double afterTransaction;





    public Learner()
    {

    }
    public Learner(String name, String surename,String room,double bal){
        this. learnerName=name;
        this.learnerSurname=surename;
        this.className=room;
        this.tuckBalance=bal;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(int learnerId) {
        this.learnerId = learnerId;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getLearnerSurname() {
        return learnerSurname;
    }

    public void setLearnerSurname(String learnerSurname) {
        this.learnerSurname = learnerSurname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getLearnerAddress() {
        return learnerAddress;
    }

    public void setLearnerAddress(String learnerAddress) {
        this.learnerAddress = learnerAddress;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherEmail() {
        return motherEmail;
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail = motherEmail;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherEmail() {
        return fatherEmail;
    }

    public void setFatherEmail(String fatherEmail) {
        this.fatherEmail = fatherEmail;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocPhone() {
        return docPhone;
    }

    public void setDocPhone(String docPhone) {
        this.docPhone = docPhone;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedPlan() {
        return medPlan;
    }

    public void setMedPlan(String medPlan) {
        this.medPlan = medPlan;
    }

    public String getMedNumber() {
        return medNumber;
    }

    public void setMedNumber(String medNumber) {
        this.medNumber = medNumber;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public double getTuckBalance() {
        return tuckBalance;
    }

    public void setTuckBalance(double tuckBalance) {
        this.tuckBalance = tuckBalance;
    }

    public double getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(double currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public double getAfterTransaction() {
        return afterTransaction;
    }

    public void setAfterTransaction(double afterTransaction) {
        this.afterTransaction = afterTransaction;
    }
}
