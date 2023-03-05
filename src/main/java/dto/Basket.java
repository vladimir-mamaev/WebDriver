package dto;

public class Basket {
    private  String deliveryCost;
    private String total;

    public Basket(String deliveryCost, String total ) {
        this.deliveryCost = deliveryCost;
        this.total = total;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public String getTotal() {
        return total;
    }

}
