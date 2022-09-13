package monads;

import functional.Function;
import functional.Supplier;

/**
 * An Option is a container which may or not contain a non-null value. This class
 * provides additional methods that depends on the presence or the absence of a
 * contained value.
 */
public abstract class Option<ENCLOSED_TYPE> {

    /**
     * Returns an Option representing the absence of contained value.
     *
     * @return The Option representing the absence of contained value.
     */
    public static <ENCLOSED_TYPE> Option<ENCLOSED_TYPE> none() {
        return new None<>();
    }

    /**
     * Returns an Option representing the presence of contained value.
     *
     * @param value The value to contain in the Option.
     * @return The Option representing the presence of contained value.
     */
    public static <ENCLOSED_TYPE> Option<ENCLOSED_TYPE> of(final ENCLOSED_TYPE value) {
        return new Some<>(value);
    }

    /**
     * Returns a boolean representing the presence of a contained value.
     * Should returns true if a value is contained, false otherwise.
     *
     * @return A boolean value representing the presence of a contained value.
     */
    public abstract boolean isPresent();

    /**
     * Returns the contained value in the Option if a value is present.
     * Throws an IllegalStateException if no value is present.
     *
     * @return The contained value.
     */
    public abstract ENCLOSED_TYPE get();

    /**
     * Returns the contained value in the Option if a value is present.
     * Returns the value given as parameter if no value is present.
     *
     * @param other The value to returns if no value is present.
     * @return The contained value or the one given as parameter.
     */
    public abstract ENCLOSED_TYPE orElseGet(final ENCLOSED_TYPE other);

    /**
     * Returns the contained value in the Option if a value is present.
     * Returns the value supply by the Supplier given as parameter if
     * no value is present.
     *
     * @param supplier The supplier supplying the value to returns if
     *                 no value is present.
     * @return The contained value or the one supply by the Supplier given
     * as parameter.
     */
    public abstract ENCLOSED_TYPE orElseSupply(final Supplier<ENCLOSED_TYPE> supplier);

    /**
     * Returns an Option containing the application result of the Function given
     * as parameter if a value is present, returns an Option representing the
     * absence of contained value otherwise.
     *
     * @param function The function to apply if a value is present.
     * @return The Option after Function application.
     */
    public abstract <TARGET_TYPE> Option<TARGET_TYPE> map(final Function<ENCLOSED_TYPE, TARGET_TYPE> function);

    static class None<ENCLOSED_TYPE> extends Option<ENCLOSED_TYPE> {

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public ENCLOSED_TYPE get() {
            throw new IllegalStateException("Value isn't present");
        }

        @Override
        public ENCLOSED_TYPE orElseGet(ENCLOSED_TYPE other) {
            return other;
        }

        @Override
        public ENCLOSED_TYPE orElseSupply(Supplier<ENCLOSED_TYPE> supplier) {
            return supplier.supply();
        }

        @Override
        public <TARGET_TYPE> Option<TARGET_TYPE> map(Function<ENCLOSED_TYPE, TARGET_TYPE> function) {
            return new None<>();
        }
    }

    static class Some<ENCLOSED_TYPE> extends Option<ENCLOSED_TYPE> {

        private final ENCLOSED_TYPE value;

        public Some(ENCLOSED_TYPE value) {
            this.value = value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public ENCLOSED_TYPE get() {
            return this.value;
        }

        @Override
        public ENCLOSED_TYPE orElseGet(ENCLOSED_TYPE other) {
            return this.value;
        }

        @Override
        public ENCLOSED_TYPE orElseSupply(Supplier<ENCLOSED_TYPE> supplier) {
            return this.value;
        }

        @Override
        public <TARGET_TYPE> Option<TARGET_TYPE> map(Function<ENCLOSED_TYPE, TARGET_TYPE> function) {
            return new Some<>(function.apply(this.value));
        }
    }
}