package model;

import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.annotations.WithDataSize;
import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_providers.TargetNumberProvider;
import data_for_entity.data_providers.country_info.CountryCode;
import data_for_entity.data_providers.data_target.SMSTextProvider;
import data_for_entity.data_types.FieldDataType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Target teams. F-SMS source.
 *
 * Example:
 * "ID","Calling Number Raw","Called Number Raw","SMS Text","IMSI","IMEI","Datetime","Datetime End","call_length","opc_raw","Original TMSI","Target Number","calling_global_title"
 * 66860345,"750353256509","750353256777","kEqDwaBCZY",4927540264,72991,"2017-01-12 12:18:51","2017-01-12 12:18:51","1970-01-01 00:00:00","AD",6535105053,750353256509,379225174486
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FSMS extends G4Record {

    @WithFieldDataType(FieldDataType.DATE)
    private Date eventTime;
    @WithFieldDataType(FieldDataType.DATE)
    private Date datetime;
    @WithFieldDataType(FieldDataType.DATE)
    private Date dateTimeEnd;
    @DataProvider(SMSTextProvider.class)
    private String txt;
    @WithDataSize(15)
    @WithFieldDataType(FieldDataType.NUMERIC)
    private String imsi;
    @WithDataSize(15)
    @WithFieldDataType(FieldDataType.NUMERIC)
    private String imei;
    private Date callLength = new Date(0);
    @DataProvider(CountryCode.class)
    private String opcRaw;
    @WithDataSize(12)
    @WithFieldDataType(FieldDataType.NUMERIC)
    private String originalTmsi;
    @WithDataDependencies(provider = TargetNumberProvider.class, fields = {"fromNumber"})
    private String targetNumber;
    @WithDataDependencies(provider = TargetNumberProvider.class, fields = {"fromNumber"})
    private String callingGlobalTitle;

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Date getCallLength() {
        return callLength;
    }

    public void setCallLength(Date callLength) {
        this.callLength = callLength;
    }

    public String getOpcRaw() {
        return opcRaw;
    }

    public void setOpcRaw(String opcRaw) {
        this.opcRaw = opcRaw;
    }

    public String getOriginalTmsi() {
        return originalTmsi;
    }

    public void setOriginalTmsi(String originalTmsi) {
        this.originalTmsi = originalTmsi;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public String getCallingGlobalTitle() {
        return callingGlobalTitle;
    }

    public void setCallingGlobalTitle(String callingGlobalTitle) {
        this.callingGlobalTitle = callingGlobalTitle;
    }

}
