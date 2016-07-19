package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Report extends TeelaEntity {

    private String name;
    private Boolean deleted;
    private Boolean staffIdHidden;
    private ReportStatus status;
    //private Classification classification;
    private String authorId;
    private String authorName;
    private String ownerName;
    private ReportOwner owner;
    //private ReportModificationMetadata authorModification;
    //private ReportModificationMetadata analystModification;
    //private ReportModificationMetadata approverModification;
    private long sequenceNumber;
    private String requestNumber;
    @JsonProperty("reportNumber")
    private String reportID;
    private String metadataString;
    private String subject;
    private String body;
    private String bodyRich;
    private String comments;
    private String commentsRich;
    private String notes;
    private String notesRich;
    private String analystComments;
    private String analystCommentsRich;
    private String analystRecommendations;
    private String analystRecommendationsRich;
    private String analystNotes;
    private String analystNotesRich;
    private String approverComments;
    private String approverCommentsRich;
    private List<ReportCategory> categories = new ArrayList<>();
    private List<String> recordIds = new ArrayList<>();
    private List<Record> reportRecords = new ArrayList<>();
    private List<String> reportTeamIds = new ArrayList<>();
    private Boolean statusChanged;
    private String sourceType;
    private String recordType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getStaffIdHidden() {
        return staffIdHidden;
    }

    public void setStaffIdHidden(Boolean staffIdHidden) {
        this.staffIdHidden = staffIdHidden;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Report setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMetadataString() {
        return metadataString;
    }

    public void setMetadataString(String metadataString) {
        this.metadataString = metadataString;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyRich() {
        return bodyRich;
    }

    public void setBodyRich(String bodyRich) {
        this.bodyRich = bodyRich;
    }

    public String getCommentsRich() {
        return commentsRich;
    }

    public void setCommentsRich(String commentsRich) {
        this.commentsRich = commentsRich;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotesRich() {
        return notesRich;
    }

    public void setNotesRich(String notesRich) {
        this.notesRich = notesRich;
    }

    public String getAnalystComments() {
        return analystComments;
    }

    public void setAnalystComments(String analystComments) {
        this.analystComments = analystComments;
    }

    public String getAnalystCommentsRich() {
        return analystCommentsRich;
    }

    public void setAnalystCommentsRich(String analystCommentsRich) {
        this.analystCommentsRich = analystCommentsRich;
    }

    public String getAnalystRecommendations() {
        return analystRecommendations;
    }

    public void setAnalystRecommendations(String analystRecommendations) {
        this.analystRecommendations = analystRecommendations;
    }

    public String getAnalystRecommendationsRich() {
        return analystRecommendationsRich;
    }

    public void setAnalystRecommendationsRich(String analystRecommendationsRich) {
        this.analystRecommendationsRich = analystRecommendationsRich;
    }

    public String getAnalystNotes() {
        return analystNotes;
    }

    public void setAnalystNotes(String analystNotes) {
        this.analystNotes = analystNotes;
    }

    public String getAnalystNotesRich() {
        return analystNotesRich;
    }

    public void setAnalystNotesRich(String analystNotesRich) {
        this.analystNotesRich = analystNotesRich;
    }

    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments(String approverComments) {
        this.approverComments = approverComments;
    }

    public String getApproverCommentsRich() {
        return approverCommentsRich;
    }

    public void setApproverCommentsRich(String approverCommentsRich) {
        this.approverCommentsRich = approverCommentsRich;
    }

    public List<Record> getReportRecords() {
        return reportRecords;
    }

    public Report setReportRecords(List<Record> reportRecords) {
        this.reportRecords = reportRecords;
        return this;
    }

    public List<String> getReportTeamIds() {
        return reportTeamIds;
    }

    public void setReportTeamIds(List<String> reportTeamIds) {
        this.reportTeamIds = reportTeamIds;
    }

    public Boolean getStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(Boolean statusChanged) {
        this.statusChanged = statusChanged;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Report setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public Report setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public Report setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public List<String> getRecordIds() {
        return recordIds;
    }

    public Report setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
        return this;
    }

    public List<ReportCategory> getCategories() {
        return categories;
    }

    public Report setCategories(List<ReportCategory> categories) {
        this.categories = categories;
        return this;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public Report setStatus(ReportStatus status) {
        this.status = status;
        return this;
    }

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

    public Report setSourceType(String sourceType) {
        this.sourceType = sourceType;
        return this;
    }

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

    public Report setReportID(String reportID) {
        this.reportID = reportID;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Report setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public ReportOwner getOwner() {
        return owner;
    }

    public Report setOwner(ReportOwner owner) {
        this.owner = owner;
        return this;
    }

    public Report generate() {
        this
                .setSubject("subject:" + RandomStringUtils.randomAlphabetic(4))
                .setStatus(ReportStatus.DRAFT)
                .initOwner();
        this.setCreatedAt(new Date());
        return this;
    }

    public Report initOwner() {
        UserService userService = new UserService();
        User user = userService.me();

        User reportUser = new User();
        reportUser.setId(user.getId());
        reportUser.setName(user.getName());
        reportUser.setStaffId(user.getStaffId());

        ReportOwner owner = new ReportOwner();
        owner
                .setRole(userService.getReportRole(user))
                .setUser(reportUser);
        this
                .setAuthorId(user.getId())
                .setAuthorName(user.getName())
                .setOwner(owner);
        return this;
    }
}
