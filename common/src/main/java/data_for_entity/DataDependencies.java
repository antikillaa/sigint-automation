package data_for_entity;

import data_for_entity.data_providers.DependencyDataProvider;


class DataDependencies {
    
    private String[] fields;
    private DependencyDataProvider provider;
    
    
    public String[] getFields() {
        return fields;
    }
    
    public void setFields(String[] fields) {
        this.fields = fields;
    }
    
    public DependencyDataProvider getProvider() {
        return provider;
    }
    
    public void setProvider(DependencyDataProvider provider) {
        this.provider = provider;
    }
}
