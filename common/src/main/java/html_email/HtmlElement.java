package html_email;

import java.util.ArrayList;
import java.util.List;

public abstract class HtmlElement {

    private String tag;
    private HtmlElement parent;
    private List<HtmlElement> childs = new ArrayList<>();
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public List<String> expose() {
        List<String> elements = new ArrayList<>();
        String startTag= "<" + tag;
        if (!(style==null)) {
            startTag += ' ' + style.toString();
        }
        elements.add(startTag + ">"+ "\r\n ");
        for (HtmlElement element:childs) {
            elements.addAll(element.expose());
        }
        if (!(value==null)) {
            elements.add(value+"\r\n ");
        }
        elements.add("<"+tag+"/>"+"\r\n ");
        return elements;
    }


    public HtmlElement(String tag) {
        this.tag = tag;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    private Style style;

    public HtmlElement getParent() {
        return parent;
    }

    public void setParent(HtmlElement parent) {
        this.parent = parent;
    }
    public void addChild(HtmlElement child) {
        this.childs.add(child);
    }

    public void removeChild(HtmlElement child) {
        this.childs.remove(child);
    }
}
