package model.phonebook;

import model.Phonebook;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhonebookSearchResults {

    private List<Phonebook> content;

    public List<Phonebook> getContent() {
        return content;
    }

    public void setContent(List<Phonebook> content) {
        this.content = content;
    }
}