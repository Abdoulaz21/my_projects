public class main {
    public static void main(String[] args) {
        Pen blue_one = new Pen(Color.BLUE);
        Pen red_one = new Pen(Color.RED);
        Pen false_blue = new Pen(Color.RED);
        false_blue.print();
        System.out.println("Red pens: " + Pen.getRedPenCounter());
        System.out.println("Blue pens: " + Pen.getBluePenCounter());
        false_blue.changeColor(Color.BLUE);
        false_blue.print();
        System.out.println("Red pens: " + Pen.getRedPenCounter());
        System.out.println("Blue pens: " + Pen.getBluePenCounter());
    }
}
