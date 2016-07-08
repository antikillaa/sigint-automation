package model;


import abs.TeelaEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Abbreviated record stored as part of the report.
 *
 * The id field can be used to reference the original record.  The time/type fields can be used to determine the
 * index name and type that the record is in.  If the manualEntry field is set, the record is assumed to have been
 * entered manually and will not have an id.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ReportRecord extends TeelaEntity {

    private String sourceId;
    private SourceType sourceType;
    private String sourceLetterCode;
    private String sourceName;
    private String sourceLocation;
    private RecordType type;
    private String typeEnglishName;
    private String typeArabicName;
    private Date time;
    private String fromNumber;
    private List<String> fromNumberNames;
    private String toNumber;
    private List<String> toNumberNames;
    private String imsi;
    private String tmsi;
    private String originalId;
    private String text;
    private Long duration;
    private Long optimizedDuration;
    private String fileId;
    private String optimizedFileId;
    private Boolean manualEntry;

    public String getSourceId() {
        return sourceId;
    }

    public ReportRecord setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public ReportRecord setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public String getSourceLetterCode() {
        return sourceLetterCode;
    }

    public ReportRecord setSourceLetterCode(String sourceLetterCode) {
        this.sourceLetterCode = sourceLetterCode;
        return this;
    }

    public String getSourceName() {
        return sourceName;
    }

    public ReportRecord setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public ReportRecord setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
        return this;
    }

    public RecordType getType() {
        return type;
    }

    public ReportRecord setType(RecordType type) {
        this.type = type;
        return this;
    }

    public String getTypeEnglishName() {
        return typeEnglishName;
    }

    public ReportRecord setTypeEnglishName(String typeEnglishName) {
        this.typeEnglishName = typeEnglishName;
        return this;
    }

    public String getTypeArabicName() {
        return typeArabicName;
    }

    public ReportRecord setTypeArabicName(String typeArabicName) {
        this.typeArabicName = typeArabicName;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public ReportRecord setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public ReportRecord setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
        return this;
    }

    public List<String> getFromNumberNames() {
        return fromNumberNames;
    }

    public ReportRecord setFromNumberNames(List<String> fromNumberNames) {
        this.fromNumberNames = fromNumberNames;
        return this;
    }

    public String getToNumber() {
        return toNumber;
    }

    public ReportRecord setToNumber(String toNumber) {
        this.toNumber = toNumber;
        return this;
    }

    public List<String> getToNumberNames() {
        return toNumberNames;
    }

    public ReportRecord setToNumberNames(List<String> toNumberNames) {
        this.toNumberNames = toNumberNames;
        return this;
    }

    public String getImsi() {
        return imsi;
    }

    public ReportRecord setImsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    public String getTmsi() {
        return tmsi;
    }

    public ReportRecord setTmsi(String tmsi) {
        this.tmsi = tmsi;
        return this;
    }

    public String getOriginalId() {
        return originalId;
    }

    public ReportRecord setOriginalId(String originalId) {
        this.originalId = originalId;
        return this;
    }

    public String getText() {
        return text;
    }

    public ReportRecord setText(String text) {
        this.text = text;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public ReportRecord setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Long getOptimizedDuration() {
        return optimizedDuration;
    }

    public ReportRecord setOptimizedDuration(Long optimizedDuration) {
        this.optimizedDuration = optimizedDuration;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public ReportRecord setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getOptimizedFileId() {
        return optimizedFileId;
    }

    public ReportRecord setOptimizedFileId(String optimizedFileId) {
        this.optimizedFileId = optimizedFileId;
        return this;
    }

    public Boolean getManualEntry() {
        return manualEntry;
    }

    public ReportRecord setManualEntry(Boolean manualEntry) {
        this.manualEntry = manualEntry;
        return this;
    }

    @Override
    public ReportRecord generate() {
        this
                .setManualEntry(true)
                .setTime(new Date())
                .setType(RecordType.getRandom())
                .setText("");
        return this;
    }

}
