package com.ikueb.collatz;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * Utilities class for deriving number of steps using the Collatz conjecture.
 */
public final class CollatzUtils {

    /**
     * Private constructor for utility class.
     */
    private CollatzUtils() {
        // intentionally blank
    }

    /**
     * Applies the Collatz conjecture on <code>value</code>, and count the steps to reach 1.
     * <p>
     * Examples:
     * <ul>
     * <li>1: 1 &#8594; 4 &#8594; 2 &#8594; 1. Therefore, the return value is 3.</li>
     * <li>2: 2 &#8594; 1. Therefore, the return value is 1.</li>
     * </ul>
     *
     * @param value the value to start from.
     * @return the number of steps to end at 1.
     * @throws IllegalArgumentException if <code>value</code> is 0 or less.
     */
    public static long stepsFor(long value) {
        if (value < 1) {
            throw new IllegalArgumentException("value must be greater than 0.");
        }
        final AtomicLong c = new AtomicLong();
        LongStream.iterate(value, (v) -> c.incrementAndGet() > 0 && v % 2 == 0 ? v / 2 : 1 + 3 * v)
                .anyMatch(v -> c.longValue() > 1 && v == 1);
        return c.decrementAndGet();
    }
}