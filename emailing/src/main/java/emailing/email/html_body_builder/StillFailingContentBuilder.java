package emailing.email.html_body_builder;

public class StillFailingContentBuilder extends FailingContentBuilder {
    
    @Override
    protected String buildSubject() {
        return "Automation build still failing";
    }
}
