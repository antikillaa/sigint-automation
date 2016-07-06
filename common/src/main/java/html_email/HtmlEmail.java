package html_email;

import com.google.common.base.Joiner;
import jersey.repackaged.com.google.common.collect.Lists;
import reporter.ReportResults;
import reporter.TestCase;

public class HtmlEmail {

    public String buildHtml(ReportResults results) {
        Style titleStyle = new Style();
        titleStyle.setColor("red");
        HtmlBuilder builder = new HtmlBuilder();
        builder.addHeading("Automation build failed!", titleStyle);
        Style headingStyle = new Style();
        headingStyle.setFontSize("20px");
        headingStyle.setFontFamily("courier");
        builder.addHeading("Build statistic:", headingStyle);
        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();
        tableBuilder.addRow();
        tableBuilder.addColumn("Total tests executed:", null);
        tableBuilder.addColumn(Integer.toString(results.getTotalRun()), null);
        tableBuilder.addColumn("Total tests passed:", null);
        tableBuilder.addColumn(Integer.toString(results.getPassed().size()), null);
        tableBuilder.addColumn("Total tests failed:", null);
        tableBuilder.addColumn(Integer.toString(results.getFailed().size()), null);
        tableBuilder.addColumn("Total tests skipped:", null);
        tableBuilder.addColumn(Integer.toString(results.getSkipped().size()), null);
        builder.addTable(tableBuilder.getTable());
        builder.addHeading("Failed scenarios:", headingStyle);
        HtmlTableBuilder failedTable = new HtmlTableBuilder();
        for (TestCase failedTest:results.getFailed()) {
            failedTable.addRow();
            failedTable.addColumn(failedTest.getTitle(), new Style().setFontWeight("bold"));
            failedTable.addRow();

            String passedSteps = Joiner.on("</br>").join(Lists.transform(failedTest.getPassedSteps(), TestCase.stepNames));
            failedTable.addColumn(passedSteps, new Style().setColor("green"));
            failedTable.addRow();
            String failedSteps = Joiner.on("<br>").join(Lists.transform(failedTest.getFailedSteps(),TestCase.stepNames));
            failedTable.addColumn(failedSteps, new Style().setColor("red"));
            failedTable.addRow();
            failedTable.addLink(failedTest.getUrl(), "Open scenario in JIRA", null);
        }
        builder.addTable(failedTable.getTable());

        return builder.build();
    }
}
