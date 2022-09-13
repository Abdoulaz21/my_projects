package pizzastreams;

import java.util.List;
import java.util.Optional;

import pizzastreams.Topping.*;

public class Main {
    public static void main(String[] args) {
        List<Pizza> pizzas = List.of(
                new Pizza("Napoli", Dough.NATURE, Sauce.TOMATO,
                        Cheese.MOZZARELLA, List.of(Vegetable.OLIVE),
                        Optional.empty()),
                new Pizza("Chicken", Dough.NATURE, Sauce.TOMATO,
                        Cheese.MOZZARELLA,
                        List.of(Vegetable.MUSHROOM, Vegetable.ONION),
                        Optional.of(Protein.CHICKEN)));

        System.out.println(
                "Total price is " + PizzaStreams.getTotalPrice(pizzas.stream())
                        + "\nAverage price is "
                        + PizzaStreams.getAveragePrice(pizzas.stream())
                        + "\nCheapest is: "
                        + PizzaStreams.getCheapest(pizzas.stream()).getName()
                        + "\nAre all nature italian? "
                        + PizzaStreams.areAllNatureItalians(pizzas.stream()));

        List<String> sortedByPrice = PizzaStreams.sortByPrice(pizzas.stream());
        System.out.println(String.format("Sorted by price pizzas: %s",
                String.join(", ", sortedByPrice)));

        List<String> carnivorousPizzas =
                PizzaStreams.getCarnivorous(pizzas.stream());
        System.out.println(String.format("Carnivorous pizzas: %s",
                String.join(", ", carnivorousPizzas)));

        List<String> vegePizzas = PizzaStreams.getVeggies(pizzas.stream());
        System.out.println(String.format("Vegetarian pizzas: %s",
                String.join(", ", vegePizzas)));
    }
}
