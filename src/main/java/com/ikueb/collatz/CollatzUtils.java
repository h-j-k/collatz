/*
 * Copyright 2015 h-j-k. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ikueb.collatz;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Utility class for deriving number of steps using the Collatz conjecture.
 * <p>
 * Given a number {@code n}:
 * <ul>
 * <li>If {@code n} is even, repeat for value {@code n / 2}.</li>
 * <li>If {@code n} is odd, repeat for value {@code 3 * n + 1}.</li>
 * <li>Repeat indefinitely, the conjecture is that {@code n = 1} eventually.</li>
 * </ul>
 */
public final class CollatzUtils {

    private CollatzUtils() {
        // empty
    }

    /**
     * Applies the Collatz conjecture on {@code value}, and count the steps to reach 1,
     * inclusive.
     * <p>
     * Examples:
     * <ul>
     * <li>1: 1 → 4 → 2 → 1. Therefore, the return value is 3.</li>
     * <li>2: 2 → 1. Therefore, the return value is 1.</li>
     * </ul>
     * <p>
     * This implementation works by using a
     * {@link Stream#anyMatch(java.util.function.Predicate)} short-circuiting terminal
     * operation, so although the right answer is returned at the end, it does alter the
     * definition of a stream of Collatz sequence numbers.
     *
     * @param value the value to start from.
     * @return the number of steps to end at 1.
     * @throws IllegalArgumentException if {@code value} is 0 or less.
     */
    public static long stepsFor(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0.");
        }
        final long[] result = new long[1];
        LongStream.iterate(value, v -> ++result[0] > 0 && v % 2 == 0 ? v / 2 : 3 * v + 1)
                    .anyMatch(v -> v == 1 && result[0] > v);
        return --result[0];
    }
}
