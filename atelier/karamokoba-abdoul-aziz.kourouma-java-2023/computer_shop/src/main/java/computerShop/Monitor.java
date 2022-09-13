package computerShop;

import java.util.Objects;

public class Monitor {
    private final int size;
    private final int widthResolution;
    private final int heightResolution;

    public Monitor(int size, int widthResolution, int heightResolution) throws IllegalArgumentException {
        if (size < 0 || widthResolution < 0 || heightResolution < 0){
            throw new IllegalArgumentException("Negative Arguments!");
        }
        this.size = size;
        this.widthResolution = widthResolution;
        this.heightResolution = heightResolution;
    }

    public Monitor withWidthResolution(int widthResolution, int heightResolution){
        return new Monitor(this.getSize(), widthResolution, heightResolution);
    }

    public Monitor withSize(int size){
        return new Monitor(size, this.getWidthResolution(), this.getHeightResolution());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Monitor mn) {
            return  this.getSize() == mn.getSize() &&
                    this.getWidthResolution() == mn.getWidthResolution() &&
                    this.heightResolution == mn.getHeightResolution();
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int getHeightResolution() {
        return heightResolution;
    }

    public int getWidthResolution() {
        return widthResolution;
    }
}
