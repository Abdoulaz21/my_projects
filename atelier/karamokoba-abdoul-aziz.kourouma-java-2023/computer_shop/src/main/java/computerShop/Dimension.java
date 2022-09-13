package computerShop;

import java.util.Objects;

public class Dimension {
    private final int height;
    private final int width;
    private final int thickness;

    public Dimension(int height, int width, int thickness) throws IllegalArgumentException {
        if (height < 0 || width < 0 || thickness < 0){
            throw new IllegalArgumentException("Negative argument!");
        }
        this.height = height;
        this.width = width;
        this.thickness = thickness;
    }

    public Dimension withHeight(int height){
        return new Dimension(height, this.width, this.thickness);
    }

    public Dimension withWidth(int width){
        return new Dimension(this.height, width, this.thickness);
    }

    public Dimension withThickness(int thickness){
        return new Dimension(this.height, this.width, thickness);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Dimension dim) {
            return  this.getHeight() == dim.getHeight() &&
                    this.getWidth() == dim.getWidth() &&
                    this.getThickness() == dim.getThickness();
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getThickness() {
        return thickness;
    }
}
