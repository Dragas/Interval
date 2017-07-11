package lt.saltyjuice.dragas

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Implements [Interval] for dates.
 *
 * @param minLength int Minimum different between end and start in days
 */

class DateInterval
@JvmOverloads constructor(val minLength: Int = 0)
    : Interval<Date>
{
    init
    {
        if (minLength < 0)
            throw IllegalArgumentException("Min length should be positive")
    }

    override var start = Date()

    override var end = Date()

    override fun isValid(): Boolean
    {
        return start.time + TimeUnit.DAYS.toMillis(minLength.toLong()) <= end.time
    }

    override fun generateIntersectingInterval(step: Date, vararg other: Interval<Date>): List<Date>
    {
        return generateIntersectingInterval(step.time, *other)
    }

    fun generateIntersectingInterval(step: Long, vararg other: Interval<Date>): List<Date>
    {
        if (step <= 0)
            throw IllegalArgumentException("Step shouldn't be <= 0")
        if (!isValid())
            throw IllegalArgumentException("This operation is only possible on valid intervals")
        val list = ArrayList<Date>()

        other.forEach {
            if (!it.isValid())
                throw IllegalArgumentException("This operation is only possible on valid intervals")
            if (!intersects(it))
                return@forEach
            var dateCopy = Date(it.start.time)
            while (contains(dateCopy) && it.contains(dateCopy))
            {
                if (!list.contains(dateCopy))
                    list.add(dateCopy)
                dateCopy = Date(dateCopy.time + step)
            }
        }
        list.sortBy { it }
        return list
    }

    override fun generateIntervalFromThis(step: Date): List<Date>
    {
        return generateIntervalFromThis(step.time)
    }

    fun generateIntervalFromThis(step: Long): List<Date>
    {
        return generateIntersectingInterval(step, this)
    }
}