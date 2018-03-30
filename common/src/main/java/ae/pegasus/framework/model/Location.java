package ae.pegasus.framework.model;

import java.util.List;

public class Location {

    private LocationType type;
    private List<Long> coordinates; //[53.84586,24.00425],
    private Long radius;
    private LocationProperties properties;

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public List<Long> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Long> coordinates) {
        this.coordinates = coordinates;
    }

    public Long getRadius() {
        return radius;
    }

    public void setRadius(Long radius) {
        this.radius = radius;
    }

    public LocationProperties getProperties() {
        return properties;
    }

    public void setProperties(LocationProperties properties) {
        this.properties = properties;
    }

    public class LocationProperties {
        private String radius_units; // "km"

        public String getRadius_units() {
            return radius_units;
        }

        public void setRadius_units(String radius_units) {
            this.radius_units = radius_units;
        }
    }

    public enum LocationType {
        Circle
    }
}
