package reporter;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private String title;
    private String status;
    private String url;
    private List<Step> steps = new ArrayList<>();
    private Logger log = Logger.getLogger(TestCase.class);

    public String getTitle() {
        return title;
    }

    public TestCase setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCase setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public TestCase setSteps(List<Step> steps) {
        this.steps = steps;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TestCase setUrl(String url) {
        this.url = url;
        return this;
    }

    private List<Step> getStepsByStatus(String status) {
        List<Step> stepList = new ArrayList<>();
        log.debug("Getting '" + status + "' steps for test: " + title);
        for (Step step : steps) {
            if (step.getStatus().equals(status)) {
                if (!stepList.add(step)) {
                    log.warn("Step: " + step.getName() + ", does not added into '" + status + "' steps list");
                }
            }
        }
        return stepList;
    }

    public List<Step> getFailedSteps() {
        return getStepsByStatus("failed");
    }

    public List<Step> getPassedSteps() {
        return getStepsByStatus("passed");
    }

    public List<Step> getBrokenSteps() {
        return getStepsByStatus("broken");
    }

    public List<Step> getSkippedSteps() {
        return getStepsByStatus("skipped");
    }

}
