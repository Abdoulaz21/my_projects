package computerShop;

public class Desktop extends Computer {
    private Monitor monitor;

    public Desktop(String brand, Processor processor, SerialNumber serialNumber,
                   Price price, Monitor monitor) {
        super(brand, processor, serialNumber, price);
        this.monitor = monitor;
    }

    public Monitor getMonitor() {
        return monitor;
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
        toprint.append("GHz");
        if (this.getMonitor() != null)
        {
            toprint.append("\n--- Monitor: ");
            toprint.append(this.getMonitor().getSize() + " inches - " +
                    this.getMonitor().getWidthResolution() + "x" +
                    this.getMonitor().getHeightResolution() + " pixels");
        }
        System.out.println(new String(toprint));
    }
}
