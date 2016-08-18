package jenkins;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
class JobInfo {
    
    private JobStatus result;
    private Long duration;
    @JsonProperty("fullDisplayName")
    private String name;
    
    @Override
    public String toString() {
        return String.format("result:%s, duration:%s, name:%s", result, duration, name);
    }
    
    public JobStatus getResult() {
        return result;
    }
    
    public void setResult(JobStatus result) {
        this.result = result;
    }
    
    public Long getDuration() {
        return duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
