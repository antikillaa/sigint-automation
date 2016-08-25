package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Meta {

    private boolean isProcessed;
    private String fileName;
    private String sourceId;
    private String userId;
    private Date process_timestamp;
    private String md5_sigint;
    private boolean finished;

    public boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(boolean processed) {
        isProcessed = processed;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public Date getProcess_timestamp() {
        return process_timestamp;
    }

    public void setProcess_timestamp(Date process_timestamp) {
        this.process_timestamp = process_timestamp;
    }

    public String getMd5_sigint() {
        return md5_sigint;
    }

    public void setMd5_sigint(String md5_sigint) {
        this.md5_sigint = md5_sigint;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
