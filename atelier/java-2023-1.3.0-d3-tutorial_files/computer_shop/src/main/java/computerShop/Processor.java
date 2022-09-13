package computerShop;

import java.util.Objects;

public class Processor {
    private final String reference;
    private final int numberOfCores;
    private final float frequency;

    public Processor(String reference, int numberOfCores, float frequency) throws IllegalArgumentException {
        if (!reference.matches("[a-zA-Z0-9-]*")){
            throw new IllegalArgumentException("Bad reference!");
        }
        if (frequency < 0){
            throw new IllegalArgumentException("Bad frequency!");
        }
        if (numberOfCores < 0){
            throw new IllegalArgumentException("Bad number of cores!");
        }
        this.reference = reference;
        this.frequency = frequency;
        this.numberOfCores = numberOfCores;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Processor pp){
            return this.getReference().equals(pp.getReference()) &&
                   this.getFrequency() == pp.getFrequency() &&
                   this.getNumberOfCores() == pp.getNumberOfCores();
        }
        return false;
    }

    public float getFrequency() {
        return frequency;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public String getReference() {
        return reference;
    }
}
