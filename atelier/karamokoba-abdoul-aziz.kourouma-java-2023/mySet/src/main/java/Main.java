import mySet.GenericSet;
import mySet.IntegerSet;

public class Main {
    public static void main(String[] args) {
        GenericSet my_set = new GenericSet();
        my_set.insert(2);
        my_set.insert(2);
        System.out.println("My set contains " + my_set.size() + " element(s)");
        my_set.remove(1);
        my_set.remove(2);
        System.out.println("My set contains " + my_set.size() + " element(s)");
        my_set.insert(1);
        System.out.println("My set contains " + my_set.size() + " element(s)");
        System.out.println(my_set.has(1).toString());
    }
}
