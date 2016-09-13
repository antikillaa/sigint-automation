package model;

public class SelenideContext {

    public String getScenarioTitle() {
        return ScenarioTitle;
    }

    public void setScenarioTitle(String scenarioTitle) {
        ScenarioTitle = scenarioTitle;
    }

    private String ScenarioTitle;
    
    
    private static SelenideContext instance;
    
    
    public static SelenideContext get() {
        if (instance == null) {
            instance = new SelenideContext();
        }
        return instance;
    }

}
