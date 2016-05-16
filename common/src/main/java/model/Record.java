package model;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;
import java.util.Random;

public class Record {

    private String source;
    private String fromNumber;
    private String fromCountry;
    private String toNumber;
    private String toCountry;
    private String language;
    private String TMSI;
    private String IMSI;
    private String recordID;
    private Date dateAndTime;
    private String type;
    private String SMSText;
    private int duration;


    public String getSource() {
        return source;
    }

    public Record setSource(String source) {
        this.source = source;
        return this;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public Record setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
        return this;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public Record setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Record setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getTMSI() {
        return TMSI;
    }

    public Record setTMSI(String TMSI) {
        this.TMSI = TMSI;
        return this;
    }

    public String getIMSI() {
        return IMSI;
    }

    public Record setIMSI(String IMSI) {
        this.IMSI = IMSI;
        return this;
    }

    public String getRecordID() {
        return recordID;
    }

    public Record setRecordID(String recordID) {
        this.recordID = recordID;
        return this;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public Record setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public String getToNumber() {
        return toNumber;
    }

    public Record setToNumber(String toNumber) {
        this.toNumber = toNumber;
        return this;
    }

    public String getToCountry() {
        return toCountry;
    }

    public Record setToCountry(String toCountry) {
        this.toCountry = toCountry;
        return this;
    }

    public String getSMSText() {
        return SMSText;
    }

    public Record setSMSText(String SMSText) {
        this.SMSText = SMSText;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Record setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getType() {
        return type;
    }

    public Record setType(String type) {
        this.type = type;
        return this;
    }

    public String durationToString() {
        int hours = getDuration() / 3600;
        int minutes = (getDuration() % 3600) / 60;
        int seconds = getDuration() % 60;

        if ( getDuration() > 0 && getDuration() < 60 ) { return String.format("%ds", seconds); }
        else if ( getDuration() >= 60 && getDuration() < 300 ) { return String.format("%dm %ds", minutes, seconds); }
        else if ( getDuration() >= 300 && getDuration() < 3600 ) { return String.format("%dm", minutes); }
        else if ( getDuration() >= 3600 ) { return String.format("%dh %dm", hours, minutes); }

        return "-";
    }

    public Record generate() {
        this
                .setSMSText(RandomStringUtils.randomAlphabetic(30))
                .setDuration(new Random().nextInt(9999))
                .setIMSI(RandomStringUtils.randomAlphabetic(30))
                .setTMSI(RandomStringUtils.randomAlphabetic(30))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setRecordID(RandomStringUtils.randomAlphanumeric(8))
                .setDateAndTime(new Date());
        return this;
    }

}
