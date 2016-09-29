package conditions;

public class TrueCondition extends ExpectedCondition {

    private Boolean element;

    public String toString() {return "True condition";}

    public TrueCondition element(Boolean element) {
        this.element = element;
        return this;
    }


    protected Boolean check() {
        return element;
    }
}
