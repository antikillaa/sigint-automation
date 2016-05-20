package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;

public class Report extends TeelaEntity{

    private String subject;
    private String reportID;
    private String owner;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Report generate() {
        this.setSubject("subject:" + RandomStringUtils.randomAlphabetic(4));
        return this;
    }
}
