package model.phonebook;

import abs.SearchResult;
import model.EtisalatEntry;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EtisalatSearchResult extends SearchResult<EtisalatEntry> {

}
