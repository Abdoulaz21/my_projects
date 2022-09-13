package computerShop;

public class Main {
    public static void main(String[] args) {
        Shop s = new Shop();

        Processor processor1 = new Processor("i3-L13G4", 5, 2.80f);
        SerialNumber serialNumber1 = new SerialNumber("SAM123");
        Price price1 = new Price(529, Currency.EURO);
        Dimension dimension1 = new Dimension(312, 468, 12);

        Laptop product1 = new Laptop("Samsung", processor1, serialNumber1,
                price1, dimension1);

        Processor processor2 = new Processor("i7-1180G7", 4, 2.20f);
        SerialNumber serialNumber2 = new SerialNumber("AS456");
        Price price2 = new Price(698.9f, Currency.US_DOLLAR);
        Dimension dimension2 = new Dimension(468, 512, 9);

        Laptop product2 = new Laptop("Asus", processor2, serialNumber2,
                price2, dimension2);

        Processor processor3 = new Processor("i5-1145G7", 4, 2.60f);
        SerialNumber serialNumber3 = new SerialNumber("SUS798");
        Price price3 = new Price(998, Currency.EURO);
        Monitor monitor3 = new Monitor(15, 1920, 1080);

        Desktop product3 = new Desktop("Lenovo", processor3, serialNumber3,
                price3, monitor3);

        s.addProduct(product1);
        s.addProduct(product2);
        s.addProduct(product3);

        s.print();
        s.sort();
        s.print();

        Desktop product4 = new Desktop("Lenovo", processor3, serialNumber3,
                price3, null);

        if (!s.removeProduct(product4)) {
            System.out.println("Remove failed.");
        }

        s.removeProduct(product2);
        s.print();
    }
}
