package ae.pegasus.framework.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class FinderCase extends FinderFile {

    public FinderCase() {
        setType(ProfileJsonType.Case);
        setBaseType(ProfileJsonType.Case);
    }
}
