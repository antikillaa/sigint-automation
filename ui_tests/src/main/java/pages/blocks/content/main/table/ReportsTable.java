package pages.blocks.content.main.table;

import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;

public class ReportsTable extends Table {

    public ArrayList<ReportRow> getReports() {
        ArrayList<ReportRow> reportRows = new ArrayList<>();
        for (SelenideElement row : getRows()) {
            reportRows.add(new ReportRow(row));
        }
        return reportRows;
    }

    public ReportRow getReportByColumnNameAndValue(String columnName, String columnValue) {
        log.debug("Find report by column name:" + columnName + " and value:" + columnValue + " in the Reports table");

        for (ReportRow reportRow : getReports()) {
            if (reportRow.getCellByColumnName(columnName).getText().contentEquals(columnValue)) {
                return reportRow;
            }
        }

        log.warn("Report with column name:" + columnName + " and value:" + columnValue + " was not found in the Reports table!");
        return null;
    }

    public ReportRow firstReport() {
        return getReports().get(0);
    }
}
