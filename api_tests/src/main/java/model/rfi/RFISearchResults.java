package model.rfi;

import abs.SearchResult;
import model.InformationRequest;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RFISearchResults extends SearchResult<InformationRequest> {

}
