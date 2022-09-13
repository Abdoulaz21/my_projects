package regexpToolchain;

public abstract class LabelGenerator {

    public static final LabelGenerator numericLabelGenerator = new LabelGenerator() {

        int i = 0;

        @Override
        public String generate() {
            return String.format("%d", i++);
        }
    };

    public abstract String generate();
}
