package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;

import comp3350.pbbs.business.Validation;

/**
 * TestValidation
 * Group4
 * PBBS
 *
 * This class tests Validation class
 */
public class TestValidation extends TestCase {

    /**
     * Testing Valid input
     */
    public void testValidInput() {
        //Testing isValidName()
        assertTrue(Validation.isValidName("cool name"));
        assertTrue(Validation.isValidName(" cool name "));
        assertTrue(Validation.isValidName("COOL NAME"));
        assertTrue(Validation.isValidName(" name"));
        assertTrue(Validation.isValidName("cool "));
        assertTrue(Validation.isValidName("c. n."));

        //Testing isValidExpirationDate()
        assertEquals(0, Validation.isValidExpirationDate("1", "2068"));
        assertEquals(0, Validation.isValidExpirationDate("12", "2068"));

        //Testing isValidPayDay()
        assertTrue(Validation.isValidPayDate(1));
        assertTrue(Validation.isValidPayDate(15));
        assertTrue(Validation.isValidPayDate(31));

        //Testing isValidDateTime()
        assertTrue(Validation.isValidDateTime("30/3/2020", "0:00"));
        assertTrue(Validation.isValidDateTime("30/3/2020", "2:30"));
        assertTrue(Validation.isValidDateTime("30-3-2020", "23:59"));

        //Test isValidAmount()
        assertTrue(Validation.isValidAmount("20.00"));
        assertTrue(Validation.isValidAmount("1.20"));
        assertTrue(Validation.isValidAmount("0.89"));
        assertTrue(Validation.isValidAmount("12345.56"));
        assertTrue(Validation.isValidAmount("20"));
        assertTrue(Validation.isValidAmount("0000000000123456789"));
        assertTrue(Validation.isValidAmount("00000000001234567.89"));

        //Testing isValidDescription()
        assertTrue(Validation.isValidDescription("Bought groceries."));
        assertTrue(Validation.isValidDescription("1234"));
        assertTrue(Validation.isValidDescription("  Bought groceries  "));
        assertTrue(Validation.isValidDescription("<<>>>????:::{{{}}}"));
        assertTrue(Validation.isValidDescription("<BOUGHT groceries>"));
    }

    /**
     * Testing null and empty values
     */
    public void testNullAndEmpty() {
        //Testing isValidName()
        assertFalse(Validation.isValidName(""));
        assertFalse(Validation.isValidName(" "));
        assertFalse(Validation.isValidName(null));

        //Testing isValidExpirationDate()
        assertEquals(7, Validation.isValidExpirationDate("", "2068"));
        assertEquals(7, Validation.isValidExpirationDate(" ", "2068"));
        assertEquals(7, Validation.isValidExpirationDate(null, "2068"));
        assertEquals(7, Validation.isValidExpirationDate("1", ""));
        assertEquals(7, Validation.isValidExpirationDate("1", " "));
        assertEquals(7, Validation.isValidExpirationDate("1", null));

        //Testing isValidDateTime()
        String testDate = "30/3/2020";
        String testTime = "2:30";
        assertFalse(Validation.isValidDateTime(testDate, null));
        assertFalse(Validation.isValidDateTime(testDate, ""));
        assertFalse(Validation.isValidDateTime(testDate, " "));
        assertFalse(Validation.isValidDateTime(null, testTime));
        assertFalse(Validation.isValidDateTime("", testTime));
        assertFalse(Validation.isValidDateTime(" ", testTime));

        //Test isValidAmount()
        assertFalse(Validation.isValidAmount(""));
        assertFalse(Validation.isValidAmount(" "));
        assertFalse(Validation.isValidAmount(null));

        //Testing isValidDescription()
        assertFalse(Validation.isValidDescription(null));
        assertFalse(Validation.isValidDescription(""));
        assertFalse(Validation.isValidDescription(" "));
    }

    /**
     * Testing Negative input (only for numeric validation)
     */
    public void testNegativeInput() {
        //Testing isValidExpirationDate()
        assertEquals(1, Validation.isValidExpirationDate("-1", "2068"));
        assertEquals(1, Validation.isValidExpirationDate("-20", "2068"));
        assertEquals(4, Validation.isValidExpirationDate("1", "-20"));
        assertEquals(4, Validation.isValidExpirationDate("-1", "20"));

        //Testing isValidPayDay()
        assertFalse(Validation.isValidPayDate(-1));
        assertFalse(Validation.isValidPayDate(-15));

        //Testing isValidDateTime()
        assertFalse(Validation.isValidDateTime("-30/10/2020", "1:00"));
        assertFalse(Validation.isValidDateTime("30/-10/2020", "1:00"));
        assertFalse(Validation.isValidDateTime("30/10/-2020", "1:00"));
        assertFalse(Validation.isValidDateTime("30/10/2020", "-1:00"));

        //Test isValidAmount()
        assertFalse(Validation.isValidAmount("-20"));
        assertFalse(Validation.isValidAmount("-2.52"));
        assertFalse(Validation.isValidAmount("-0.58"));
    }

    /**
     * Test invalid String input (bad strings for string validation, and any string input for numeric validation)
     */
    public void testInvalidString() {
        //Testing isValidName()
        assertFalse(Validation.isValidName("X AE A-12"));

        //Testing isValidExpirationDate()
        Calendar calender = Calendar.getInstance();
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(5, Validation.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, Validation.isValidExpirationDate("1", Integer.toString(currYear - 1)));
        assertEquals(7, Validation.isValidExpirationDate("string", "2068"));
        assertEquals(7, Validation.isValidExpirationDate("1", "string"));
        assertEquals(7, Validation.isValidExpirationDate("string", "string 2"));

        //Testing isValidDateTime()
        assertFalse(Validation.isValidDateTime("30/3/2020", "\n"));
        assertFalse(Validation.isValidDateTime("30/3/2020", "time"));
        assertFalse(Validation.isValidDateTime("\n", "2:30"));
        assertFalse(Validation.isValidDateTime("date", "2:30"));

        //Test isValidAmount()
        assertFalse(Validation.isValidAmount("number"));
        assertFalse(Validation.isValidAmount("\n"));
        assertFalse(Validation.isValidAmount("one"));
        assertFalse(Validation.isValidAmount("20.205"));

        //Testing isValidDescription()
        assertFalse(Validation.isValidDescription("\n"));
    }

    /**
     * Testing out of bounds (for date and time)
     */
    public void testOutOfBounds() {
        //Testing isValidExpirationDate()
        assertEquals(1, Validation.isValidExpirationDate("13", "2068"));
        assertEquals(1, Validation.isValidExpirationDate("24", "2068"));
        assertEquals(2, Validation.isValidExpirationDate("1", "2100"));
        assertEquals(2, Validation.isValidExpirationDate("1", "3000"));
        assertEquals(3, Validation.isValidExpirationDate("13", "2100"));
        assertEquals(3, Validation.isValidExpirationDate("24", "3000"));
        assertEquals(4, Validation.isValidExpirationDate("1", "900"));
        assertEquals(4, Validation.isValidExpirationDate("1", "90"));
        assertEquals(4, Validation.isValidExpirationDate("1", "9"));

        //Testing isValidPayDay()
        assertFalse(Validation.isValidPayDate(0));
        assertFalse(Validation.isValidPayDate(32));
        assertFalse(Validation.isValidPayDate(64));

        //Testing isValidDateTime()
        assertFalse(Validation.isValidDateTime("30/3/2020", "24:00"));
        assertFalse(Validation.isValidDateTime("30/3/2020", "48:00"));
        assertFalse(Validation.isValidDateTime("30/3/2020", "23:60"));
        assertFalse(Validation.isValidDateTime("30/13/2020", "0:00"));
        assertFalse(Validation.isValidDateTime("30/00/2020", "0:00"));
        assertFalse(Validation.isValidDateTime("30/02/2020", "0:00"));
    }
}
