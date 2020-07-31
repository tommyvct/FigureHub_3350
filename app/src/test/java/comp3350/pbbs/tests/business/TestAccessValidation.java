package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;

import comp3350.pbbs.business.AccessValidation;

/**
 * TestAccessValidation
 * Group4
 * PBBS
 *
 * This class tests AccessValidation class
 */
public class TestAccessValidation extends TestCase {

    /**
     * Testing Valid input
     */
    public void testValidInput() {
        //Testing isValidName()
        assertTrue(AccessValidation.isValidName("cool name"));
        assertTrue(AccessValidation.isValidName(" cool name "));
        assertTrue(AccessValidation.isValidName("COOL NAME"));
        assertTrue(AccessValidation.isValidName(" name"));
        assertTrue(AccessValidation.isValidName("cool "));
        assertTrue(AccessValidation.isValidName("c. n."));

        //Testing isValidExpirationDate()
        assertEquals(0, AccessValidation.isValidExpirationDate("1", "2068"));
        assertEquals(0, AccessValidation.isValidExpirationDate("12", "2068"));

        //Testing isValidPayDay()
        assertTrue(AccessValidation.isValidPayDate(1));
        assertTrue(AccessValidation.isValidPayDate(15));
        assertTrue(AccessValidation.isValidPayDate(31));

        //Testing isValidDateTime()
        assertTrue(AccessValidation.isValidDateTime("30/3/2020", "0:00"));
        assertTrue(AccessValidation.isValidDateTime("30/3/2020", "2:30"));
        assertTrue(AccessValidation.isValidDateTime("30-3-2020", "23:59"));

        //Test isValidAmount()
        assertTrue(AccessValidation.isValidAmount("20.00"));
        assertTrue(AccessValidation.isValidAmount("1.20"));
        assertTrue(AccessValidation.isValidAmount("0.89"));
        assertTrue(AccessValidation.isValidAmount("12345.56"));
        assertTrue(AccessValidation.isValidAmount("20"));
        assertTrue(AccessValidation.isValidAmount("0000000000123456789"));
        assertTrue(AccessValidation.isValidAmount("00000000001234567.89"));

        //Testing isValidDescription()
        assertTrue(AccessValidation.isValidDescription("Bought groceries."));
        assertTrue(AccessValidation.isValidDescription("1234"));
        assertTrue(AccessValidation.isValidDescription("  Bought groceries  "));
        assertTrue(AccessValidation.isValidDescription("<<>>>????:::{{{}}}"));
        assertTrue(AccessValidation.isValidDescription("<BOUGHT groceries>"));

        //Testing parseAmount()
        assertEquals(1f, AccessValidation.parseAmount("1.00"));
        assertEquals(1f, AccessValidation.parseAmount("1"));
        assertEquals(1f, AccessValidation.parseAmount(" 1"));
        assertEquals(1f, AccessValidation.parseAmount("1 "));
        assertEquals(10f, AccessValidation.parseAmount("10.00"));
        assertEquals(1.10f, AccessValidation.parseAmount("1.10"));
        assertEquals(1.10f, AccessValidation.parseAmount(" 1.10"));
        assertEquals(1.10f, AccessValidation.parseAmount("1.10 "));
        assertEquals(1.10f, AccessValidation.parseAmount("1.1"));
        assertEquals(10.10f, AccessValidation.parseAmount("10.1"));
        assertEquals(10.10f, AccessValidation.parseAmount("10.10"));
        assertEquals(55.58f, AccessValidation.parseAmount("55.58"));
        assertEquals(1.23f, AccessValidation.parseAmount("1.23"));
        assertEquals(12345.67f, AccessValidation.parseAmount("12345.67"));
        assertEquals(10000.00f, AccessValidation.parseAmount("10000"));
        assertEquals(123456789f, AccessValidation.parseAmount("0000000000123456789"));
        assertEquals(1234567.89f, AccessValidation.parseAmount("00000000001234567.89"));
    }

    /**
     * Testing null and empty values
     */
    public void testNullAndEmpty() {
        //Testing isValidName()
        assertFalse(AccessValidation.isValidName(""));
        assertFalse(AccessValidation.isValidName(" "));
        assertFalse(AccessValidation.isValidName(null));

        //Testing isValidExpirationDate()
        assertEquals(7, AccessValidation.isValidExpirationDate("", "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate(" ", "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate(null, "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", ""));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", " "));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", null));

        //Testing isValidDateTime()
        String testDate = "30/3/2020";
        String testTime = "2:30";
        assertFalse(AccessValidation.isValidDateTime(testDate, null));
        assertFalse(AccessValidation.isValidDateTime(testDate, ""));
        assertFalse(AccessValidation.isValidDateTime(testDate, " "));
        assertFalse(AccessValidation.isValidDateTime(null, testTime));
        assertFalse(AccessValidation.isValidDateTime("", testTime));
        assertFalse(AccessValidation.isValidDateTime(" ", testTime));

        //Test isValidAmount()
        assertFalse(AccessValidation.isValidAmount(""));
        assertFalse(AccessValidation.isValidAmount(" "));
        assertFalse(AccessValidation.isValidAmount(null));

        //Testing isValidDescription()
        assertFalse(AccessValidation.isValidDescription(null));
        assertFalse(AccessValidation.isValidDescription(""));
        assertFalse(AccessValidation.isValidDescription(" "));

        //Testing parseAmount()
        assertNull(AccessValidation.parseAmount(""));
        assertNull(AccessValidation.parseAmount("  "));
        assertNull(AccessValidation.parseAmount(null));
    }

    /**
     * Testing Negative input (only for numeric validation)
     */
    public void testNegativeInput() {
        //Testing isValidExpirationDate()
        assertEquals(1, AccessValidation.isValidExpirationDate("-1", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("-20", "2068"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "-20"));
        assertEquals(4, AccessValidation.isValidExpirationDate("-1", "20"));

        //Testing isValidPayDay()
        assertFalse(AccessValidation.isValidPayDate(-1));
        assertFalse(AccessValidation.isValidPayDate(-15));

        //Testing isValidDateTime()
        assertFalse(AccessValidation.isValidDateTime("-30/10/2020", "1:00"));
        assertFalse(AccessValidation.isValidDateTime("30/-10/2020", "1:00"));
        assertFalse(AccessValidation.isValidDateTime("30/10/-2020", "1:00"));
        assertFalse(AccessValidation.isValidDateTime("30/10/2020", "-1:00"));

        //Test isValidAmount()
        assertFalse(AccessValidation.isValidAmount("-20"));
        assertFalse(AccessValidation.isValidAmount("-2.52"));
        assertFalse(AccessValidation.isValidAmount("-0.58"));

        //Testing parseAmount()
        assertNull(AccessValidation.parseAmount("-1.23"));
        assertNull(AccessValidation.parseAmount("-0.23"));
        assertNull(AccessValidation.parseAmount("-1000"));
    }

    /**
     * Test invalid String input (bad strings for string validation, and any string input for numeric validation)
     */
    public void testInvalidString() {
        //Testing isValidName()
        assertFalse(AccessValidation.isValidName("X AE A-12"));

        //Testing isValidExpirationDate()
        Calendar calender = Calendar.getInstance();
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(5, AccessValidation.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, AccessValidation.isValidExpirationDate("1", Integer.toString(currYear - 1)));
        assertEquals(7, AccessValidation.isValidExpirationDate("string", "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", "string"));
        assertEquals(7, AccessValidation.isValidExpirationDate("string", "string 2"));

        //Testing isValidDateTime()
        assertFalse(AccessValidation.isValidDateTime("30/3/2020", "\n"));
        assertFalse(AccessValidation.isValidDateTime("30/3/2020", "time"));
        assertFalse(AccessValidation.isValidDateTime("\n", "2:30"));
        assertFalse(AccessValidation.isValidDateTime("date", "2:30"));

        //Test isValidAmount()
        assertFalse(AccessValidation.isValidAmount("number"));
        assertFalse(AccessValidation.isValidAmount("\n"));
        assertFalse(AccessValidation.isValidAmount("one"));
        assertFalse(AccessValidation.isValidAmount("20.205"));

        //Testing isValidDescription()
        assertFalse(AccessValidation.isValidDescription("\n"));

        //Testing parseAmount()
        assertNull(AccessValidation.parseAmount("abcd"));
        assertNull(AccessValidation.parseAmount("1.234"));
        assertNull(AccessValidation.parseAmount("1.23 4"));
        assertNull(AccessValidation.parseAmount("1 .234"));
        assertNull(AccessValidation.parseAmount("1,234"));
        assertNull(AccessValidation.parseAmount("1.2{34"));
        assertNull(AccessValidation.parseAmount("10.234"));
        assertNull(AccessValidation.parseAmount("one"));
        assertNull(AccessValidation.parseAmount("0two"));
        assertNull(AccessValidation.parseAmount("1three"));
        assertNull(AccessValidation.parseAmount("123four"));
        assertNull(AccessValidation.parseAmount("1two3"));
        assertNull(AccessValidation.parseAmount("1.four"));
        assertNull(AccessValidation.parseAmount("1.zero2"));
    }

    /**
     * Testing out of bounds (for date and time)
     */
    public void testOutOfBounds() {
        //Testing isValidExpirationDate()
        assertEquals(1, AccessValidation.isValidExpirationDate("13", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("24", "2068"));
        assertEquals(2, AccessValidation.isValidExpirationDate("1", "2100"));
        assertEquals(2, AccessValidation.isValidExpirationDate("1", "3000"));
        assertEquals(3, AccessValidation.isValidExpirationDate("13", "2100"));
        assertEquals(3, AccessValidation.isValidExpirationDate("24", "3000"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "900"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "90"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "9"));

        //Testing isValidPayDay()
        assertFalse(AccessValidation.isValidPayDate(0));
        assertFalse(AccessValidation.isValidPayDate(32));
        assertFalse(AccessValidation.isValidPayDate(64));

        //Testing isValidDateTime()
        assertFalse(AccessValidation.isValidDateTime("30/3/2020", "24:00"));
        assertFalse(AccessValidation.isValidDateTime("30/3/2020", "48:00"));
        assertFalse(AccessValidation.isValidDateTime("30/3/2020", "23:60"));
        assertFalse(AccessValidation.isValidDateTime("30/13/2020", "0:00"));
        assertFalse(AccessValidation.isValidDateTime("30/00/2020", "0:00"));
        assertFalse(AccessValidation.isValidDateTime("30/02/2020", "0:00"));
    }
}
