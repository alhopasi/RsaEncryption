package datastructures;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class MyRandomTest {

    MyRandom random;

    @Before
    public void setUp() {
        random = new MyRandom();
        random.setSeed(1337);
    }

    @Test
    public void myRandomIsCreated() {
        assertNotNull(random);
    }

    @Test
    public void nextByteArrayReturnsArrayAndIsCorrectLength() {
        byte[] array1 = random.nextByteArray(0);
        byte[] array2 = random.nextByteArray(10);
        byte[] array3 = random.nextByteArray(1000);
        assertNotNull(array1);
        assertNotNull(array2);
        assertNotNull(array3);
        assertEquals(0, array1.length);
        assertEquals(10, array2.length);
        assertEquals(1000, array3.length);
    }

    @Test
    public void byteArrayOfThreeWorks() {
        byte[] array = random.nextByteArray(3);
        assertEquals(123, array[0]);
        assertEquals(113, array[1]);
        assertEquals(42, array[2]);
    }

    @Test
    public void nextDecimalStringReturnsAndIsCorrectLength() {
        String decimal1 = random.nextDecimalString(0);
        String decimal2 = random.nextDecimalString(10);
        String decimal3 = random.nextDecimalString(999);
        assertEquals(0, decimal1.length());
        assertEquals(10, decimal2.length());
        assertEquals(999, decimal3.length());
    }

    @Test
    public void nextDecimalNotEvenStringReturnsAndIsCorrectLength() {
        String decimal1 = random.nextDecimalStringNotEven(0);
        String decimal2 = random.nextDecimalStringNotEven(10);
        String decimal3 = random.nextDecimalStringNotEven(999);
        assertEquals(0, decimal1.length());
        assertEquals(10, decimal2.length());
        assertEquals(999, decimal3.length());
    }

    @Test
    public void nextDecimalStringWorks() {
        random.setSeed(124);
        String decimal0 = random.nextDecimalString(2);
        random.setSeed(1337);
        String decimal1 = random.nextDecimalString(0);
        String decimal2 = random.nextDecimalString(3);
        String decimal3 = random.nextDecimalString(20);
        assertEquals("92", decimal0);
        assertEquals("", decimal1);
        assertEquals("564", decimal2);
        assertEquals("78675144667051528226", decimal3);
    }

    @Test
    public void nextDecimalNotEvenStringWorks() {
        String decimal1 = random.nextDecimalStringNotEven(0);
        String decimal2 = random.nextDecimalStringNotEven(3);
        String decimal3 = random.nextDecimalStringNotEven(20);
        assertEquals("", decimal1);
        assertEquals("567", decimal2);
        assertEquals("86751446670515282261", decimal3);
    }
}
