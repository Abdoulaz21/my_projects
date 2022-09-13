package functional;

/**
 * <p>Semigroup is an associative binary operation across at least two instances of the same type.
 * Semigroup is not a commutative binary operation.
 *
 * <p>Examples of semigroup applications:
 *
 * <ul>
 *     <li>String concatenation
 *     <li>Integer addition
 *     <li>Boolean AND
 *     <li>Boolean OR
 * </ul>
 *
 * @param <TYPE_T> Semigroup's type.
 */
public interface Semigroup<TYPE_T> {

    TYPE_T apply(TYPE_T ele1, TYPE_T ele2);
}
