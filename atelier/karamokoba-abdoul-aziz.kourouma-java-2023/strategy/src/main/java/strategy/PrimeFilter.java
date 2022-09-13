package strategy;

import java.util.List;

public class PrimeFilter implements FilterStrategy {

    boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for(int i = 2; i <= n / 2; i++) {
            if(n % i == 0)
                return false;
        }
        return true;
    }

    @Override
    public List<Integer> filter(List<Integer> input) {
        return input.stream().filter(this::isPrime).toList();
    }
}
