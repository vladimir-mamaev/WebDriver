package dto;

public class Filters {
    private final String priceRange;
    private final String availability;
    private final String language;
    private final String format;

    public Filters(String priceRange, String availability, String language, String format) {
        this.priceRange = priceRange;
        this.availability = availability;
        this.language = language;
        this.format = format;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getAvailability() {
        return availability;
    }

    public String getLanguage() {
        return language;
    }

    public String getFormat() {
        return format;
    }
}
