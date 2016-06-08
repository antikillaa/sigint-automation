package model.phonebook;

import model.DuSubscriberEntry;
import abs.SearchResult;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DuSubscriberSearchResult extends SearchResult<DuSubscriberEntry> {

}
