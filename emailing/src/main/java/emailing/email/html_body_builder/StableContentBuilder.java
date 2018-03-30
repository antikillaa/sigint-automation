package emailing.email.html_body_builder;

import emailing.email.EmailContentBuilder;
import emailing.html_elements.ElementsFabric;
import emailing.html_elements.HtmlBuilder;
import emailing.html_elements.HtmlElement;
import emailing.html_elements.Style;
import ae.pegasus.framework.reporter.ReportResults;

public class StableContentBuilder extends EmailContentBuilder {
    
    public StableContentBuilder(String stand) {
        super(stand);
    }
    
    @Override
    protected HtmlElement buildStatusHeader() {
        HtmlElement tableRowStatus = ElementsFabric.tableRow();
        tableRowStatus.addChild(ElementsFabric.tableColumn("", new Style().setAlign("right")).
                addChild(ElementsFabric.image(connection.getHost()+"/static/e59dfe28/images/32x32/blue.gif")));
        tableRowStatus.addChild(ElementsFabric.tableColumn("", new Style().setValign("center")).
                addChild(ElementsFabric.bold("BUILD SUCCESS", new Style().setFontSize("200%"))));
        return tableRowStatus;
    }
    
    @Override
    protected void buildBody(HtmlBuilder builder, ReportResults results) {
        
    }
    
    @Override
    protected String buildSubject() {
        return getStand() + ":Automation build came to stable";
    }
}
