package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RFISearchResults {

    public List<InformationRequest> getContent() {
        return content;
    }

    public void setContent(List<InformationRequest> content) {
        this.content = content;
    }

    private List<InformationRequest> content;
}
