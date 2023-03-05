package dto;

public class DeliveryAddress {
    private String fullName;
    private String deliveryCountry;
    private String addressLine1;
    private String addressLine2;
    private String townCity;
    private String countyState;
    private String postCode;

    public DeliveryAddress(String fullName, String deliveryCountry, String addressLine1, String addressLine2, String townCity, String countyState, String postCode) {
        this.fullName = fullName;
        this.deliveryCountry = deliveryCountry;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.townCity = townCity;
        this.countyState = countyState;
        this.postCode = postCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getTownCity() {
        return townCity;
    }

    public String getCountyState() {
        return countyState;
    }

    public String getPostCode() {
        return postCode;
    }
}
