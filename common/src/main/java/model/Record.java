package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;
import utils.TeelaDate;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Record extends TeelaEntity {

    private String source;
    private String fromNumber;
    private String fromCountry;
    private String toNumber;
    private String toCountry;
    private String language;
    private String TMSI;
    private String IMSI;
    @JsonProperty("originalId")
    private String recordID;
    private TeelaDate dateAndTime;
    private Date time;
    private String type;
    private String text;
    private int duration;
    private boolean manualEntry = true;
    private int priority;
    private String state;
    private String sourceId;
    private String processedStatus;

    public String getProcessedStatus() {
        return processedStatus;
    }

    public void setProcessedStatus(String processedStatus) {
        this.processedStatus = processedStatus;
    }

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

    public TeelaDate getDateAndTime() {
        return dateAndTime;
    }

    public Record setDateAndTime(TeelaDate dateAndTime) {
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

    public String getText() {
        return text;
    }

    public Record setText(String text) {
        this.text = text;
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

    public Date getTime() {
        return time;
    }

    public Record setTime(Date time) {
        this.time = time;
        return this;
    }

    public boolean isManualEntry() {
        return manualEntry;
    }

    public Record setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSourceId() {
        return sourceId;
    }

    public Record setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getState() {
        return state;
    }

    public Record setState(String state) {
        this.state = state;
        return this;
    }

    public Record generate() {
        this
                .setIMSI(RandomStringUtils.randomNumeric(15))
                .setTMSI(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setRecordID(RandomStringUtils.randomAlphanumeric(8))
                .setDateAndTime(new TeelaDate())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage());
        if (type.equalsIgnoreCase("sms")) {
            this.setText(RandomStringUtils.randomAlphabetic(30));
        } else if (type.equalsIgnoreCase("voice")) {
            this.setDuration(RandomGenerator.getRandomDuration());
        }
        return this;
    }

    public Record generateSMS() {
        this
                .setIMSI(RandomStringUtils.randomNumeric(15))
                .setTMSI(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setRecordID(RandomStringUtils.randomAlphanumeric(8))
                .setTime(new Date())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage())
                .setType("SMS")
                .setText(RandomStringUtils.randomAlphabetic(30));
        return this;
    }

    public Record generateVoice() {
        this
                .setIMSI(RandomStringUtils.randomNumeric(15))
                .setTMSI(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setRecordID(RandomStringUtils.randomAlphanumeric(8))
                .setTime(new Date())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage())
                .setType("Voice")
                .setDuration(RandomGenerator.getRandomDuration());
        return this;
    }

}
