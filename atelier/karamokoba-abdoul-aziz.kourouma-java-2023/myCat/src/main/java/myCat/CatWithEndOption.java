package myCat;

public class CatWithEndOption extends CatOption {

    public CatWithEndOption(MyCat initial) {
        super(initial);
    }

    @Override
    protected String catLine(String line, int number) {
        String res = this.initial.catLine(line, number);
        return res + "$";
    }
}
