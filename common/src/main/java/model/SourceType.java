package model;

public class SourceType extends AbstractEntity {

    private String eventFeed;
    private String dataSource;
    private String subSource;

    public String getSubSource() {
        return subSource;
    }

    public void setSubSource(String subSource) {
        this.subSource = subSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getEventFeed() {
        return eventFeed;
    }

    public void setEventFeed(String eventFeed) {
        this.eventFeed = eventFeed;
    }
}
