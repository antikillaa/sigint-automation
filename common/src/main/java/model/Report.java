package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;

public class Report extends TeelaEntity{

    private String subject;
    private String reportID;
    private String owner;
    private String sourceType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getRecordType() {
        return recordType;
    }

    public Report setRecordType(String recordType) {
        this.recordType = recordType;
        return this;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    private String recordType;


    public String getSubject() {
        return subject;
    }

    public Report setSubject(String subject) {
        this.subject = subject;
        return this;
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
