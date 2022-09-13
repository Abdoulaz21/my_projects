package myCat;

public class CatWithNumberOption extends CatOption {

    CatWithNumberOption(MyCat initial) {
        super(initial);
    }

    @Override
    protected String catLine(String line, int number) {
        String res = this.initial.catLine(line, number);
        return number + " " + res;
    }
}
