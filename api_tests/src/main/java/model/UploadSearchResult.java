package model;

import abs.SearchResult;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class UploadSearchResult extends SearchResult<Process> {

    //{"content":[],"totalElements":0,"totalPages":0,"last":true,"sort":[{"direction":"DESC","property":"createdAt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}],"numberOfElements":0,"first":true,"size":1000,"number":0}

}
