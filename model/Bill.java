package model;

public class Bill {
    private final double baseFee;              
    private final double extraServices;        
    private final double totalBeforeDiscount;  
    private final double discountAmount;       
    private final double netPayable;          

    public Bill(double baseFee, double extraServices, double discountAmount) {
        this.baseFee = baseFee;
        this.extraServices = extraServices;
        this.totalBeforeDiscount = baseFee + extraServices;
        this.discountAmount = discountAmount;
        this.netPayable = totalBeforeDiscount - discountAmount;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public double getExtraServices() {
        return extraServices;
    }

    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getNetPayable() {
        return netPayable;
    }

    @Override
    public String toString() {
        return String.format("""
    Base Fee:              RM%.2f
    Extra Services:        RM%.2f
    Total Before Discount: RM%.2f
    Discount:              RM%.2f
    Net Payable:           RM%.2f
    """,
    baseFee, extraServices, totalBeforeDiscount, discountAmount, netPayable
);
    }
}
