package strategy;

import java.util.List;

public class EvenFilter implements FilterStrategy {

    @Override
    public List<Integer> filter(List<Integer> input) {
        return input.stream().filter(val -> val % 2 == 0).toList();
    }
}
