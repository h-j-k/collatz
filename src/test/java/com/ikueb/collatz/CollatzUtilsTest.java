package com.ikueb.collatz;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Iterator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CollatzUtilsTest {

    static enum TestCase {
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
        return Stream.of(TestCase.values())
                .map((current) -> new Object[] { current.value, current.expected }).iterator();
    }

    @DataProvider(name = "twos-test-cases")
    public Iterator<Object[]> getTwosTestCases() {
        return LongStream.range(1, 11).boxed()
                .map((v) -> new Object[] { Long.valueOf((long) Math.pow(2, v.doubleValue())), v })
                .iterator();
    }

    @Test(dataProvider = "test-cases")
    public void testFirstTen(Long value, Long expected) {
        assertThat(Long.valueOf(CollatzUtils.stepsFor(value.longValue())), equalTo(expected));
    }

    @Test(dataProvider = "twos-test-cases")
    public void testPowersOfTwo(Long value, Long expected) {
        assertThat(Long.valueOf(CollatzUtils.stepsFor(value.longValue())), equalTo(expected));
    }

    @Test
    public void testLargeValue() {
        assertThat(Long.valueOf(CollatzUtils.stepsFor(9780657631L)), equalTo(Long.valueOf(1132)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        CollatzUtils.stepsFor(0);
    }

}
