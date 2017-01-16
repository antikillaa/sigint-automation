package controllers.reports;

import app_context.RunContext;
import blocks.context.tables.Row;
import blocks.context.tables.reports.ReportRow;
import controllers.TableController;
import controllers.reports.form_page.ReportFormFactoryController;
import errors.NotFoundException;
import model.Report;
import org.apache.log4j.Logger;
import pages.SigintPage;

public class ReportsTableController extends TableController {

    private Logger log = Logger.getLogger(ReportsTableController.class);


    public ReportsTableController(SigintPage page) {
        super(page);
    }

    @Override
    protected Report initFromRow(Row recordRow) {
        ReportRow row = (ReportRow)recordRow;
        Report report = new Report();
        report.setSubject(row.getSubject());
        report.setReportID(row.getReportID());
        return report;
    }

    public Report findReportById (String id) throws NotFoundException {
        Row reportRow = table.getRecordByColumnNameAndValue("Report ID", id);
        return initFromRow(reportRow);
    }

    public Report findReportBySubject(String subject) throws NotFoundException {
        Row reportRow = table.getRecordByColumnNameAndValue("Subject", subject);
        return initFromRow(reportRow);
    }

    public Report selectReportBySubject(String reportSubject) {
        ReportRow row;
        try {
            row = getRowBySubject(reportSubject);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssertionError();
        }
        selectRecordInTable(row);
        return initFromRow(row);
    }

    public Report selectAnyReport() {
        ReportRow row = (ReportRow)table.getEntitiesRows().get(0);
        return initFromRow(row);
    }

    private ReportRow getRowBySubject(String reportSubject) throws NotFoundException {
        ReportRow row = (ReportRow)table.getRecordByColumnNameAndValue("Subject", reportSubject);
        return row;

    }


    public void openDetailsForm(String reportSubject) {
        ReportRow row;
        try {
            row = getRowBySubject(reportSubject);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssertionError();
        }
        row.clickShowInfoButton();
        RunContext.get().put("controller", new ReportFormFactoryController());

    }

    public void openEditForm(String reportSubject) {
        ReportRow row;
        try {
            row = getRowBySubject(reportSubject);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssertionError();
        }
        row.clickEditReportButton();
        RunContext.get().put("controller", new ReportFormFactoryController());
    }


    public Report selectReportByFilter(Filter filter) {
        Row row = selectRowByFilter(filter);
        return initFromRow(row);
    }
}
