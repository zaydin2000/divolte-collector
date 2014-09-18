package io.divolte.server;

import static org.junit.Assert.*;
import io.divolte.server.CookieValues.CookieValue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CookieValuesTest {
    @Test
    public void cookieValuesShouldBeUnique() {
        final int num = 100000;
        final Set<String> values = new HashSet<>(num + num / 2);
        for (int c = 0; c < num; c++) {
            values.add(CookieValues.generate().value);
        }

        assertEquals(num, values.size());
    }

    @Test
    public void cookieValuesShouldEncodeTimestamp() {
        CookieValue cv = CookieValues.generate(42);
        assertEquals(42, CookieValues.tryParse(cv.value).get().timestamp);
    }

    @Test
    public void equalCookieValuesShouldBeConsistentWithHashcodeAndEquals() {
        CookieValue left = CookieValues.generate();
        CookieValue right = CookieValues.tryParse(left.value).get();

        assertTrue(left.equals(right));
        assertEquals(left.hashCode(), right.hashCode());

        assertNotEquals(CookieValues.generate(42), CookieValues.generate(42));
    }

    @Test
    public void cookieValuesShouldParseVersionAndTimestamp() {
        String stringValue = "0:16:5mRCeUO4p2_6R7u1m9ZoxXG2AfBeJeHD";
        CookieValue value = CookieValues.tryParse(stringValue).get();
        assertEquals(42, value.timestamp);
        assertEquals('0', value.version);
        assertEquals(stringValue, value.value);
    }
}
