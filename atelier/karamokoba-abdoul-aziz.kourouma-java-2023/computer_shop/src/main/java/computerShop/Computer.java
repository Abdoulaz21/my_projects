package computerShop;

public abstract class Computer implements Sellable{
    private String brand;
    Processor processor;
    SerialNumber serialNumber;
    Price price;

    public Computer(String brand,
                    Processor processor,
                    SerialNumber serialNumber,
                    Price price){
        this.brand = brand;
        this.processor = processor;
        this.serialNumber = serialNumber;
        this.price = price;
    }

    public Price getPrice(){
        return this.price;
    }

    public String getBrand() {
        return brand;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void applyDiscount(int discount){
        this.price = this.price.withDiscount(discount);
    }

    public abstract void print();
}
