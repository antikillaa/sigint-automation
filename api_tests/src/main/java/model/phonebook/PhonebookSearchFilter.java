package model.phonebook;

import abs.SearchFilter;
import model.Phonebook;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class PhonebookSearchFilter extends SearchFilter<Phonebook> {

    private String address;
    private String name;
    private String imsi;
    private String phoneNumber;
    private String countryCode;
    private String country;
    private String queryString;


    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhonebookSearchFilter setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public PhonebookSearchFilter setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public PhonebookSearchFilter setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getName() {
        return name;
    }

    public PhonebookSearchFilter setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PhonebookSearchFilter setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getQueryString() {
        return queryString;
    }

    public PhonebookSearchFilter setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public boolean filter(Phonebook entity) {
        return activeFilter.filter(entity);
    }


    private class AddressFilter extends SearchFilter<Phonebook> {

        AddressFilter(String value) {
            address = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getAddress().equals(address);
        }
    }

    private class NameFilter extends SearchFilter<Phonebook> {

        NameFilter(String value) {
            name = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getName().equals(name);
        }
    }

    private class ImsiFilter extends SearchFilter<Phonebook> {

        ImsiFilter(String value) {
            imsi = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getImsi().equals(imsi);
        }
    }

    private class PhoneNumberFilter extends SearchFilter<Phonebook> {

        PhoneNumberFilter(String value) {
            phoneNumber = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getPhoneNumber().equals(phoneNumber);
        }
    }

    private class CountryCodeFilter extends SearchFilter<Phonebook> {

        CountryCodeFilter(String value) {
            countryCode = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getCountryCode().equals(countryCode);
        }
    }

    private class CountryFilter extends SearchFilter<Phonebook> {

        CountryFilter(String value) {
            country = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getCountry().equals(country);
        }
    }

    private class QueryStringFilter extends SearchFilter<Phonebook> {

        QueryStringFilter(String value) {
            queryString = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getPhoneNumber().equals(queryString);
        }
    }

    public PhonebookSearchFilter filterBy(String criteria, String value) {
        if (criteria.toLowerCase().equals("address")) {
            this.setActiveFilter(this.new AddressFilter(value));
        } else if (criteria.toLowerCase().equals("name")) {
            this.setActiveFilter(this.new NameFilter(value));
        } else if (criteria.toLowerCase().equals("country")) {
            this.setActiveFilter(this.new CountryFilter(value));
        } else if (criteria.toLowerCase().equals("countrycode")) {
            this.setActiveFilter(this.new CountryCodeFilter(value));
        } else if (criteria.toLowerCase().equals("phonenumber")) {
            this.setActiveFilter(this.new PhoneNumberFilter(value));
        } else if (criteria.toLowerCase().equals("imsi")) {
            this.setActiveFilter(this.new ImsiFilter(value));
        } else if (criteria.toLowerCase().equals("querystring")) {
            this.setActiveFilter(this.new QueryStringFilter(value));
        } else {
            throw new AssertionError("Unknown filter type");
        }
        return this;
    }
}
