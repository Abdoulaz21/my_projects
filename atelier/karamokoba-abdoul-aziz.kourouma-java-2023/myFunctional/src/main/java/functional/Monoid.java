package functional;

/**
 * Monoid is a semigroup with a neutral/identity element.
 * The neutral element should be a value (A) that when applied to another value (B), the other value (B) stay unchanged.
 *
 * @param <TYPE_T> Monoid's type
 */
public interface Monoid<TYPE_T> extends Semigroup<TYPE_T> {

    TYPE_T getNeutralElement();
}
