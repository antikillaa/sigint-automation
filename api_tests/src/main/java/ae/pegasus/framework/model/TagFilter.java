package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TagFilter extends SearchFilter<Tag> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAppliedToEntity(Tag entity) {
        return activeFilter.isAppliedToEntity(entity);
    }


    private class NameFilter extends SearchFilter<Tag> {

        NameFilter(String value) {
            name = value;
            sortField = "name";
        }

        @Override
        public boolean isAppliedToEntity(Tag entity) {
            return entity.getName().equals(name);
        }
    }


    private class EmptyFilter extends SearchFilter<Tag> {

        EmptyFilter() {
            name = null;
        }

        @Override
        public boolean isAppliedToEntity(Tag entity) {
            return true;
        }
    }

    /**
     * Init or update record-tags filter.
     * Filter is used in TagService for receive list of records-tags.
     *
     * @param criteria filter field
     * @param value value of filter
     * @return filter entity for tags
     */
    public TagFilter filterBy(String criteria, String value) {
        if (criteria.toLowerCase().equals("name")) {
            this.name = value;
            this.sortField = "name";
            this.setActiveFilter(this.new NameFilter(value));
        } else if (criteria.toLowerCase().equals("empty")) {
            this.setActiveFilter(this.new EmptyFilter());
        } else {
            throw new AssertionError("Unknown isAppliedToEntity type");
        }
        return this;
    }
}
