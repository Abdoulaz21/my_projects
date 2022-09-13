package strategy;

import java.util.List;

public class PowerFilter implements FilterStrategy {

    @Override
    public List<Integer> filter(List<Integer> input) {
        return input.stream().filter(val -> (val != 0) && ((val & (val - 1)) == 0)).toList();
    }
}
