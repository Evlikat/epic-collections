package net.evlikat.epiccollections.util;

/**
 * Range.
 */
public final class Range<T extends Comparable<T>> {

    private final Boolean startInclusive;
    private final Boolean endInclusive;
    private final T start;
    private final T end;

    public static <T extends Comparable<T>> Range<T> fromInclusive(T start) {
        return new Range<>(true, null, start, null);
    }

    public static <T extends Comparable<T>> Range<T> fromExclusive(T start) {
        return new Range<>(false, null, start, null);
    }

    private Range(Boolean startInclusive, Boolean endInclusive, T start, T end) {
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
        this.start = start;
        this.end = end;
    }

    public Range<T> toInclusive(T end) {
        return new Range<>(this.startInclusive, false, this.start, end);
    }

    public Range<T> toExclusive(T end) {
        return new Range<>(this.startInclusive, true, this.start, end);
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    @Override
    public String toString() {
        String inclusiveBracket = startInclusive ? "[" : "(";
        String exclusiveBracket = endInclusive ? "]" : ")";
        return inclusiveBracket + start + ", " + end + exclusiveBracket;
    }
}
