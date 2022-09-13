package pizzastreams;

import java.util.List;
import java.util.Optional;

public class Topping {
    public enum Sauce {
        TOMATO, BUFFALO, PESTO,
    }

    public enum Cheese {
        MOZZARELLA, CHEDDAR, CREAM,
    }

    public enum Vegetable {
        OLIVE, MUSHROOM, ONION,
    }

    public enum Protein {
        CHICKEN, BACON, MERGUEZ,
    }

    private Sauce s;
    private Cheese c;
    private List<Vegetable> vege;
    private Optional<Protein> prot;

    public Topping(Sauce s, Cheese c, List<Vegetable> vege,
                   Optional<Protein> prot) {
        this.s = s;
        this.c = c;
        this.vege = vege;
        this.prot = prot;
    }

    public Sauce getSauce() {
        return s;
    }

    public Cheese getCheese() {
        return c;
    }

    public List<Vegetable> getVegetables() {
        return vege;
    }

    public Optional<Protein> getProtein() {
        return prot;
    }

    public Integer getSaucePrice() {
        return (s == Sauce.TOMATO) ? 1 : ((s == Sauce.BUFFALO) ? 2 : 3);
    }

    public Integer getCheesePrice() {
        return (c == Cheese.MOZZARELLA) ? 2 : ((c == Cheese.CHEDDAR) ? 3 : 4);
    }

    public Integer getProteinPrice() {
        return prot.isEmpty() ? 0
                : ((prot.get() == Protein.CHICKEN
                || prot.get() == Protein.CHICKEN) ? 5 : 8);
    }

    public Integer getVegetablesPrice() {
        return (vege
                .isEmpty()
                ? 0
                : vege.stream()
                .mapToInt((v) -> (v == Vegetable.OLIVE
                        || v == Vegetable.ONION) ? 1 : 2)
                .sum());
    }
}
