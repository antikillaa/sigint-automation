package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class DuSubscriberEntry extends TeelaEntity {

    @JsonProperty("_version")
    protected String version = "1.0";
    private String sourceId;
    private String fileName;
    private Date fileUploadDate;
    private String phoneNumber;
    private String title;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String poBox;
    private String city;
    private String address;
    private String nationality;
    private String visaType;
    private String visaNumber;
    private String idType;
    private String idNumber;
    private String status;
    private String customerType;
    private String serviceType;
    private String customerCode;


    public String getVersion() {
        return version;
    }

    public DuSubscriberEntry setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getSourceId() {
        return sourceId;
    }

    public DuSubscriberEntry setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DuSubscriberEntry setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DuSubscriberEntry setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DuSubscriberEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getName() {
        return name;
    }

    public DuSubscriberEntry setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public DuSubscriberEntry setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public DuSubscriberEntry setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DuSubscriberEntry setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPoBox() {
        return poBox;
    }

    public DuSubscriberEntry setPoBox(String poBox) {
        this.poBox = poBox;
        return this;
    }

    public String getCity() {
        return city;
    }

    public DuSubscriberEntry setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DuSubscriberEntry setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public DuSubscriberEntry setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getVisaType() {
        return visaType;
    }

    public DuSubscriberEntry setVisaType(String visaType) {
        this.visaType = visaType;
        return this;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public DuSubscriberEntry setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
        return this;
    }

    public String getIdType() {
        return idType;
    }

    public DuSubscriberEntry setIdType(String idType) {
        this.idType = idType;
        return this;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public DuSubscriberEntry setIdNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DuSubscriberEntry setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCustomerType() {
        return customerType;
    }

    public DuSubscriberEntry setCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public DuSubscriberEntry setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public Date getFileUploadDate() {
        return fileUploadDate;
    }

    public DuSubscriberEntry setFileUploadDate(Date fileUploadDate) {
        this.fileUploadDate = fileUploadDate;
        return this;
    }

    public String getServiceType() {
        return serviceType;
    }

    public DuSubscriberEntry setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    @Override
    public DuSubscriberEntry generate() {
        this
                .setSourceId("du")
                .setFileName(RandomStringUtils.randomAlphanumeric(10))
                .setFileUploadDate(new Date())
                .setName(RandomStringUtils.randomAlphabetic(8))
                .setFirstName(RandomStringUtils.randomAlphabetic(8))
                .setLastName(RandomStringUtils.randomAlphabetic(8))
                .setMiddleName(RandomStringUtils.randomAlphabetic(8))
                .setPoBox(RandomStringUtils.randomAlphabetic(10))
                .setPhoneNumber(RandomStringUtils.randomNumeric(10))
                .setTitle(RandomStringUtils.randomAlphabetic(10))
                .setCity(RandomStringUtils.randomAlphabetic(10))
                .setAddress(RandomStringUtils.randomAlphanumeric(20))
                .setNationality(RandomGenerator.getCountryName(RandomGenerator.generateCountryCode()))
                .setVisaType(RandomStringUtils.randomAlphanumeric(4))
                .setVisaNumber(RandomStringUtils.randomNumeric(8))
                .setIdType(RandomStringUtils.randomAlphanumeric(4))
                .setIdNumber(RandomStringUtils.randomNumeric(8))
                .setStatus(RandomStringUtils.randomAlphabetic(8))
                .setCustomerType(RandomStringUtils.randomAlphanumeric(4))
                .setCustomerCode(RandomStringUtils.randomNumeric(8))
                .setServiceType(RandomStringUtils.randomAlphanumeric(4));
        return this;
    }
}
