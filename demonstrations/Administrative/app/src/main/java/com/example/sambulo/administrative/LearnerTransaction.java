package com.example.sambulo.administrative;

import java.io.Serializable;

/**
 * Created by Mokgako on 2017/10/14.
 */

public class LearnerTransaction implements Serializable{


    private double transaction;
    private double afterTransaction;
    private double currentBalance;
    private String learnerName;
    private String learnerSurname;
    private String className;
    private String objectId;
    private String ownerId;




    public LearnerTransaction() {

        transaction=0.0;
        afterTransaction=0.0;
        learnerName=null;
        learnerSurname=null;
        className=null;



    }


    public double getTransaction() {
        return transaction;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    public double getAfterTransaction() {
        return afterTransaction;
    }

    public void setAfterTransaction(double afterTransaction) {
        this.afterTransaction = afterTransaction;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }



    public String getObjectId() {
        return objectId;
    }

    public String getLearnerSurname() {
        return learnerSurname;
    }

    public void setLearnerSurname(String learnerSurname) {
        this.learnerSurname = learnerSurname;
    }


}
