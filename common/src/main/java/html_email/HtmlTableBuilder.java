package html_email;

import html_email.html_elements.Link;
import html_email.html_elements.html_table.HtmlTable;
import html_email.html_elements.html_table.HtmlTableColumn;
import html_email.html_elements.html_table.HtmlTableRow;

public class HtmlTableBuilder{

    private HtmlElement currentRow;
    private HtmlElement top;

    public HtmlTableBuilder() {
        table();
    }

    public HtmlElement getTable() {
        return top;
    }


    private void table() {
        HtmlElement table = new HtmlTable();
        top = table;
    }

    HtmlTableBuilder addRow() {
        HtmlElement row = new HtmlTableRow();
        top.addChild(row);
        currentRow = row;
        return this;

    }

    HtmlTableBuilder addColumn(String value, Style style) {
        HtmlElement column = new HtmlTableColumn();
        column.setValue(value);
        if (!(style==null)) {
            column.setStyle(style);
        }
        currentRow.addChild(column);
        return this;
    }

    HtmlTableBuilder addLink(String link, String text, Style style) {
        Link aLink = new Link();
        aLink.setValue(text);
        aLink.setUrl(link);
        if (style!=null) {
            aLink.setStyle(style);
        }
        currentRow.addChild(aLink);
        return this;
    }



}
