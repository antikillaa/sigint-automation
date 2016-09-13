package model;

import abs.TeelaEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Process extends TeelaEntity {

    private Integer version;
    private String ownerId;
    private State state;
    private String sourceId;
    private String md5;
    private List<String> indexes;
    private Date lmt;
    private MatchingContext matchingContext;
    private int recordsCount;
    private int smsCount;
    private int workload;
    private Date earlistRecordTime;
    private Date latestRecordTime;
    private int targetHitCount;
    private int targetMentionCount;
    private boolean ingestMatchingComplete;


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    public Date getLmt() {
        return lmt;
    }

    public void setLmt(Date lmt) {
        this.lmt = lmt;
    }

    public MatchingContext getMatchingContext() {
        return matchingContext;
    }

    public void setMatchingContext(MatchingContext matchingContext) {
        this.matchingContext = matchingContext;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public Date getEarlistRecordTime() {
        return earlistRecordTime;
    }

    public void setEarlistRecordTime(Date earlistRecordTime) {
        this.earlistRecordTime = earlistRecordTime;
    }

    public Date getLatestRecordTime() {
        return latestRecordTime;
    }

    public void setLatestRecordTime(Date latestRecordTime) {
        this.latestRecordTime = latestRecordTime;
    }

    public int getTargetHitCount() {
        return targetHitCount;
    }

    public void setTargetHitCount(int targetHitCount) {
        this.targetHitCount = targetHitCount;
    }

    public int getTargetMentionCount() {
        return targetMentionCount;
    }

    public void setTargetMentionCount(int targetMentionCount) {
        this.targetMentionCount = targetMentionCount;
    }

    public boolean isIngestMatchingComplete() {
        return ingestMatchingComplete;
    }

    public void setIngestMatchingComplete(boolean ingestMatchingComplete) {
        this.ingestMatchingComplete = ingestMatchingComplete;
    }

    @Override
    public <T extends TeelaEntity> T generate() {
        return null;
    }
}
