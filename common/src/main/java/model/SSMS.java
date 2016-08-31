package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import utils.RandomGenerator;

import java.util.*;


public class SSMS extends TeelaEntity {

    private int cdr_id;
    private Date event_time;
    private int unit;
    private int matrix_ts;
    private int ni;
    private int opc;
    private int dpc;
    private int hit_count;
    private int maskmarked;
    private String srcip;
    private String dscip;
    private Date sms_time;
    private String smstimezone;
    private String txt;
    private int haveparts;
    private int totalparts;
    private String called_imsi;
    private String called_imsi_cc;
    private String called_imsi_ac;
    private String called_imsi_hit;
    private String caller_imsi;
    private String caller_imsi_cc;
    private String caller_imsi_ac;
    private String caller_imsi_hit;
    private String pattern;
    private String called;
    private int called_non;
    private String called_mod;
    private String called_rule;
    private String called_hit;
    private String called_cc;
    private String called_ac;
    private String caller;
    private int caller_non;
    private String caller_mod;
    private String caller_rule;
    private String caller_hit;
    private String caller_cc;
    private String caller_ac;
    private String smsc;
    private int smsc_non;
    private String smsc_mod;
    private String smsc_rule;
    private String smsc_hit;
    private String smsc_cc;
    private String smsc_ac;

    public int getCdr_id() {
        return cdr_id;
    }

    public void setCdr_id(int cdr_id) {
        this.cdr_id = cdr_id;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getMatrix_ts() {
        return matrix_ts;
    }

    public void setMatrix_ts(int matrix_ts) {
        this.matrix_ts = matrix_ts;
    }

    public int getNi() {
        return ni;
    }

    public void setNi(int ni) {
        this.ni = ni;
    }

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    public int getDpc() {
        return dpc;
    }

    public void setDpc(int dpc) {
        this.dpc = dpc;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public int getMaskmarked() {
        return maskmarked;
    }

    public void setMaskmarked(int maskmarked) {
        this.maskmarked = maskmarked;
    }

    public String getSrcip() {
        return srcip;
    }

    public void setSrcip(String srcip) {
        this.srcip = srcip;
    }

    public String getDscip() {
        return dscip;
    }

    public void setDscip(String dscip) {
        this.dscip = dscip;
    }

    public Date getSms_time() {
        return sms_time;
    }

    public void setSms_time(Date sms_time) {
        this.sms_time = sms_time;
    }

    public String getSmstimezone() {
        return smstimezone;
    }

    public void setSmstimezone(String smstimezone) {
        this.smstimezone = smstimezone;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getHaveparts() {
        return haveparts;
    }

    public void setHaveparts(int haveparts) {
        this.haveparts = haveparts;
    }

    public int getTotalparts() {
        return totalparts;
    }

    public void setTotalparts(int totalparts) {
        this.totalparts = totalparts;
    }

    public String getCalled_imsi() {
        return called_imsi;
    }

    public void setCalled_imsi(String called_imsi) {
        this.called_imsi = called_imsi;
    }

    public String getCalled_imsi_cc() {
        return called_imsi_cc;
    }

    public void setCalled_imsi_cc(String called_imsi_cc) {
        this.called_imsi_cc = called_imsi_cc;
    }

    public String getCalled_imsi_ac() {
        return called_imsi_ac;
    }

    public void setCalled_imsi_ac(String called_imsi_ac) {
        this.called_imsi_ac = called_imsi_ac;
    }

    public String getCalled_imsi_hit() {
        return called_imsi_hit;
    }

    public void setCalled_imsi_hit(String called_imsi_hit) {
        this.called_imsi_hit = called_imsi_hit;
    }

    public String getCaller_imsi() {
        return caller_imsi;
    }

    public void setCaller_imsi(String caller_imsi) {
        this.caller_imsi = caller_imsi;
    }

    public String getCaller_imsi_cc() {
        return caller_imsi_cc;
    }

    public void setCaller_imsi_cc(String caller_imsi_cc) {
        this.caller_imsi_cc = caller_imsi_cc;
    }

    public String getCaller_imsi_ac() {
        return caller_imsi_ac;
    }

    public void setCaller_imsi_ac(String caller_imsi_ac) {
        this.caller_imsi_ac = caller_imsi_ac;
    }

    public String getCaller_imsi_hit() {
        return caller_imsi_hit;
    }

    public void setCaller_imsi_hit(String caller_imsi_hit) {
        this.caller_imsi_hit = caller_imsi_hit;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public int getCalled_non() {
        return called_non;
    }

    public void setCalled_non(int called_non) {
        this.called_non = called_non;
    }

    public String getCalled_mod() {
        return called_mod;
    }

    public void setCalled_mod(String called_mod) {
        this.called_mod = called_mod;
    }

    public String getCalled_rule() {
        return called_rule;
    }

    public void setCalled_rule(String called_rule) {
        this.called_rule = called_rule;
    }

    public String getCalled_hit() {
        return called_hit;
    }

    public void setCalled_hit(String called_hit) {
        this.called_hit = called_hit;
    }

    public String getCalled_cc() {
        return called_cc;
    }

    public void setCalled_cc(String called_cc) {
        this.called_cc = called_cc;
    }

    public String getCalled_ac() {
        return called_ac;
    }

    public void setCalled_ac(String called_ac) {
        this.called_ac = called_ac;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public int getCaller_non() {
        return caller_non;
    }

    public void setCaller_non(int caller_non) {
        this.caller_non = caller_non;
    }

    public String getCaller_mod() {
        return caller_mod;
    }

    public void setCaller_mod(String caller_mod) {
        this.caller_mod = caller_mod;
    }

    public String getCaller_rule() {
        return caller_rule;
    }

    public void setCaller_rule(String caller_rule) {
        this.caller_rule = caller_rule;
    }

    public String getCaller_hit() {
        return caller_hit;
    }

    public void setCaller_hit(String caller_hit) {
        this.caller_hit = caller_hit;
    }

    public String getCaller_cc() {
        return caller_cc;
    }

    public void setCaller_cc(String caller_cc) {
        this.caller_cc = caller_cc;
    }

    public String getCaller_ac() {
        return caller_ac;
    }

    public void setCaller_ac(String caller_ac) {
        this.caller_ac = caller_ac;
    }

    public String getSmsc() {
        return smsc;
    }

    public void setSmsc(String smsc) {
        this.smsc = smsc;
    }

    public int getSmsc_non() {
        return smsc_non;
    }

    public void setSmsc_non(int smsc_non) {
        this.smsc_non = smsc_non;
    }

    public String getSmsc_mod() {
        return smsc_mod;
    }

    public void setSmsc_mod(String smsc_mod) {
        this.smsc_mod = smsc_mod;
    }

    public String getSmsc_rule() {
        return smsc_rule;
    }

    public void setSmsc_rule(String smsc_rule) {
        this.smsc_rule = smsc_rule;
    }

    public String getSmsc_hit() {
        return smsc_hit;
    }

    public void setSmsc_hit(String smsc_hit) {
        this.smsc_hit = smsc_hit;
    }

    public String getSmsc_cc() {
        return smsc_cc;
    }

    public void setSmsc_cc(String smsc_cc) {
        this.smsc_cc = smsc_cc;
    }

    public String getSmsc_ac() {
        return smsc_ac;
    }

    public void setSmsc_ac(String smsc_ac) {
        this.smsc_ac = smsc_ac;
    }

    @Override
    public <T extends TeelaEntity> T generate() {

        Calendar calendar = Calendar.getInstance();
        int seconds = new Random().nextInt(3600);
        calendar.add(Calendar.HOUR, -3); // "GMT+03:00"
        calendar.add(Calendar.SECOND, -seconds);

        setCdr_id(Integer.valueOf(RandomStringUtils.randomNumeric(8)));
        setEvent_time(calendar.getTime()); // 2015-05-15 00:00:00
        setUnit(0);
        setMatrix_ts(Integer.valueOf(RandomStringUtils.randomNumeric(4)));
        setNi(0);
        setOpc(Integer.valueOf(RandomStringUtils.randomNumeric(6)));
        setDpc(Integer.valueOf(RandomStringUtils.randomNumeric(6)));
        setHit_count(0);
        setMaskmarked(0);
        setSrcip("");
        setDscip("");
        setSms_time(calendar.getTime());  // 2015-05-15 00:00:00
        setSmstimezone("GMT+03:00");
        int random = new Random().nextInt(1000);
        setTxt(random == 1 ? RandomGenerator.generateSMSText(true) : RandomGenerator.generateSMSText(false));
        setHaveparts(Integer.valueOf(RandomStringUtils.randomNumeric(3)));
        setTotalparts(Integer.valueOf(RandomStringUtils.randomNumeric(3)));

        String calledIMSI = RandomGenerator.generateIMSI();
        setCalled_imsi(calledIMSI);
        setCalled_imsi_cc(calledIMSI.substring(0,3));
        setCalled_imsi_ac(RandomStringUtils.randomNumeric(1));
        setCalled_imsi_hit("");

        String callerIMSI = RandomGenerator.generateIMSI();
        setCaller_imsi(callerIMSI);
        setCaller_imsi_cc(callerIMSI.substring(0,3));
        setCaller_imsi_ac(RandomStringUtils.randomNumeric(1));
        setCaller_imsi_hit("");

        setPattern("");

        String toNumber = RandomGenerator.generatePhones(1).iterator().next();
        setCalled(toNumber);
        setCalled_non(1);
        setCalled_mod(toNumber);

        List<String> list = new ArrayList<>(Arrays.asList("", "imsi"));
        setCalled_rule(RandomGenerator.getRandomItemFromList(list));
        setCalled_hit("");
        setCalled_cc("");
        setCalled_ac("");

        String fromNumber = RandomGenerator.generatePhones(1).iterator().next();
        setCaller(fromNumber);
        setCaller_non(1);
        setCaller_mod(fromNumber);

        setCaller_rule("");
        setCaller_hit("");
        setCaller_cc("");
        setCaller_ac("");

        String ssmsNumber = RandomGenerator.generatePhones(1).iterator().next();
        setSmsc(ssmsNumber);
        setSmsc_non(1);
        setSmsc_mod(ssmsNumber);
        setSmsc_rule("");
        setSmsc_hit("");
        setSmsc_cc("");
        setSmsc_ac("");

        return (T) this;
    }
}
