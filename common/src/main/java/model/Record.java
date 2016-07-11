package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;
import utils.TeelaDate;

import java.util.Date;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Record extends TeelaEntity {

    private String sourceId;
    private String sourceName;
    private SourceType sourceType;
    private String sourceLetterCode;
    private String sourceLocation;
    private String typeEnglishName;
    private String typeArabicName;
    private String fromNumber;
    private List<String> fromNumberNames;
    private String toNumber;
    private List<String> toNumberNames;
    private String fromCountry;
    private String toCountry;
    private String language;
    private String tmsi;
    private String imsi;
    private String originalId;
    private TeelaDate dateAndTime;
    private Date time;
    private RecordType type;
    private String text;
    private int duration;
    private boolean manualEntry = true;
    private int priority;
    private String state;
    private String processedStatus;
    private int optimizedDuration;
    private String fileId;
    private String optimizedFileId;

    public String getProcessedStatus() {
        return processedStatus;
    }

    public void setProcessedStatus(String processedStatus) {
        this.processedStatus = processedStatus;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Record setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public String getTmsi() {
        return tmsi;
    }

    public Record setTmsi(String tmsi) {
        this.tmsi = tmsi;
        return this;
    }

    public String getImsi() {
        return imsi;
    }

    public Record setImsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    public String getOriginalId() {
        return originalId;
    }

    public Record setOriginalId(String originalId) {
        this.originalId = originalId;
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

    public RecordType getType() {
        return type;
    }

    public Record setType(RecordType type) {
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

    public SourceType getSourceType() {
        return sourceType;
    }

    public Record setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public String getSourceLetterCode() {
        return sourceLetterCode;
    }

    public Record setSourceLetterCode(String sourceLetterCode) {
        this.sourceLetterCode = sourceLetterCode;
        return this;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public Record setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
        return this;
    }

    public String getTypeEnglishName() {
        return typeEnglishName;
    }

    public Record setTypeEnglishName(String typeEnglishName) {
        this.typeEnglishName = typeEnglishName;
        return this;
    }

    public String getTypeArabicName() {
        return typeArabicName;
    }

    public Record setTypeArabicName(String typeArabicName) {
        this.typeArabicName = typeArabicName;
        return this;
    }

    public List<String> getFromNumberNames() {
        return fromNumberNames;
    }

    public Record setFromNumberNames(List<String> fromNumberNames) {
        this.fromNumberNames = fromNumberNames;
        return this;
    }

    public List<String> getToNumberNames() {
        return toNumberNames;
    }

    public Record setToNumberNames(List<String> toNumberNames) {
        this.toNumberNames = toNumberNames;
        return this;
    }

    public int getOptimizedDuration() {
        return optimizedDuration;
    }

    public Record setOptimizedDuration(int optimizedDuration) {
        this.optimizedDuration = optimizedDuration;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public Record setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getOptimizedFileId() {
        return optimizedFileId;
    }

    public Record setOptimizedFileId(String optimizedFileId) {
        this.optimizedFileId = optimizedFileId;
        return this;
    }

    public Record generate() {
        this
                .setImsi(RandomStringUtils.randomNumeric(15))
                .setTmsi(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setOriginalId(RandomStringUtils.randomAlphanumeric(8))
                .setDateAndTime(new TeelaDate())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage());
        if (type.equals(RecordType.SMS)) {
            this.setText(RandomStringUtils.randomAlphabetic(30));
        } else if (type.equals(RecordType.Voice)) {
            this.setDuration(RandomGenerator.getRandomDuration());
        }
        return this;
    }

    public Record generateSMS() {
        this
                .setImsi(RandomStringUtils.randomNumeric(15))
                .setTmsi(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setOriginalId(RandomStringUtils.randomAlphanumeric(8))
                .setTime(new Date())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage())
                .setType(RecordType.SMS)
                .setText(RandomStringUtils.randomAlphabetic(30));
        return this;
    }

    public Record generateVoice() {
        this
                .setImsi(RandomStringUtils.randomNumeric(15))
                .setTmsi(RandomStringUtils.randomNumeric(15))
                .setFromNumber(RandomStringUtils.randomNumeric(12))
                .setToNumber(RandomStringUtils.randomNumeric(12))
                .setOriginalId(RandomStringUtils.randomAlphanumeric(8))
                .setTime(new Date())
                .setFromCountry(RandomGenerator.getRandomCountry())
                .setToCountry(RandomGenerator.getRandomCountry())
                .setLanguage(RandomGenerator.getRandomLanguage())
                .setType(RecordType.Voice)
                .setDuration(RandomGenerator.getRandomDuration());
        return this;
    }

}
