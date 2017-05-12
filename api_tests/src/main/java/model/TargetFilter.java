package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.DateHelper;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetFilter extends SearchFilter<Target> {

    private boolean includeDeleted;
    private Date updatedAfter = DateHelper.yesterday();

    public Date getUpdatedAfter() {
        return updatedAfter;
    }

    public void setUpdatedAfter(Date updatedAfter) {
        this.updatedAfter = updatedAfter;
    }

    public boolean getIncludeDeleted() {
        return includeDeleted;
    }

    public void setIncludeDeleted(boolean includeDeleted) {
        this.includeDeleted = includeDeleted;
    }


    @Override
    public boolean isAppliedToEntity(Target entity) {
        return activeFilter.isAppliedToEntity(entity);
    }

    private class DeletedFilter extends SearchFilter<Target> {

        DeletedFilter(Boolean value) {
            includeDeleted = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.isDeleted() == includeDeleted;
        }
    }

    private class UpdateAfterFilter extends SearchFilter<Target> {

        UpdateAfterFilter(Date value) {
            updatedAfter = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.getModifiedAt().after(updatedAfter);
        }
    }

    private class EmptyFilter extends SearchFilter<Target> {

        EmptyFilter() {
            updatedAfter = null;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return true;
        }
    }

    /**
     * Init or update target filter.
     * Filter is used in TargetService for receive list of targets.
     *
     * @param criteria field filter
     * @param value value of filter
     * @return filter entity for targets
     */
    public TargetFilter filterBy(String criteria, String value) {
        if (criteria.toLowerCase().equals("includedeleted")) {
            this.setActiveFilter(this.new DeletedFilter(Boolean.valueOf(value)));
        } else if (criteria.toLowerCase().equals("updatedafter")) {
            this.setActiveFilter(this.new UpdateAfterFilter(new Date(Long.valueOf(value))));
        } else if (criteria.toLowerCase().equals("empty")) {
            this.setActiveFilter(this.new EmptyFilter());
        } else {
            throw new AssertionError("Unknown isAppliedToEntity type");
        }
        return this;
    }

}
