package pizzastreams;

import java.util.List;
import java.util.Optional;

import pizzastreams.Topping.*;

public class Pizza {

    private String name;
    private Dough d;
    private Topping topping;
    private Integer price;

    public Pizza(String name, Dough d, Sauce s, Cheese c, List<Vegetable> v,
                 Optional<Protein> p) {
        this.name = name;
        this.topping = new Topping(s, c, v, p);
        this.d = d;

        int doughPrice = d.getPrice();
        int saucePrice = topping.getSaucePrice();
        int cheesePrice = topping.getCheesePrice();
        int vegePrice = topping.getVegetablesPrice();
        int proteinPrice = topping.getProteinPrice();

        this.price = doughPrice + saucePrice + cheesePrice + vegePrice
                + proteinPrice;
    }

    public String getName() {
        return name;
    }

    public Dough getDough() {
        return d;
    }

    public Topping getTopping() {
        return topping;
    }

    public Integer getPrice() {
        return price;
    }
}
