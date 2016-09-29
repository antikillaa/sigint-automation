package model;


import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Source extends TeelaEntity {
    
    @DataProvider(SourceTypeProvider.class)
    private SourceType type;
    @DataProvider(RecordTypeProvider.class)
    private RecordType recordType;
    @WithDataDependencies(provider = SourceNameProvider.class, fields = {"type", "recordType"})
    private String name;
    @DataProvider(CountryProvider.class)
    private String location;
    @DataIgnore
    private boolean deleted = false;
    @DataProvider(CoordinateProvider.class)
    private double latitude;
    @DataProvider(CoordinateProvider.class)
    private double longitude;
    @DataIgnore
    private Long version;

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
        
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    
    /**
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
    **/
}
