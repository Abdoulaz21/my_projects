package computerShop;

public enum Currency {
    US_DOLLAR(1f, "$"),
    EURO(1.2f, "€"),
    POUND(1.4f, "£");

    private final float value;
    private final String sign;

    Currency(float f, String s){
        this.value = f;
        this.sign = s;
    }

    public String getSign() { return sign; }

    public float getValue() {
        return value;
    }
}
