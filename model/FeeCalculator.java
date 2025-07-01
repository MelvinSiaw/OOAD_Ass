package model;

public class FeeCalculator {
    public static Bill calculate(Registration reg, DiscountStrategy discountStrategy) {
    double base = reg.getEvent().getFee();
    double extra = 0;
    if (reg.hasCatering()) extra += 20;
    if (reg.hasTransportation()) extra += 10;

    double totalBeforeDiscount = (base + extra) * reg.getGroupSize();
    double discountedTotal = discountStrategy.applyDiscount(totalBeforeDiscount, reg.getGroupSize());
    double discountAmount = totalBeforeDiscount - discountedTotal;

    return new Bill(
        base * reg.getGroupSize(),
        extra * reg.getGroupSize(),
        discountAmount,
        discountedTotal
    );
}
}
