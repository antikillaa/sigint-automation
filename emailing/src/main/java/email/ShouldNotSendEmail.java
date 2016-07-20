package email;

import html_elements.HtmlElement;

public class ShouldNotSendEmail extends HtmlEmail {
    
    @Override
    protected HtmlElement buildStatusHeader() {
        return null;
    }
    
    @Override
    protected HtmlElement buildBody() {
        return null;
    }
}
