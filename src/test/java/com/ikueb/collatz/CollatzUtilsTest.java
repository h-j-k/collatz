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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.LongStream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CollatzUtilsTest {

    enum TestCase {
        ONE(3), TWO(1), THREE(7), FOUR(2), FIVE(5), SIX(8), SEVEN(16), EIGHT(3), NINE(19), TEN(6);

        private Long value;
        private Long expected;

        TestCase(long expected) {
            this.value = Long.valueOf(ordinal() + 1);
            this.expected = Long.valueOf(expected);
        }
    }

    @DataProvider(name = "test-cases")
    public Iterator<Object[]> getTestCases() {
        return Arrays.stream(TestCase.values())
                .map(test -> new Object[] { test.value, test.expected }).iterator();
    }

    @DataProvider(name = "twos-test-cases")
    public Iterator<Object[]> getTwosTestCases() {
        return LongStream.range(1, 61).boxed()
                .map(v -> new Object[] { Long.valueOf((long) Math.pow(2, v.doubleValue())), v })
                .iterator();
    }

    @Test(dataProvider = "test-cases")
    public void testCaseFor(Long value, Long expected) {
        assertThat(Long.valueOf(CollatzUtils.stepsFor(value.longValue())), equalTo(expected));
    }

    @Test(dataProvider = "twos-test-cases")
    public void testPowersOfTwo(Long value, Long expected) {
        testCaseFor(value, expected);
    }

    @Test
    public void testLargeValue() {
        testCaseFor(Long.valueOf(9780657631L), Long.valueOf(1132));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        CollatzUtils.stepsFor(0);
    }

}
