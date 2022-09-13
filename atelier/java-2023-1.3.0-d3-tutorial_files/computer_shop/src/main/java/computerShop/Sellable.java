package computerShop;

public interface Sellable {
    Price getPrice();

    void applyDiscount(int discount);

    void print();
}
