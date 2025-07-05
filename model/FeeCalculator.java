package model;

public class FeeCalculator {

    public static Bill calculate(Registration reg, DiscountStrategy discountStrategy) {
        double baseFee = reg.getEvent().getFee();
        double extraFee = 0;

        if (reg.hasCatering()) {
            extraFee += 20;
        }
        if (reg.hasTransportation()) {
            extraFee += 10;
        }

        int groupSize = reg.getGroupSize();

        double totalBeforeDiscount = (baseFee + extraFee) * groupSize;
        double discountedTotal = discountStrategy.applyDiscount(totalBeforeDiscount, groupSize);
        double discountAmount = totalBeforeDiscount - discountedTotal;

        return new Bill(
        baseFee * groupSize,
        extraFee * groupSize,
        discountAmount
    );
    }
}
