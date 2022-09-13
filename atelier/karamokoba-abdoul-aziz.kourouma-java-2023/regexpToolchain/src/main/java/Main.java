import regexpToolchain.*;

public class Main {
    public static void main(String[] args) {
        RegExp regExp = new Kleene(
                new Union(new Token('a'),
                        new Concatenation(
                                new Token('b'),
                                new Token('c'))));
        System.out.println(regExp.print());
        Automaton thompson = regExp.thompson();
        System.out.println(new DotPrinter().print(thompson));
        System.out.println(regExp.matches(""));
        System.out.println(regExp.matches("a"));
        System.out.println(regExp.matches("abcaaaabc"));
        System.out.println(regExp.matches("bac"));
    }
}
