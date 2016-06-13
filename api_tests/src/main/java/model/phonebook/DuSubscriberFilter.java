package model.phonebook;

import abs.SearchFilter;
import model.DuSubscriberEntry;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class DuSubscriberFilter extends SearchFilter<DuSubscriberEntry> {

    private String phoneNumber;
    private String name;
    private String address;
    // To search all fields using the query string syntax, phone number is the default field:
    // https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html
    private String queryString; //TODO

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public boolean filter(DuSubscriberEntry entity) {
        return activeFilter.filter(entity);
    }


    public class AddressFilter extends SearchFilter<DuSubscriberEntry> {

        public AddressFilter(String value) {
            address = value;
        }

        @Override
        public boolean filter(DuSubscriberEntry entity) {
            return entity.getAddress().equals(address);
        }
    }

    public class PhoneNumberFilter extends SearchFilter<DuSubscriberEntry> {

        public PhoneNumberFilter(String value) {
            phoneNumber = value;
        }

        @Override
        public boolean filter(DuSubscriberEntry entity) {
            return entity.getPhoneNumber().equals(phoneNumber);
        }
    }

    public class NameFilter extends SearchFilter<DuSubscriberEntry> {

        public NameFilter(String value) {
            name = value;
        }

        @Override
        public boolean filter(DuSubscriberEntry entity) {
            return entity.getName().equals(name);
        }
    }

    public DuSubscriberFilter filterBy(String criteria, String value) {
        if (criteria.toLowerCase().equals("address")) {
            this.setActiveFilter(this.new AddressFilter(value));
        } else if (criteria.toLowerCase().equals("name")) {
            this.setActiveFilter(this.new NameFilter(value));
        } else if (criteria.toLowerCase().equals("phonenumber")) {
            this.setActiveFilter(this.new PhoneNumberFilter(value));
        } else {
            throw new AssertionError("Unknown filter type");
        }
        return this;
    }
}
