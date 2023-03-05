package dto;

public class CheckoutOrderSummary {
    private String subTotal;
    private String delivery;
    private String vat;
    private String total;

    public CheckoutOrderSummary(String subTotal, String delivery, String vat, String total) {
        this.subTotal = subTotal;
        this.delivery = delivery;
        this.vat = vat;
        this.total = total;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getVat() {
        return vat;
    }

    public String getTotal() {
        return total;
    }
}
