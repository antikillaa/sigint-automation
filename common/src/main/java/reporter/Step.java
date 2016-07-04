package reporter;

public class Step {

    private String name;
    private String status; //Passed, Skipped, Failed etc

    public String getStatus() {
        return status;
    }

    public Step setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getName() {
        return name;
    }

    public Step setName(String name) {
        this.name = name;
        return this;
    }
}
