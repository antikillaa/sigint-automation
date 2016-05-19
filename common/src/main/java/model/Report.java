package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;

public class Report extends TeelaEntity{

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
