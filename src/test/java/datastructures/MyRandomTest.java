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
        assertEquals(-107, array[2]);
    }   
}