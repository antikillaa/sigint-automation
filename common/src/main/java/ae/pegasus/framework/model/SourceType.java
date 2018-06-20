package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class SourceType extends AbstractEntity {

    private String eventFeed;
    private String dataSource;
    private String subSource;
    private String subSourceId;

    public SourceType() {
        // dummy constructor for jackson serialization
    }

    public SourceType(String eventFeed, String dataSource, String subSource) {
        this.eventFeed = eventFeed;
        this.dataSource = dataSource;
        this.subSource = subSource;
    }

    @Override
    public String toString() {
        return "SourceType{" + eventFeed +
                ", " + dataSource +
                ", " + subSource +
                ", " + subSourceId +
                '}';
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;

        if (!(that instanceof SourceType)) return false;

        SourceType thatSourceType = (SourceType) that;
        return
                this.eventFeed.equals(thatSourceType.eventFeed)
                        && this.dataSource.equals(thatSourceType.dataSource)
                        && this.subSource.equals(thatSourceType.subSource)
                        && this.subSourceId.equals(thatSourceType.subSourceId);
    }

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

    public String getSubSourceId() {
        return subSourceId;
    }

    public void setSubSourceId(String subSourceId) {
        this.subSourceId = subSourceId;
    }
}
