import mathParser.Parser;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser("input.csv");
        parser.parse();
    }
}
