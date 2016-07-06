package html_email.html_elements;

import html_email.HtmlElement;

import java.util.ArrayList;
import java.util.List;

public class Link extends HtmlElement {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public Link() {
        super("a");
    }

    @Override
    public List<String> expose() {
        List<String> elements = new ArrayList<>();
        String startTag= "<" + tag;
        if (style!=null) {
            startTag += ' ' + style.toString();
        }
        elements.add(startTag + String.format(" href='%s'", url)+ ">"+ "\r\n ");

        if (value!=null) {
            elements.add(value+"\r\n ");
        }
        elements.add("<"+tag+"/>"+"\r\n ");
        return elements;
    }
}
