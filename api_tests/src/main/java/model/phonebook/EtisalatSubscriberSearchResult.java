package model.phonebook;

import abs.SearchResult;
import model.EtisalatSubscriberEntry;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EtisalatSubscriberSearchResult extends SearchResult<EtisalatSubscriberEntry> {

}
