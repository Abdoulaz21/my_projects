package computerShop;

public class Laptop extends Computer {
    private Dimension dimension;
    public Laptop(String brand, Processor processor, SerialNumber serialNumber,
                  Price price, Dimension dimension) throws IllegalArgumentException{
        super(brand, processor, serialNumber, price);
        if (dimension == null){
            throw new IllegalArgumentException("Bad dimension!");
        }
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void print() {
        var toprint = new StringBuilder();
        toprint.append(this.getBrand());
        toprint.append(": ");
        toprint.append(this.getSerialNumber().getSerialNumber());
        toprint.append(": ");
        toprint.append(this.getPrice().getCurrency().getSign());
        toprint.append(this.getPrice().getPrice());
        toprint.append("\n--- Processor: ");
        toprint.append(this.getProcessor().getReference());
        toprint.append(" - ");
        toprint.append(this.getProcessor().getNumberOfCores());
        toprint.append(" cores - ");
        toprint.append(this.getProcessor().getFrequency());
        toprint.append("GHz\n--- Dimensions: ");
        toprint.append(this.getDimension().getHeight() + "x" +
                       this.getDimension().getWidth() + "x" +
                       this.getDimension().getThickness() + " mm");
        System.out.println(new String(toprint));
    }
}
