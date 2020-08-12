package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.business.Parser;

public class TestParser extends TestCase {

    /**
     * Testing Valid input
     */
    public void testValidInput() {
        //Testing parseAmount()
        assertEquals(1f, Parser.parseAmount("1.00"));
        assertEquals(1f, Parser.parseAmount("1"));
        assertEquals(1f, Parser.parseAmount(" 1"));
        assertEquals(1f, Parser.parseAmount("1 "));
        assertEquals(10f, Parser.parseAmount("10.00"));
        assertEquals(1.10f, Parser.parseAmount("1.10"));
        assertEquals(1.10f, Parser.parseAmount(" 1.10"));
        assertEquals(1.10f, Parser.parseAmount("1.10 "));
        assertEquals(1.10f, Parser.parseAmount("1.1"));
        assertEquals(10.10f, Parser.parseAmount("10.1"));
        assertEquals(10.10f, Parser.parseAmount("10.10"));
        assertEquals(55.58f, Parser.parseAmount("55.58"));
        assertEquals(1.23f, Parser.parseAmount("1.23"));
        assertEquals(12345.67f, Parser.parseAmount("12345.67"));
        assertEquals(10000.00f, Parser.parseAmount("10000"));
        assertEquals(123456789f, Parser.parseAmount("0000000000123456789"));
        assertEquals(1234567.89f, Parser.parseAmount("00000000001234567.89"));
    }

    /**
     * Testing null and empty values
     */
    public void testNullAndEmpty() {
        //Testing parseAmount()
        assertNull(Parser.parseAmount(""));
        assertNull(Parser.parseAmount("  "));
        assertNull(Parser.parseAmount(null));
    }

    /**
     * Testing Negative input (only for numeric validation)
     */
    public void testNegativeInput() {
        //Testing parseAmount()
        assertNull(Parser.parseAmount("-1.23"));
        assertNull(Parser.parseAmount("-0.23"));
        assertNull(Parser.parseAmount("-1000"));
    }

    /**
     * Test invalid String input (bad strings for string validation, and any string input for numeric validation)
     */
    public void testInvalidString() {
        //Testing parseAmount()
        assertNull(Parser.parseAmount("abcd"));
        assertNull(Parser.parseAmount("1.234"));
        assertNull(Parser.parseAmount("1.23 4"));
        assertNull(Parser.parseAmount("1 .234"));
        assertNull(Parser.parseAmount("1,234"));
        assertNull(Parser.parseAmount("1.2{34"));
        assertNull(Parser.parseAmount("10.234"));
        assertNull(Parser.parseAmount("one"));
        assertNull(Parser.parseAmount("0two"));
        assertNull(Parser.parseAmount("1three"));
        assertNull(Parser.parseAmount("123four"));
        assertNull(Parser.parseAmount("1two3"));
        assertNull(Parser.parseAmount("1.four"));
        assertNull(Parser.parseAmount("1.zero2"));
    }
}
