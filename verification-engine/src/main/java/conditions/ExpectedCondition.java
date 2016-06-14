package conditions;

abstract class ExpectedCondition {

    public abstract String toString();

    protected abstract Boolean check();
}
