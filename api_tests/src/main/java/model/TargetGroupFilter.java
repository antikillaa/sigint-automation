package model;

import abs.SearchFilter;
import utils.DateHelper;

import java.util.Date;

public class TargetGroupFilter extends SearchFilter<TargetGroup> {

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
    public boolean isAppliedToEntity(TargetGroup entity) {
        return activeFilter.isAppliedToEntity(entity);
    }

    private class DeletedFilter extends SearchFilter<TargetGroup> {

        DeletedFilter(Boolean value) {
            includeDeleted = value;
        }

        @Override
        public boolean isAppliedToEntity(TargetGroup entity) {
            return entity.isDeleted() == includeDeleted;
        }
    }

    private class UpdateAfterFilter extends SearchFilter<TargetGroup> {

        UpdateAfterFilter(Date value) {
            updatedAfter = value;
        }

        @Override
        public boolean isAppliedToEntity(TargetGroup entity) {
            return entity.getModifiedAt().after(updatedAfter);
        }
    }

    private class EmptyFilter extends SearchFilter<TargetGroup> {

        EmptyFilter() {
            updatedAfter = null;
        }

        @Override
        public boolean isAppliedToEntity(TargetGroup entity) {
            return true;
        }
    }

    /**
     * Init or update targetGroup filter.
     * Filter is used in TargetGroupService for receive list of targetGroups.
     *
     * @param criteria field filter
     * @param value value of filter
     * @return filter entity for targetGroups
     */
    public TargetGroupFilter filterBy(String criteria, String value) {
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
