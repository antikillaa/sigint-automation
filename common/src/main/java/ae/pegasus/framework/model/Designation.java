package ae.pegasus.framework.model;

import ae.pegasus.framework.data_for_entity.annotations.DataIgnore;
import ae.pegasus.framework.data_for_entity.annotations.WithDataSize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Designation extends BaseEntity {

    @WithDataSize(15)
    private String name;
    @DataIgnore
    private Long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
