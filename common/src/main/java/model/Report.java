package model;

import org.apache.commons.lang.RandomStringUtils;

public class Report {

    private String subject;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Report generate() {
        this.setSubject("subject:" + RandomStringUtils.randomAlphabetic(4));
        return this;
    }
}
