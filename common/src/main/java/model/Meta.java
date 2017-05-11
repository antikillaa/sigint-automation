package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Meta {

    private boolean isProcessed;
    private MetaProperties properties;
    
    public MetaProperties getProperties() {
        return properties;
    }
    
    public void setProperties(MetaProperties properties) {
        this.properties = properties;
    }
    
    public Boolean getIsProcessed() {
        return isProcessed;
    }
    
    public void setIsProcessed(Boolean processed) {
        isProcessed = processed;
    }
}
