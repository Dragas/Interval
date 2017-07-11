package lt.saltyjuice.dragas

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class IntervalUnitTest
{
    var interval = DateInterval()

    @Before
    fun refreshInterval()
    {
        interval = DateInterval()
        interval.start.time -= TimeUnit.DAYS.toMillis(1)
    }

    @Test
    @Throws(Exception::class)
    fun intervalIsValid()
    {
        Assert.assertTrue(interval.isValid())
    }

    @Test
    @Throws(Exception::class)
    fun intervalWithMinLengthIsValid()
    {
        val mInterval = DateInterval(1)
        mInterval.start.time -= TimeUnit.DAYS.toMillis(1)
        Assert.assertTrue(mInterval.isValid())
    }

    @Test
    @Throws(Exception::class)
    fun intervalWithMinLengthIsInvalid()
    {
        val mInterval = DateInterval(1)
        Assert.assertFalse(mInterval.isValid())
    }

    @Test
    @Throws(Exception::class)
    fun intervalIsInvalid()
    {
        interval.start = Date()
        interval.start.time += TimeUnit.DAYS.toMillis(3) // some absurd value that is going to be bigger than current
        Assert.assertFalse(interval.isValid())
    }

    @Test
    @Throws(Exception::class)
    fun intervalIntersects()
    {
        val anotherInterval = DateInterval()
        anotherInterval.start.time -= TimeUnit.DAYS.toMillis(1)
        Assert.assertTrue(anotherInterval.intersects(interval))
        Assert.assertTrue(interval.intersects(anotherInterval))
    }

    @Test
    @Throws(Exception::class)
    fun greaterIntervalContainsSmallerInterval()
    {
        interval.end.time += TimeUnit.DAYS.toMillis(1)
        val anotherInterval = DateInterval()
        Assert.assertTrue(interval.contains(anotherInterval))
    }

    @Test
    @Throws(Exception::class)
    fun smallerIntervalDoesNotContainGreaterInterval()
    {
        val anotherInterval = DateInterval()
        Assert.assertFalse(anotherInterval.contains(interval))
    }

    @Test
    @Throws(Exception::class)
    fun generatesValidIntervalOfIntersectingDates()
    {
        interval.end.time += TimeUnit.DAYS.toMillis(7)
        interval.start.time -= TimeUnit.DAYS.toMillis(6)
        val anotherInterval = DateInterval()
        val thirdInterval = DateInterval()
                .apply {
                    start.time -= TimeUnit.DAYS.toMillis(1)
                    end.time += TimeUnit.DAYS.toMillis(1)
                }
        val list = interval.generateIntersectingInterval(TimeUnit.DAYS.toMillis(1), anotherInterval, thirdInterval)
        Assert.assertEquals(3, list.size)
    }

    @Test
    @Throws(Exception::class)
    fun generatesValidIntervalOfDates()
    {
        interval.end.time += TimeUnit.DAYS.toMillis(1)
        val list = interval.generateIntervalFromThis(TimeUnit.DAYS.toMillis(1))
        Assert.assertEquals(3, list.size)
    }
}