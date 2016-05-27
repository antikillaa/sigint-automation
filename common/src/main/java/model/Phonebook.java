package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Phonebook extends TeelaEntity {

    private String phoneNumber;
    private String name;
    private String address;
    private String country;
    private String provider;
    private String location;
    private String imsi;
    private boolean manualEntry;
    private String _version;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Phonebook setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public Phonebook setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Phonebook setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Phonebook setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public Phonebook setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Phonebook setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getImsi() {
        return imsi;
    }

    public Phonebook setImsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    public boolean isManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    @Override
    public Phonebook generate() {
        this
                .setName("name:" + RandomStringUtils.randomAlphabetic(10))
                .setPhoneNumber(RandomStringUtils.randomNumeric(10))
                .setCountry("country:" + RandomGenerator.generateCountry())
                .setAddress("address:" + RandomStringUtils.randomAlphanumeric(20))
                .setImsi("imsi:" + RandomStringUtils.randomAlphanumeric(10))
                .setProvider("provider:" + RandomStringUtils.randomAlphanumeric(10))
                .setLocation("location:" + RandomStringUtils.randomAlphanumeric(20));
        return this;
    }
}
