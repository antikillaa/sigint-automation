package model;

import abs.SearchFilter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.DateHelper;
import utils.Parser;

import java.util.Date;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetFilter extends SearchFilter<Target> {

    private boolean deleted;
    private String name;
    private String description;
    private TargetType type;
    private Set<String> languages;
    private Set<String> phones;
    private Set<String> keywords;
    private String originalName;
    private Integer minThreatScore;
    private Integer maxThreatScore;
    private Long minLmt;
    private Long maxLmt;
    private Date updatedAfter = DateHelper.yesterday();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TargetType getType() {
        return type;
    }

    public void setType(TargetType type) {
        this.type = type;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Integer getMinThreatScore() {
        return minThreatScore;
    }

    public void setMinThreatScore(Integer minThreatScore) {
        this.minThreatScore = minThreatScore;
    }

    public Integer getMaxThreatScore() {
        return maxThreatScore;
    }

    public void setMaxThreatScore(Integer maxThreatScore) {
        this.maxThreatScore = maxThreatScore;
    }

    public Long getMinLmt() {
        return minLmt;
    }

    public void setMinLmt(Long minLmt) {
        this.minLmt = minLmt;
    }

    public Long getMaxLmt() {
        return maxLmt;
    }

    public void setMaxLmt(Long maxLmt) {
        this.maxLmt = maxLmt;
    }

    public Date getUpdatedAfter() {
        return updatedAfter;
    }

    public void setUpdatedAfter(Date updatedAfter) {
        this.updatedAfter = updatedAfter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    @Override
    public boolean isAppliedToEntity(Target entity) {
        return activeFilter.isAppliedToEntity(entity);
    }

    private class NameFilter extends SearchFilter<Target> {

        NameFilter(String value) {
            name = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.getName().equals(name);
        }
    }

    private class TypeFilter extends SearchFilter<Target> {

        TypeFilter(String value) {
            type = TargetType.valueOf(value);
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.getType().equals(type);
        }
    }

    private class DescriptionFilter extends SearchFilter<Target> {

        DescriptionFilter(String value) {
            description = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.getDescription().equals(description);
        }
    }

    private class DeletedFilter extends SearchFilter<Target> {

        DeletedFilter(Boolean value) {
            deleted = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            return entity.isDeleted() == deleted;
        }
    }

    private class KeywordsFilter extends SearchFilter<Target> {

        KeywordsFilter(Set<String> value) {
            keywords = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            Set<String> valueSet = entity.getKeywords();
            for (String keyword : keywords) {
                for (String value : valueSet) {
                    if (value.toLowerCase().contains(keyword.toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class LanguagesFilter extends SearchFilter<Target> {

        LanguagesFilter(Set<String> value) {
            languages = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            Set<String> valueSet = entity.getLanguages();
            valueSet.retainAll(languages);
            return !valueSet.isEmpty();
        }
    }

    private class PhonesFilter extends SearchFilter<Target> {

        PhonesFilter(Set<String> value) {
            phones = value;
        }

        @Override
        public boolean isAppliedToEntity(Target entity) {
            Set<String> valueSet = entity.getPhones();
            valueSet.retainAll(phones);
            return !valueSet.isEmpty();
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
        if (criteria.toLowerCase().equals("name")) {
            this.setActiveFilter(this.new NameFilter(value));
        } else if (criteria.toLowerCase().equals("type")) {
            this.setActiveFilter(this.new TypeFilter(value));
        } else if (criteria.toLowerCase().equals("description")) {
            this.setActiveFilter(this.new DescriptionFilter(value));
        } else if (criteria.toLowerCase().equals("deleted")) {
            this.setActiveFilter(this.new DeletedFilter(Boolean.valueOf(value)));
        } else if (criteria.toLowerCase().equals("keywords")) {
            this.setActiveFilter(this.new KeywordsFilter(Parser.stringToSet(value)));
        } else if (criteria.toLowerCase().equals("languages")) {
            this.setActiveFilter(this.new LanguagesFilter(Parser.stringToSet(value)));
        } else if (criteria.toLowerCase().equals("phones")) {
            this.setActiveFilter(this.new PhonesFilter(Parser.stringToSet(value)));
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
