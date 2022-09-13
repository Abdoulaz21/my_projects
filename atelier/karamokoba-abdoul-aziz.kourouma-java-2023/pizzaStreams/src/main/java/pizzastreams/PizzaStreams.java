package pizzastreams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import pizzastreams.Topping.*;

public class PizzaStreams {
    /**
     * @return The sum of the prices of all the pizzas in the stream
     */
    public static Integer getTotalPrice(Stream<Pizza> pizzaStream) {
        return pizzaStream.map(Pizza::getPrice)
                          .reduce(0, Integer::sum);
    }

    /**
     * @return The average price of the pizzas in the stream
     */
    public static Double getAveragePrice(Stream<Pizza> pizzaStream) {
        return pizzaStream.mapToDouble(Pizza::getPrice)
                          .average()
                          .getAsDouble();
    }

    /**
     * @return Names of the pizzas, sorted by price in ascending order
     */
    public static List<String> sortByPrice(Stream<Pizza> pizzaStream) {
        return pizzaStream.sorted(Comparator.comparing(Pizza::getPrice))
                          .map(Pizza::getName)
                          .toList();
    }

    /**
     * @return The Pizza object which has the lowest price
     */
    public static Pizza getCheapest(Stream<Pizza> pizzaStream) {
        return pizzaStream.min(Comparator.comparing(Pizza::getPrice)).get();
    }

    /**
     * @return Names of the pizzas with meat (Protein)
     */
    public static List<String> getCarnivorous(Stream<Pizza> pizzaStream) {
        return pizzaStream.filter(pizza -> pizza.getTopping()
                                                .getProtein()
                                                .isPresent())
                          .map(Pizza::getName)
                          .toList();
    }

    /**
     * @return Names of the pizzas with at least one Vegetable and no Proteins
     */
    public static List<String> getVeggies(Stream<Pizza> pizzaStream) {
        return pizzaStream.filter(pizza -> pizza.getTopping()
                                                .getProtein()
                                                .isEmpty()
                                  && pizza.getTopping()
                                          .getVegetables()
                                          .size() > 0)
                .map(Pizza::getName)
                .toList();
    }

    /**
     * @return true if all the pizzas with a nature dough are based with tomato
     * and mozzarella, (italian pizza criteria), false otherwise
     */
    public static boolean areAllNatureItalians(Stream<Pizza> pizzaStream) {
        return pizzaStream
                .allMatch(pizza -> pizza.getDough().getPrice() == 2
                        && pizza.getTopping().getSauce() == Sauce.TOMATO
                        && pizza.getTopping().getCheese() == Cheese.MOZZARELLA);
    }
}
