package myCat;

public class Program {

    public static void main(String[] args) {

        boolean opt_END = false;
        boolean opt_LINE = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e"))
                opt_END = true;
            else if (args[i].equals("-n"))
                opt_LINE = true;
        }

        BaseCat baseCat = new BaseCat();

        if (opt_LINE && opt_END) {
            CatWithNumberOption cat = new CatWithNumberOption(baseCat);
            CatWithEndOption cat2 = new CatWithEndOption(cat);
            System.out.print(cat2.cat(args));
        } else if (opt_END) {
            CatWithEndOption cat = new CatWithEndOption(baseCat);
            System.out.print(cat.cat(args));
        } else if (opt_LINE) {
            CatWithNumberOption cat = new CatWithNumberOption(baseCat);
            System.out.print(cat.cat(args));
        } else {
            System.out.print(baseCat.cat(args));
        }
    }
}
