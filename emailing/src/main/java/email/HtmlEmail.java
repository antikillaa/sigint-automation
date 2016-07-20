package email;

import html_elements.ElementsFabric;
import html_elements.HtmlBuilder;
import html_elements.HtmlElement;
import html_elements.Style;
import model.AppContext;
import reporter.ReportResults;

import java.util.Properties;

public abstract class HtmlEmail {
    
    private static Properties connection = AppContext.getContext().getJiraConnection();
    
    protected abstract HtmlElement buildStatusHeader();
    protected abstract HtmlElement buildBody();
    
    protected HtmlElement buildStatistic(HtmlElement tableStatistic, ReportResults results) {
        HtmlElement tableRowTotal = ElementsFabric.tableRow();
        tableRowTotal.addChild(ElementsFabric.tableColumn("Total tests executed:", null));
        tableRowTotal.addChild(ElementsFabric.tableColumn(Integer.toString(results.getTotalRun()), null));
        tableStatistic.addChild(tableRowTotal);
        HtmlElement tableRowPassed = ElementsFabric.tableRow();
        tableRowPassed.addChild(ElementsFabric.tableColumn("Total tests passed:", null));
        tableRowPassed.addChild(ElementsFabric.tableColumn(Integer.toString(results.getPassed().size()), null));
        tableStatistic.addChild(tableRowPassed);
        HtmlElement tableRowFailed = ElementsFabric.tableRow();
        tableRowFailed.addChild(ElementsFabric.tableColumn("Total tests failed:", null));
        tableRowFailed.addChild(ElementsFabric.tableColumn(Integer.toString(results.getFailed().size()), null));
        tableStatistic.addChild(tableRowFailed);
        HtmlElement tableRowReportLink = ElementsFabric.tableRow();
        tableRowReportLink.addChild(ElementsFabric.tableColumn("View detailed report:", null));
        tableRowReportLink.addChild(ElementsFabric.tableColumn("", null).addChild(
                ElementsFabric.link(connection.getProperty("jenkins")+"/job/Tests/allure", "report link")));
        tableStatistic.addChild(tableRowReportLink);
        return null;
    }
    
    
    public String buildHtml(ReportResults results) {
    
        HtmlBuilder builder = new HtmlBuilder();
        HtmlElement tableStatistic = ElementsFabric.table(new Style().setBorder("2"));
        tableStatistic.addChild(buildStatusHeader());
        
    }
}
