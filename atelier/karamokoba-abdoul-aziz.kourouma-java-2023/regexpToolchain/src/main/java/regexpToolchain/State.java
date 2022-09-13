package regexpToolchain;

public final class State implements Comparable {

    public static LabelGenerator labelGenerator = LabelGenerator.numericLabelGenerator;
    private String label;
    private boolean isInitial, isFinal;

    public State() {
        this(labelGenerator.generate());
    }

    public State(String label) {
        this(label, false, false);
    }

    public State(String label, boolean isInitial, boolean isFinal) {
        this.label = label;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof State))
            return 1;
        State s = (State) o;
        return label.compareTo(s.label) * (isFinal == s.isFinal && isInitial == s.isInitial ? 0 : 1);
    }
}
