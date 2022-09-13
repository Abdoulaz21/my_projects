package pizzastreams;

public enum Dough {
    NATURE(2), CAJUN(3);

    private Integer price;

    private Dough(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return this.price;
    }
}
