package utils;

import model.AppContext;
import model.Target;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RandomGenerator {

    private static Logger log = Logger.getLogger(RandomGenerator.class);

    public static LinkedHashSet<String> generatePhones(int maxPhones) {

         class RandomPhone {
            String num1, num2, num3; //3 numbers in area code
            String set2, set3; //sequence 2 and 3 of the phone number

            Random generator = new Random();

            public String generate() {
                num1 = Integer.toString(generator.nextInt(7) + 1);
                num2 = Integer.toString(generator.nextInt(8));
                num3 = Integer.toString(generator.nextInt(8));


                set2 = Integer.toString(generator.nextInt(643) + 100);


                set3 = Integer.toString(generator.nextInt(8999) + 1000);

                return num1+num2+num3+set2+set3;
            }
        }
        LinkedHashSet<String> phoneNumbers = new LinkedHashSet<>();
        int phones = RandomUtils.nextInt(maxPhones);
        int i = 0;
        do {
            phoneNumbers.add(new RandomPhone().generate());
            i++;
        } while (i < phones);

        return phoneNumbers;
    }

    public static LinkedHashSet<String> generateLanguagesCodes(int maxLanguages) {
        LinkedHashSet<String> languages = new LinkedHashSet<>();
        int numLanguages = RandomUtils.nextInt(maxLanguages);
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        int i = 0;
        do {
            languages.add(countryCodes.get(RandomUtils.nextInt(countryCodes.size())));
            i++;
        } while (i < numLanguages);
        return languages;
    }

    public static String getRandomCountry() {
        List<String> countries = new LinkedList<>(AppContext.getContext().getCountries().values());
        String country = countries.get(RandomUtils.nextInt(countries.size()-1));
        return country;
    }

    public static String getRandomLanguage() {
        List<String> languages = new LinkedList<>(AppContext.getContext().getLanguages().values());
        String language = languages.get(RandomUtils.nextInt(languages.size()-1));
        return language;
    }

    public static LinkedHashSet<String> generateRandomStrings(int maxNumber) {
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        int numMaxStrings = RandomUtils.nextInt(maxNumber);
        int i = 0;
        do {
            strings.add(RandomStringUtils.randomAlphanumeric(5));
            i++;
        } while (i < numMaxStrings);
        return strings;
    }

    public static <T>T getRandomItemFromList(List<T> list) {
        int index = RandomUtils.nextInt(list.size());
        return list.get(index);
    }

    public static String todayDateInterval() {

        Date currDate = new Date();
        Date startTime = getStartDate(currDate);
        Date endTime =  getEndDate(currDate);
        DateFormat format = new SimpleDateFormat("dd.MM.YYYY HH:MM:ss");
        return format.format(startTime) + " - " + format.format(endTime);
    }

    public static int getRandomDuration() {
        return (RandomUtils.nextInt(120)+1)*60;
    }

    private static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        return calendar.getTime();
    }

    private static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    public static String generateCountryCode() {
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        return countryCodes.get(RandomUtils.nextInt(countryCodes.size()));
    }

    public static String getCountryName(String countryCode) {
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }

    public static File writeTargetXLS(List<Target> targets) {

        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[] {"ID", "Name", "Type", "Phones", "Keywords", "Target Groups"});

        for (int i = 0; i < targets.size(); i++) {
            List<String> targetFields = new ArrayList<>();

            //ID	Name	Type	Phones	Keywords    Target Groups
            targetFields.add(targets.get(i).getId());
            targetFields.add(targets.get(i).getName());
            targetFields.add(targets.get(i).getType().name());
            targetFields.add(targets.get(i).getPhones().toString().replace("[", "").replace("]", ""));
            targetFields.add(targets.get(i).getKeywords().toString().replace("[", "").replace("]", ""));
            //targetFields.add(targets.get(i).getGroups().toString().replace("[", "").replace("]", ""));

            Object[] array = new Object[targetFields.size()];
            Object[] row = targetFields.toArray(array);

            Integer rowNum = i + 2;
            data.put(rowNum, row);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Target sheet");

        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset) {
            Row row = sheet.createRow(rownum++);

            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        File file = null;
        try {
            file = new File("Targets-" + new Date().getTime() + ".xls");
            log.info("Write targets to xls file: " + file.getAbsolutePath());
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            log.info("Excel written successfully..");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public static void readTargetXLS(File file) {
        log.info("Read xls file: " + file.getName());
        try {
            FileInputStream inputStream = new FileInputStream(file);

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {

                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                    }
                }
                System.out.println("");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

