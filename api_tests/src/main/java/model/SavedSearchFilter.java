package model;

public class SavedSearchFilter extends SearchFilter<SavedSearch> {

    @Override
    public boolean isAppliedToEntity(SavedSearch entity) {
        return false;
    }
}
