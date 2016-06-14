package model.phonebook;

import abs.SearchResult;
import model.Phonebook;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhonebookSearchResults extends SearchResult<Phonebook>{

}