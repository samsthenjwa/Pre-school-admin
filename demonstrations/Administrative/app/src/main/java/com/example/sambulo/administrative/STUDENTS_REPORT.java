package com.example.sambulo.administrative;

import static android.R.attr.name;

/**
 * Created by SAMBULO on 11-Sep-17.
 */

public class STUDENTS_REPORT
{
   private String names;
   private String surname;
   private String className;
    private String reports;
    private String title;

    private String status;
    private String recomendation;

    public STUDENTS_REPORT() {
    }

    public String getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getReports() {
        return reports;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

