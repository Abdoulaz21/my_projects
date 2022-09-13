package computerShop;

import java.util.*;

public class Shop {
    ArrayList<Sellable> products;

    public Shop(){
        this.products = new ArrayList<>();
    }

    public void print() {
        System.out.println("**********");
        for (var x : products){
            x.print();
        }
        System.out.println("**********");
    }

    public boolean addProduct(Sellable product) throws IllegalArgumentException {
        if (product == null){
            throw new IllegalArgumentException("Null product!");
        }
        this.products.add(product);
        return true;
    }

    public void sort() {
        products.sort(Comparator.comparing(Sellable::getPrice));
    }

    public void applyDiscount(int discount){
        for (var x : this.products){
            x.applyDiscount(discount);
        }
    }

    public boolean removeProduct(Sellable product) {
        return this.products.remove(product);
    }
}
