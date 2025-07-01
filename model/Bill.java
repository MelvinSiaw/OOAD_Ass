package model;

public class Bill {
    private final double baseFee;
    private final double extraServices;
    private final double discountAmount;
    private final double total;

    public Bill(double baseFee, double extraServices, double discountAmount, double total) {
        this.baseFee = baseFee;
        this.extraServices = extraServices;
        this.discountAmount = discountAmount;
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format(
            "Base Fee: RM%.2f\nExtra Services: RM%.2f\nDiscount: RM%.2f\nTotal Payable: RM%.2f",
            baseFee, extraServices, discountAmount, total
        );
    }
}
