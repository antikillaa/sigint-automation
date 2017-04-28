package model;

import abs.TeelaEntity;

public class Tag extends TeelaEntity {

    private Integer version;
    private String name;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
