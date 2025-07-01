package model;

public interface DiscountStrategy {
    double applyDiscount(double amount, int groupSize);
}
