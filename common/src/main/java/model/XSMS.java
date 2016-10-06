package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Target teams. X-SMS source.
 *
 * Example:
 * EventTime	        CallerMod	    CalledMod	Txt
 * 16.03.22 23:03:52	923312824935	787	        Turkey
 * 16.03.22 21:14:57	971509155084	38970281738	istanbul
 */
public class XSMS extends TeelaEntity {

    private Date eventTime;
    private String callerMod;
    private String calledMod;
    private String txt;

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getCallerMod() {
        return callerMod;
    }

    public void setCallerMod(String callerMod) {
        this.callerMod = callerMod;
    }

    public String getCalledMod() {
        return calledMod;
    }

    public void setCalledMod(String calledMod) {
        this.calledMod = calledMod;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public XSMS generate() {
        eventTime = new Date();
        callerMod = RandomStringUtils.randomNumeric(12);
        calledMod = RandomStringUtils.randomNumeric(12);
        txt = RandomGenerator.generateSMSText(false);
        return this;
    }

    public List<XSMS> generateList(int count){
        List<XSMS> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            XSMS xsms = new XSMS().generate();
            list.add(xsms);
        }
        return list;
    }

    public List<XSMS> produceListByMatrix(GenerationMatrix generationMatrix) {
        List<XSMS> list = new ArrayList<>();

        /*
            Generate X-SMS list for each target from GenerationMatrix,
            according to the current Generation matrix row with target and 'from/to' target records,
            or any mention about this target parameters for him
         */
        for (GenerationMatrixRow row : generationMatrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());
            XSMS xsms;

            // generate X-SMS list 'from' current target phone
            int from = 0;
            while (from < row.getFromNumberCount()) {
                xsms = new XSMS().generate();
                xsms.setCallerMod(phone);
                list.add(xsms);
                from++;
            }

            // generate X-SMS list 'to' current target phone
            int to = 0;
            while (to < row.getToNumberCount()) {
                xsms = new XSMS().generate();
                xsms.setCallerMod(phone);
                list.add(xsms);
                to++;
            }

            // generate X-SMS list with 'target phone' mention in the text message
            int fromMention = 0;
            while (fromMention < row.getNumberMention()) {
                xsms = new XSMS().generate();
                xsms.setTxt(RandomGenerator.generateSMSText(phone));
                list.add(xsms);
                fromMention++;
            }

            // generate X-SMS list with 'target keyword' mention in the text message
            int keywordMention = 0;
            while (keywordMention < row.getKeywordMention()) {
                List<String> keywords = new ArrayList<>(row.getTarget().getKeywords());
                String mention = RandomGenerator.getRandomItemFromList(keywords);

                xsms = new XSMS().generate();
                xsms.setTxt(RandomGenerator.generateSMSText(mention));
                list.add(xsms);
                keywordMention++;
            }

            // generate X-SMS list with 'target name' mention in the text message
            int nameMention = 0;
            while (nameMention < row.getNameMention()) {
                xsms = new XSMS().generate();
                xsms.setTxt(RandomGenerator.generateSMSText(row.getTarget().getName()));
                list.add(xsms);
                nameMention++;
            }

            // generate randomly X-SMS list without any target mention in the text message
            int withoutHitMention = 0;
            while (withoutHitMention < row.getWithoutHitMention()) {
                xsms = new XSMS().generate();
                list.add(xsms);
                withoutHitMention++;
            }
        }

        return list;
    }

    private String getTargetPhone(Target target) {
        return RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));
    }
}
