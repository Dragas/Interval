package lt.saltyjuice.dragas

/**
 * Defines how interval based objects should behave.
 *
 * Since [T] is a comparable type, it often uses an underlaying comparing interface, which in turn
 * uses uses internal primitive values to compare the two objects, therefore the actual interval
 * generation has to be implemented per comparable type.
 */
interface Interval<T : Comparable<T>>
{
    var start: T

    var end: T

    /**
     * Checks if the interval is valid.
     *
     * @return true, when start value is lower than or equal to end value
     */
    fun isValid(): Boolean
    {
        return start <= end
    }

    /**
     * Checks if this interval contains both start and end of [another] interval
     *
     * Equivalent to calling containsStart(another) && containsEnd(another)
     */
    fun contains(another: Interval<T>): Boolean
    {
        return containsStart(another) && containsEnd(another)
    }

    /**
     * checks whether or not another element is in this interval, inclusive
     *
     * equivalent to calling contains(another, true)
     */
    fun contains(another: T): Boolean
    {
        return contains(another, true)
    }

    /**
     * checks whether or not another element is in this interval.
     *
     * Equivalent to calling (contains, inclusive, inclusive)
     *
     * @param inclusive notes whether or not start and end should be inclusive
     */
    fun contains(another: T, inclusive: Boolean): Boolean
    {
        return contains(another, inclusive, inclusive)
    }

    /**
     * checks whether or not another element is in this in this interval. By default, inclusive
     * checks are separated.
     *
     * @param startInclusive checks if [another] matches [start]
     * @param endInclusive checks if [another] matches end
     */
    fun contains(another: T, startInclusive: Boolean, endInclusive: Boolean): Boolean
    {
        return (start < another && another < end)
                || (startInclusive && start == another)
                || (endInclusive && end == another)
    }

    /**
     * Checks whether or not another interval's start is in this interval
     *
     * equivalent to calling containsStart(another, true)
     */
    fun containsStart(another: Interval<T>): Boolean
    {
        return isValid() && another.isValid() && containsStart(another, true)
    }

    /**
     * Checks whether or not another interval's start is in this interval
     *
     * @param inclusive notes if this intervals start and end should be taken into account
     */
    fun containsStart(another: Interval<T>, inclusive: Boolean): Boolean
    {
        return isValid() && another.isValid() && contains(another.start, inclusive)
    }

    /**
     * Equivalent to calling [containsEnd(another, true)]
     */
    fun containsEnd(another: Interval<T>): Boolean
    {
        return isValid() && another.isValid() && containsEnd(another, true)
    }

    /**
     * Returns true, if provided interval's end value is in this interval.
     *
     * @param another another interval to compare
     * @param inclusive true, checks whether or not the value is
     */
    fun containsEnd(another: Interval<T>, inclusive: Boolean): Boolean
    {
        return isValid() && another.isValid() && contains(another.end, inclusive)
    }

    /**
     * Checks if another interval intersects this interval
     *
     * equivalent to calling containsStart(another) || containsEnd(another)
     */
    fun intersects(another: Interval<T>): Boolean
    {
        return isValid() && another.isValid() && (containsStart(another) || containsEnd(another))
    }

    /**
     * Tests each provided interval if they intersect this interval and generates a new list
     * of values, each value greater than the last by 1 step that are in the intersecting interval
     */
    fun generateIntersectingInterval(step: T, vararg other: Interval<T>): List<T>

    /**
     * Equivalent to calling generateIntersectingInterval(step, this)
     */
    fun generateIntervalFromThis(step: T): List<T>
}