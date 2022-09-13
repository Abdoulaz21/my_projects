import java.util.TreeMap;

public class Roman {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public String toRoman(int decimal) {
        int l =  map.floorKey(decimal);
        if ( decimal == l ) {
            return map.get(decimal);
        }
        return map.get(l) + toRoman(decimal-l);
    }

    public static void main(String[] args) {
        Roman roman = new Roman();
        String one = roman.toRoman(614);
        String two = roman.toRoman(1990);
        String three = roman.toRoman(2014);

        System.out.println("614 = " + one);
        System.out.println("1990 = " + two);
        System.out.println("2014 = " + three);
    }
}
