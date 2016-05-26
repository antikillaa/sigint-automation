package model.phonebook;

import abs.SearchFilter;
import model.Phonebook;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class PhonebookSearchFilter extends SearchFilter<Phonebook> {

    private int page;
    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public PhonebookSearchFilter setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPage() {
        return page;
    }

    public PhonebookSearchFilter setPage(int page) {
        this.page = page;
        return this;
    }

    public boolean filter(Phonebook entity) {
        return (entity.getId().isEmpty() && entity.getName().isEmpty() && entity.getPhoneNumber().isEmpty());
    }
}
