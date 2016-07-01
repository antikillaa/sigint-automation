package html_email;

import html_email.html_elements.*;

public class HtmlBuilder {

    private HtmlElement current;
    private HtmlElement top;

    public HtmlBuilder(String title) {
        html(title);
    }

    private void html(String assignTitle) {
        HtmlElement base = new Base();
        HtmlElement head = new Heading();
        HtmlElement title = new Title();
        title.setValue(assignTitle);
        head.addChild(title);
        base.addChild(head);
        HtmlElement body = new Body();
        base.addChild(body);
        top = base;
        current = body;

    }

    public HtmlBuilder addHeading(String value, Style style) {
        HtmlElement heading = new Heading();
        heading.setValue(value);
        if (!(style==null)) {
            heading.setStyle(style);
        }
        current.addChild(heading);
        return this;
    }

    public HtmlBuilder addParagraph(String value, Style style) {
        HtmlElement parag = new Paragraph();
        parag.setValue(value);
        if (!(style==null)) {
            parag.setStyle(style);
        }
        current.addChild(parag);
        return this;
    }

    public HtmlBuilder addTable(HtmlElement table){
        current.addChild(table);
        return this;
    }

}
