package model;

import abs.AbstractEntity;
import abs.SearchFilter;

public class RecordsSearchFilter extends SearchFilter {

    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean isAppliedToEntity(AbstractEntity entity) {
        return false;
    }


}
