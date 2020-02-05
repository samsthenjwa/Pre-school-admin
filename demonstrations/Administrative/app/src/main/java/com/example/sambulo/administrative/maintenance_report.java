package com.example.sambulo.administrative;

import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by SAMBULO on 06-Oct-17.
 */

public class maintenance_report {
    private String maintitle;
    private String problem;
    private String description;
    private String status;
    private String objectId;
    private String ownerId;
    private String created;
    private String updated;

    public maintenance_report() {

    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getObjectId() {
        return objectId;
    }
}
