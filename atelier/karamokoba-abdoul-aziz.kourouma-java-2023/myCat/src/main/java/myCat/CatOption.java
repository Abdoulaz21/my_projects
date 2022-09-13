package myCat;

public abstract class CatOption extends MyCat {

    protected final MyCat initial;

    CatOption(MyCat initial) {
        this.initial = initial;
    }
}
