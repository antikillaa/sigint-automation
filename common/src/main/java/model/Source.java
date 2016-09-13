package model;


import abs.TeelaEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Source extends TeelaEntity {

    private SourceType type;
    private RecordType recordType;
    private String name;
    private String location;
    private boolean deleted;
    private double latitude;
    private double longitude;
    private Long version;

    public SourceType getType() {
        return type;
    }

    public Source setType(SourceType type) {
        this.type = type;
        return this;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public Source setRecordType(RecordType recordType) {
        this.recordType = recordType;
        return this;
    }

    public String getName() {
        return name;
    }

    public Source setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Source incrementVersion() {
        setVersion(getVersion() == null ? 1 : version + 1);
        return this;
    }

    @Override
    public Source generate() {
        setType(SourceType.getRandom());
        setRecordType(RecordType.getRandom());
        setName(getType().toLetterCode() + "-" + getRecordType().toEnglishName() + "-" + new Date().getTime());
        setLocation(RandomGenerator.getRandomCountry());
        setDeleted(false);
        setLatitude(Math.random() * 180 - 90);
        setLongitude(Math.random() * 360 - 180);
        return this;
    }
}
