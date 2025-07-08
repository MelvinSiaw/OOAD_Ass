package model;

public class GroupDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount, int groupSize) {
        if (groupSize >= 5) {
            return amount * 0.8; // 20% off
        }
        return amount; // no discount
    }
}
