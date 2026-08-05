package gh.edu.ug.dcit308.library.graph;

import java.util.Objects;

/**
 * A vertex in the library movement network.
 */
public final class Location {
    private final String locationId;
    private final String name;
    private final String area;
    private final String type;

    public Location(String locationId, String name, String area, String type) {
        this.locationId = requireText(locationId, "locationId");
        this.name = requireText(name, "name");
        this.area = requireText(area, "area");
        this.type = requireText(type, "type");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    public String getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Location location)) {
            return false;
        }
        return locationId.equals(location.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }

    @Override
    public String toString() {
        return locationId + " - " + name;
    }
}
