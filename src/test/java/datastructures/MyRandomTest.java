
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
    public void nextArrayReturnsArrayAndIsCorrectLength() {
        byte[] array1 = random.nextArray(0);
        byte[] array2 = random.nextArray(10);
        byte[] array3 = random.nextArray(1000);
        assertNotNull(array1);
        assertNotNull(array2);
        assertNotNull(array3);
        assertEquals(0, array1.length);
        assertEquals(10, array2.length);
        assertEquals(1000, array3.length);   
    }
    
    @Test
    public void arrayOfThreeWorks() {
        byte[] array = random.nextArray(3);
        assertEquals(-5, array[0]);
        assertEquals(-72, array[1]);
        assertEquals(74, array[2]);
    }
}
