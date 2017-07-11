# Interval

A tiny library that helps implementing intervals for comparable types. Also contains various
helper methods to validate the interval.

## Methods
### `Interval.isValid()`

Tests whether or not this interval's start value is lower than or equal to end value.

### `Interval<T>.contains(another: Interval<T>, startInclusive : Boolean, endInclusive) : Boolean`
Tests whether `this` interval contains (read: start is less than
target's and end is greater than target's) another interval. Inclusive option notes whether 
or not the values be tested against start or end of `this` interval.

### `Interval<T>.generateIntervalFromThis(step : T) : List<T>`
Returns a list that contains values from `this` interval by step.


#### There are a lot of other methods that are just shorthands for previously mentioned ones.  
