package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Phonebook extends TeelaEntity {

    private String phoneNumber;
    private String name;
    private String address;
    private String country;
    private String countryCode;
    private String provider;
    private String location;
    private String imsi;
    private boolean manualEntry;
    
    @Override
    public String toString() {
        return String.format("Phone:%s, name:%s, address:%s, country:%s," +
                "countryCode:%s, provider:%s, location:%s, imsi:%s",
                phoneNumber, name, address, country, countryCode, provider, location, imsi);
    }

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

    public String getCountryCode() {
        return countryCode;
    }

    public Phonebook setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    @Override
    public Phonebook generate() {
        this
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setPhoneNumber(RandomStringUtils.randomNumeric(10))
                .setCountryCode(RandomGenerator.generateCountryCode())
                .setCountry(RandomGenerator.getCountryName(this.getCountryCode()))
                .setAddress(RandomStringUtils.randomAlphanumeric(20))
                .setImsi(RandomStringUtils.randomAlphanumeric(10))
                .setProvider(RandomStringUtils.randomAlphanumeric(10))
                .setLocation(RandomStringUtils.randomAlphanumeric(20));
        return this;
    }

    public List<Phonebook> generate(int count) {
        List<Phonebook> list = new ArrayList<>();
        for (int i = 0; i < count; i++ ) {
            list.add(new Phonebook().generate());
        }
        return list;
    }
}
