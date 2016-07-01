package html_email;

public class Style {
    private String color;
    private String text_align;
    private String border;
    private String padding;
    private String font_weight;

    @Override
    public String toString() {
        String main = "style = \"";
        if (!(color==null)) {
            main+=String.format(" color: %s;", color);
        }
        if (!(text_align==null)) {
            main+=String.format(" text-align: %s;", text_align);
        }

        if (!(border==null)) {
            main+=String.format(" border: %s;", border);
        }
        if (!(padding==null)) {
            main+=String.format(" padding: %s;", padding);
        }
        if (!(font_weight==null)) {
            main+=String.format(" font-weight: %s;", font_weight);
        }
        main+="\"";
        return main;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText_align() {
        return text_align;
    }

    public void setText_align(String text_align) {
        this.text_align = text_align;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getFont_weight() {
        return font_weight;
    }

    public void setFont_weight(String font_weight) {
        this.font_weight = font_weight;
    }

}
