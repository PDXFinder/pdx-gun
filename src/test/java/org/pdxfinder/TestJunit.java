package org.pdxfinder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {

    @Test
    public void testAdd() {
        String str = "PDX Reference Data JUnit is working fine";
        assertEquals("PDX Reference Data JUnit is working fine",str);
    }
}