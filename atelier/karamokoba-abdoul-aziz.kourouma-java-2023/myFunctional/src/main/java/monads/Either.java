package monads;

import functional.Consumer;
import functional.Function;
import functional.Supplier;

import java.util.NoSuchElementException;

/**
 * An Either is a container that represents a choice between 2 generic types.
 * This class provides additional methods that depends on the type of the
 * contained value.
 */
public abstract class Either<LEFT_T, RIGHT_T> {

    /**
     * Returns an Either representing a contained value of left type.
     *
     * @param leftValue The value to contain.
     * @return The Either representing a contained value of left type.
     */
    public static <LEFT_T, RIGHT_T> Either<LEFT_T, RIGHT_T> left(final LEFT_T leftValue) {
        return new Left<>(leftValue);
    }

    /**
     * Returns an Either representing a contained value of right type.
     *
     * @param rightValue The value to contain.
     * @return The Either representing a contained value of right type.
     */
    public static <LEFT_T, RIGHT_T> Either<LEFT_T, RIGHT_T> right(final RIGHT_T rightValue) {
        return new Right<>(rightValue);
    }

    /**
     * Returns an Either representing a contained value of right type
     * if rightSupplier does not returns a null value, otherwise it
     * represents a contained value of left type.
     *
     * @param leftSupplier  The supplier supplying the left value.
     * @param rightSupplier The supplier supplying the right value.
     * @return an Either.
     */
    public static <LEFT_T, RIGHT_T> Either<LEFT_T, RIGHT_T> either(final Supplier<LEFT_T> leftSupplier, final Supplier<RIGHT_T> rightSupplier) {
        var result = rightSupplier.supply();
        if (result != null){
            return Either.<LEFT_T, RIGHT_T>right(result);
        } else {
            return Either.<LEFT_T,RIGHT_T>left(leftSupplier.supply());
        }
    }

    /**
     * Returns true if Either represents a contained value of left type,
     * false otherwise.
     *
     * @return a boolean.
     */
    public abstract boolean isLeft();

    /**
     * Returns false if Either represents a contained value of right type,
     * false otherwise.
     *
     * @return a boolean.
     */
    public abstract boolean isRight();

    /**
     * Returns the contained value if Either represents a contained value of
     * left type.
     * Throws a NoSuchElementException otherwise.
     *
     * @return The contained value or throws a NoSuchElementException.
     */
    public abstract LEFT_T getLeft();

    /**
     * Returns the contained value if Either represents a contained value of
     * right type.
     * Throws a NoSuchElementException otherwise.
     *
     * @return The contained value or throws a NoSuchElementException.
     */
    public abstract RIGHT_T getRight();

    /**
     * Apply one of the Function given as parameters according to the contained type
     * to the value contained by the Either.
     *
     * @param leftFunction  The function to apply if Either represents a contained
     *                      value of left type.
     * @param rightFunction The function to apply if Either represents a contained
     *                      value of right type.
     * @return The result of Function application.
     */
    public abstract <TARGET_T> TARGET_T apply(final Function<LEFT_T, TARGET_T> leftFunction, final Function<RIGHT_T, TARGET_T> rightFunction);

    /**
     * Wrap in an Either the result of the application of one of the Function given
     * as parameters according to the contained type to the value contained by the Either.
     *
     * @param leftFunction  The function to apply if Either represents a contained
     *                      value of left type.
     * @param rightFunction The function to apply if Either represents a contained
     *                      value of right type.
     * @return The Either containing the result of Function application.
     */
    public abstract <L, R> Either<L, R> map(final Function<LEFT_T, L> leftFunction, final Function<RIGHT_T, R> rightFunction);

    /**
     * Wrap in an Either the result of the application of the Function given as
     * parameter on the left side of the Either.
     *
     * @param function The Function to apply.
     * @return The Either containing the result of Function application on left
     * side of the Either.
     */
    public <L> Either<L, RIGHT_T> mapLeft(final Function<LEFT_T, L> function) {
        return map(function, Function.identity());
    }

    /**
     * Wrap in an Either the result of the application of the Function given as
     * parameter on the right side of the Either.
     *
     * @param function The Function to apply.
     * @return The Either containing the result of Function application on right
     * side of the Either.
     */
    public <R> Either<LEFT_T, R> mapRight(final Function<RIGHT_T, R> function) {
        return map(Function.identity(), function);
    }

    /**
     * Apply one of the Consumer given as parameters according to the contained type
     * to the value contained by the Either.
     *
     * @param leftConsumer  The Consumer to apply if Either represents a contained
     *                      value of left value.
     * @param rightConsumer The Consumer to apply if Either represents a contained
     *                      value of right value.
     */
    public abstract void run(final Consumer<LEFT_T> leftConsumer, final Consumer<RIGHT_T> rightConsumer);

    static class Left<LEFT_T, RIGHT_T> extends Either<LEFT_T, RIGHT_T> {

        protected LEFT_T leftValue;

        public Left(LEFT_T leftValue) {
            super();
            this.leftValue = leftValue;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public LEFT_T getLeft() {
            return this.leftValue;
        }

        @Override
        public RIGHT_T getRight() {
            throw new NoSuchElementException("Tried to getRight from a Left");
        }

        @Override
        public <TARGET_T> TARGET_T apply(Function<LEFT_T, TARGET_T> leftFunction, Function<RIGHT_T, TARGET_T> rightFunction) {
            return leftFunction.apply(this.leftValue);
        }

        @Override
        public <L, R> Either<L, R> map(Function<LEFT_T, L> leftFunction, Function<RIGHT_T, R> rightFunction) {
            return Either.left(leftFunction.apply(this.leftValue));
        }

        @Override
        public void run(Consumer<LEFT_T> leftConsumer, Consumer<RIGHT_T> rightConsumer) {
            leftConsumer.consume(this.leftValue);
        }
    }

    static class Right<LEFT_T, RIGHT_T> extends Either<LEFT_T, RIGHT_T> {

        protected RIGHT_T rightValue;

        public Right(RIGHT_T rightValue) {
            super();
            this.rightValue = rightValue;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public LEFT_T getLeft() {
            throw new NoSuchElementException("Tried to getLeft from a Right");
        }

        @Override
        public RIGHT_T getRight() {
            return this.rightValue;
        }

        @Override
        public <TARGET_T> TARGET_T apply(Function<LEFT_T, TARGET_T> leftFunction, Function<RIGHT_T, TARGET_T> rightFunction) {
            return rightFunction.apply(this.rightValue);
        }

        @Override
        public <L, R> Either<L, R> map(Function<LEFT_T, L> leftFunction, Function<RIGHT_T, R> rightFunction) {
            return Either.right(rightFunction.apply(this.rightValue));
        }

        @Override
        public void run(Consumer<LEFT_T> leftConsumer, Consumer<RIGHT_T> rightConsumer) {
            rightConsumer.consume(this.rightValue);
        }
    }
}
