import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@FunctionalInterface
public interface MyComparator<T> {
	 
	int compare(T o1, T o2);


	/**
	 * Returns a comparator that imposes the reverse ordering of this
	 * comparator.
	 *
	 * @return a comparator that imposes the reverse ordering of this
	 *         comparator.
	 * @since 1.8
	 */
	default MyComparator<T> reversed() {
		 return null;
	}

	/**
	 * Returns a lexicographic-order comparator with another comparator.
	 * If this {@code MyComparator} considers two elements equal, i.e.
	 * {@code compare(a, b) == 0}, {@code other} is used to determine the order.
	 *
	 * <p>The returned comparator is serializable if the specified comparator
	 * is also serializable.
	 *
	 * @apiNote
	 * For example, to sort a collection of {@code String} based on the length
	 * and then case-insensitive natural ordering, the comparator can be
	 * composed using following code,
	 *
	 * <pre>{@code
	 *     MyComparator<String> cmp = MyComparator.comparingInt(String::length)
	 *             .thenComparing(String.CASE_INSENSITIVE_ORDER);
	 * }</pre>
	 *
	 * @param  other the other comparator to be used when this comparator
	 *         compares two objects that are equal.
	 * @return a lexicographic-order comparator composed of this and then the
	 *         other comparator
	 * @throws NullPointerException if the argument is null.
	 * @since 1.8
	 */
	default MyComparator<T> thenComparing(MyComparator<? super T> other) {
		return null;
	}

	/**
	 * Returns a lexicographic-order comparator with a function that
	 * extracts a key to be compared with the given {@code MyComparator}.
	 *
	 * @implSpec This default implementation behaves as if {@code
	 *           thenComparing(comparing(keyExtractor, cmp))}.
	 *
	 * @param  <U>  the type of the sort key
	 * @param  keyExtractor the function used to extract the sort key
	 * @param  keyMyComparator the {@code MyComparator} used to compare the sort key
	 * @return a lexicographic-order comparator composed of this comparator
	 *         and then comparing on the key extracted by the keyExtractor function
	 * @throws NullPointerException if either argument is null.
	 * @see #comparing(Function, MyComparator)
	 * @see #thenComparing(MyComparator)
	 * @since 1.8
	 */
	default <U> MyComparator<T> thenComparing(
		Function<? super T, ? extends U> keyExtractor,
		MyComparator<? super U> keyMyComparator)
	{
		return null;
	}

	/**
	 * Returns a lexicographic-order comparator with a function that
	 * extracts a {@code Comparable} sort key.
	 *
	 * @implSpec This default implementation behaves as if {@code
	 *           thenComparing(comparing(keyExtractor))}.
	 *
	 * @param  <U>  the type of the {@link Comparable} sort key
	 * @param  keyExtractor the function used to extract the {@link
	 *         Comparable} sort key
	 * @return a lexicographic-order comparator composed of this and then the
	 *         {@link Comparable} sort key.
	 * @throws NullPointerException if the argument is null.
	 * @see #comparing(Function)
	 * @see #thenComparing(MyComparator)
	 * @since 1.8
	 */
	default <U extends Comparable<? super U>> MyComparator<T> thenComparing(
		Function<? super T, ? extends U> keyExtractor)
	{
		return null;
	}


	/**
	 * Returns a comparator that imposes the reverse of the <em>natural
	 * ordering</em>.
	 *
	 * <p>The returned comparator is serializable and throws {@link
	 * NullPointerException} when comparing {@code null}.
	 *
	 * @param  <T> the {@link Comparable} type of element to be compared
	 * @return a comparator that imposes the reverse of the <i>natural
	 *         ordering</i> on {@code Comparable} objects.
	 * @see Comparable
	 * @since 1.8
	 */
	public static <T extends Comparable<? super T>> MyComparator<T> reverseOrder() {
	    return null;
	}

	/**
	 * Returns a comparator that compares {@link Comparable} objects in natural
	 * order.
	 *
	 * <p>The returned comparator is serializable and throws {@link
	 * NullPointerException} when comparing {@code null}.
	 *
	 * @param  <T> the {@link Comparable} type of element to be compared
	 * @return a comparator that imposes the <i>natural ordering</i> on {@code
	 *         Comparable} objects.
	 * @see Comparable
	 * @since 1.8
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> MyComparator<T> naturalOrder() {
		return null;
	}

	/**
	 * Returns a null-friendly comparator that considers {@code null} to be
	 * less than non-null. When both are {@code null}, they are considered
	 * equal. If both are non-null, the specified {@code MyComparator} is used
	 * to determine the order. If the specified comparator is {@code null},
	 * then the returned comparator considers all non-null values to be equal.
	 *
	 * <p>The returned comparator is serializable if the specified comparator
	 * is serializable.
	 *
	 * @param  <T> the type of the elements to be compared
	 * @param  comparator a {@code MyComparator} for comparing non-null values
	 * @return a comparator that considers {@code null} to be less than
	 *         non-null, and compares non-null objects with the supplied
	 *         {@code MyComparator}.
	 * @since 1.8
	 */
	public static <T> MyComparator<T> nullsFirst(MyComparator<? super T> comparator) {
	   return null;
	}

	/**
	 * Returns a null-friendly comparator that considers {@code null} to be
	 * greater than non-null. When both are {@code null}, they are considered
	 * equal. If both are non-null, the specified {@code MyComparator} is used
	 * to determine the order. If the specified comparator is {@code null},
	 * then the returned comparator considers all non-null values to be equal.
	 *
	 * <p>The returned comparator is serializable if the specified comparator
	 * is serializable.
	 *
	 * @param  <T> the type of the elements to be compared
	 * @param  comparator a {@code MyComparator} for comparing non-null values
	 * @return a comparator that considers {@code null} to be greater than
	 *         non-null, and compares non-null objects with the supplied
	 *         {@code MyComparator}.
	 * @since 1.8
	 */
	static <T> MyComparator<T> nullsLast(MyComparator<? super T> comparator) {
		return null;
	}


	/**
	 * Accepts a function that extracts a sort key from a type {@code T}, and
	 * returns a {@code MyComparator<T>} that compares by that sort key using
	 * the specified {@link MyComparator}.
	 *
	 * <p>The returned comparator is serializable if the specified function
	 * and comparator are both serializable.
	 *
	 * @apiNote
	 * For example, to obtain a {@code MyComparator} that compares {@code
	 * data.Person} objects by their last name ignoring case differences,
	 *
	 * <pre>{@code
	 *     MyComparator<data.Person> cmp = MyComparator.comparing(
	 *             data.Person::getLastName,
	 *             String.CASE_INSENSITIVE_ORDER);
	 * }</pre>
	 *
	 * @param  <T> the type of element to be compared
	 * @param  <U> the type of the sort key
	 * @param  keyExtractor the function used to extract the sort key
	 * @param  keyMyComparator the {@code MyComparator} used to compare the sort key
	 * @return a comparator that compares by an extracted key using the
	 *         specified {@code MyComparator}
	 * @throws NullPointerException if either argument is null
	 * @since 1.8
	 */
	public static <T, U> MyComparator<T> comparing(
		Function<? super T, ? extends U> keyExtractor,
		MyComparator<? super U> keyMyComparator)
	{
		return null;
	}

	/**
	 * Accepts a function that extracts a {@link java.lang.Comparable
	 * Comparable} sort key from a type {@code T}, and returns a {@code
	 * MyComparator<T>} that compares by that sort key.
	 *
	 * <p>The returned comparator is serializable if the specified function
	 * is also serializable.
	 *
	 * @apiNote
	 * For example, to obtain a {@code MyComparator} that compares {@code
	 * data.Person} objects by their last name,
	 *
	 * <pre>{@code
	 *     MyComparator<data.Person> byLastName = MyComparator.comparing(data.Person::getLastName);
	 * }</pre>
	 *
	 * @param  <T> the type of element to be compared
	 * @param  <U> the type of the {@code Comparable} sort key
	 * @param  keyExtractor the function used to extract the {@link
	 *         Comparable} sort key
	 * @return a comparator that compares by an extracted key
	 * @throws NullPointerException if the argument is null
	 * @since 1.8
	 */
	public static <T, U extends Comparable<? super U>>
	MyComparator<T> comparing(
		Function<? super T, ? extends U> keyExtractor)
	{
		return (o1, o2) -> keyExtractor.apply(o1).compareTo(keyExtractor.apply(o2));
	}
}
