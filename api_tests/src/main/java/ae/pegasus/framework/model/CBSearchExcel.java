package ae.pegasus.framework.model;

import ae.pegasus.framework.utils.ExcelOpr;

import java.util.ArrayList;
import java.util.HashMap;

public class CBSearchExcel {

    public  HashMap<Integer, ArrayList<String>> exceldata = new HashMap<Integer, ArrayList<String>>();
    public HashMap<String, ArrayList<String>> rowdata =  new HashMap<String, ArrayList<String>>();
    public String workingDir = "";
   // public ArrayList<String> output = new ArrayList<String>();
    public  HashMap<Integer, ArrayList<String>> outputdata = new HashMap<Integer, ArrayList<String>>();
    private ExcelOpr op;
    public ArrayList<Integer> rows =new ArrayList<Integer>();

    public CBSearchExcel(String excelpath ) {

        workingDir = System.getProperty("user.dir") + excelpath;
        System.out.printf(workingDir);
        op = new ExcelOpr(workingDir);
        exceldata = op.getExcelData();
        rowdata = op.Rowdata();
        rows = op.getallrowsbasedoncolvalue("Run Test", "Y");

    }
    public void getRequiredResult( int key ,int reposonsecode , int totalcount, String error,int row)
    {

        ArrayList copy = new ArrayList(exceldata.get(key));
        copy.add(String.valueOf(reposonsecode));
        copy.add(String.valueOf(totalcount));
        copy.add(String.valueOf(error));
        String a  = "==";
        if (reposonsecode == Integer.parseInt(copy.get(5).toString()) &&
            compareByCriteria(copy.get(7).toString(),Integer.parseInt(copy.get(6).toString()),totalcount))
            copy.add(String.valueOf("PASS"));

       else
             copy.add(String.valueOf("FAIL"));

        outputdata.put(key, copy);
       // System.out.println(" the op is " + outputdata.get(key));

    }

    public void getRequiredResult( int key ,int reposonsecode , int totalcount, String error,int row, Boolean entity)
    {

        ArrayList copy = new ArrayList(exceldata.get(key));
        copy.add(String.valueOf(reposonsecode));
        copy.add(String.valueOf(totalcount));
        copy.add(String.valueOf(error));
        copy.add(String.valueOf("FAIL"));
        outputdata.put(key, copy);
        // System.out.println(" the op is " + outputdata.get(key));

    }
    public void writeOutputExcel(  )
    {
        op.clearSheetdata("Output");
        op.setCellData("Output", outputdata);
       // op.setCellData1("Output", outputdata);

    }
    public static boolean compareByCriteria(String criteria, int actualValue, int expectedValue) {

        boolean condition;

        switch (criteria.trim()) {
            case ">":
                condition = actualValue > expectedValue;
                break;
            case "<":
                condition = actualValue < expectedValue;
                break;
            case "==":
                condition = actualValue == expectedValue;
                break;
            case "=":
                condition = actualValue == expectedValue;
                break;
            case "<=":
                condition = actualValue <= expectedValue;
                break;
            case ">=":
                condition = actualValue >= expectedValue;
                break;

            default:
                throw new AssertionError("Unknown criteria value, expected: >, < or ==");
        }
        return condition;
    }

}
