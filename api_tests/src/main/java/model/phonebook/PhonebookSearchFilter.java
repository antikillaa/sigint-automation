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

    private String queryString; //TODO


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


    public class AddressFilter extends SearchFilter<Phonebook> {

        public AddressFilter(String value) {
            address = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getAddress().equals(address);
        }
    }

    public class NameFilter extends SearchFilter<Phonebook> {

        public NameFilter(String value) {
            name = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getName().equals(name);
        }
    }

    public class ImsiFilter extends SearchFilter<Phonebook> {

        public ImsiFilter(String value) {
            imsi = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getImsi().equals(imsi);
        }
    }

    public class PhoneNumberFilter extends SearchFilter<Phonebook> {

        public PhoneNumberFilter(String value) {
            phoneNumber = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getPhoneNumber().equals(phoneNumber);
        }
    }

    public class CountryCodeFilter extends SearchFilter<Phonebook> {

        public CountryCodeFilter(String value) {
            countryCode = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getCountryCode().equals(countryCode);
        }
    }

    public class CountryFilter extends SearchFilter<Phonebook> {

        public CountryFilter(String value) {
            country = value;
        }

        @Override
        public boolean filter(Phonebook entity) {
            return entity.getCountry().equals(country);
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
        } else {
            throw new AssertionError("Unknown filter type");
        }
        return this;
    }
}
