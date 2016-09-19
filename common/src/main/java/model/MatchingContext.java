package model;

import abs.TeelaEntity;

public class MatchingContext extends TeelaEntity {
    
    private Long version;
    private String uploadId;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    
}
