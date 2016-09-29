package model;

import abs.TeelaEntity;
import data_for_entity.annotations.*;
import data_for_entity.data_providers.*;
import data_for_entity.data_types.FieldDataType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.TeelaDate;

import java.util.Date;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Record extends TeelaEntity {
    
    @DataIgnore
    private String sourceId;
    @DataIgnore
    private String sourceName;
    @DataIgnore
    private SourceType sourceType;
    @DataIgnore
    private String sourceLetterCode;
    @DataIgnore
    private String sourceLocation;
    @DataIgnore
    private String typeEnglishName;
    @DataIgnore
    private String typeArabicName;
    @WithDataSize(length = 12)
    @WithFieldDataType(FieldDataType.NUMERIC)
    private String fromNumber;
    @DataIgnore
    private List<String> fromNumberNames;
    @WithDataSize(length = 12)
    @WithFieldDataType(FieldDataType.NUMERIC)
    private String toNumber;
    @DataIgnore
    private List<String> toNumberNames;
    @DataProvider(CountryProvider.class)
    private String fromCountry;
    @DataProvider(CountryProvider.class)
    private String toCountry;
    @DataProvider(LanguageProvider.class)
    private String language;
    @WithDataSize(length = 15)
    private String tmsi;
    @WithDataSize(length = 15)
    private String imsi;
    private String originalId;
    @DataIgnore
    private TeelaDate dateAndTime;
    @WithFieldDataType(FieldDataType.DATE)
    private Date time;
    
    @DataProvider(RecordTypeProvider.class)
    private RecordType type;
    @WithDataDependencies(provider = RecordBodySMSProvider.class, fields = {"type"})
    private String text;
    @WithDataDependencies(provider = RecordBodyVoiceProvider.class, fields = {"type"})
    private int duration;
    private boolean manualEntry = true;
    private int priority=0;
    @DataStatic("READY")
    private String state;
    @DataIgnore
    private String processedStatus;
    @DataIgnore
    private int optimizedDuration;
    @DataIgnore
    private String fileId;
    @DataIgnore
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

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTmsi() {
        return tmsi;
    }

    public void setTmsi(String tmsi) {
        this.tmsi = tmsi;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public TeelaDate getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(TeelaDate dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
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

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceLetterCode() {
        return sourceLetterCode;
    }

    public void setSourceLetterCode(String sourceLetterCode) {
        this.sourceLetterCode = sourceLetterCode;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getTypeEnglishName() {
        return typeEnglishName;
    }

    public void setTypeEnglishName(String typeEnglishName) {
        this.typeEnglishName = typeEnglishName;
    }

    public String getTypeArabicName() {
        return typeArabicName;
    }

    public void setTypeArabicName(String typeArabicName) {
        this.typeArabicName = typeArabicName;
    }

    public List<String> getFromNumberNames() {
        return fromNumberNames;
    }

    public void setFromNumberNames(List<String> fromNumberNames) {
        this.fromNumberNames = fromNumberNames;
    }

    public List<String> getToNumberNames() {
        return toNumberNames;
    }

    public void setToNumberNames(List<String> toNumberNames) {
        this.toNumberNames = toNumberNames;
        
    }

    public int getOptimizedDuration() {
        return optimizedDuration;
    }

    public void setOptimizedDuration(int optimizedDuration) {
        this.optimizedDuration = optimizedDuration;
    }

    public String getFileId() {
        return fileId;
    }

    public void  setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOptimizedFileId() {
        return optimizedFileId;
    }

    public void setOptimizedFileId(String optimizedFileId) {
        this.optimizedFileId = optimizedFileId;
    }
    
}
