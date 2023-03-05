package dto;

public class CardDetails {
    private String cardNumber;
    private String expiryYear;
    private String expiryMonth;
    private String Cvv;

    public CardDetails(String cardNumber, String expiryYear, String expiryMonth, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        Cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getCvv() {
        return Cvv;
    }
}
