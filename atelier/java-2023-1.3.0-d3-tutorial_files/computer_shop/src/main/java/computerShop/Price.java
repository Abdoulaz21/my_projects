package computerShop;

import java.util.Currency;
import java.util.Objects;

public class Price {
    private final float price;
    private final Currency currency;

    public Price(float price, Currency currency) throws IllegalArgumentException {
        if (price < 0){
            throw new IllegalArgumentException("Bad price");
        }
        this.price = price;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Price pr){
            return this.getPrice() == pr.getPrice() &&
                    Objects.equals(this.getCurrency(), pr.getCurrency());
        }
        return false;
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
