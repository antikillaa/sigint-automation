package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.whitelist.WhiteListTypeProvider;
import data_for_entity.data_providers.whitelist.WhiteListIdentifierProvider;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Whitelist extends BaseEntity {

    @DataIgnore
    private Long version;
    private String description;
    @DataProvider(WhiteListTypeProvider.class)
    private WhiteListType type;
    @WithDataDependencies(provider = WhiteListIdentifierProvider.class, fields = {"type"})
    private String identifier;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WhiteListType getType() {
        return type;
    }

    public void setType(WhiteListType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
