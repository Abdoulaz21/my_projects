package computerShop;

import java.util.Objects;

public class Price implements Comparable<Price> {
    private final float price;
    private final Currency currency;

    public Price(float price, Currency currency) throws IllegalArgumentException {
        if (price < 0){
            throw new IllegalArgumentException("Bad price");
        }
        if (currency != Currency.US_DOLLAR &&
                currency != Currency.EURO &&
                currency != Currency.POUND){
            throw new IllegalArgumentException("Bad Currency!");
        }
        this.price = price;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Price pr){
            return Float.compare(this.getPrice(), pr.getPrice()) == 0 &&
                    this.getCurrency() == pr.getCurrency();
        }
        return false;
    }

    @Override
    public int compareTo(Price o) {
        return Float.compare(this.getPrice() * this.getCurrency().getValue(),
                             o.getPrice() * o.getCurrency().getValue());
    }

    public Currency getCurrency() {
        return currency;
    }

    public float getPrice() {
        return price;
    }

    public Price withDiscount(int discount){
        return new Price(this.getPrice() - (this.getPrice() * discount / 100), this.getCurrency());
    }
}
