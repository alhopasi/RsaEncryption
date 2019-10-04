package datastructures;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class MyBigIntegerTest {

    private MyBigInteger zero;
    private MyBigInteger one;
    private MyBigInteger nine;
    private MyBigInteger ninetynine;
    private MyBigInteger bigNumber1;
    private MyBigInteger bigNumber2;

    @Before
    public void setUp() {
        zero = MyBigInteger.ZERO;
        one = MyBigInteger.ONE;
        nine = new MyBigInteger("9");
        ninetynine = new MyBigInteger("99");
        bigNumber1 = new MyBigInteger("10000000000000000000000000000000000000000000000000");
        bigNumber2 = new MyBigInteger("90000000000000000000000000000000000000000000000000");
    }

    @Test
    public void myBigIntegerIsCreated() {
        assertNotNull(one);
        assertNotNull(zero);
        assertNotNull(nine);
    }

    @Test
    public void myBigIntegerIsCreatedFromByteArray() {
        byte[] bytes = {0b01111111, 0b00000001};
        MyBigInteger number = new MyBigInteger(bytes);
        assertNotNull(number);
    }

    @Test
    public void myBigIntegerCreatedFromByteArrayGivesCorrectValue() {
        byte[] bytes = {0x1F};
        MyBigInteger number = new MyBigInteger(bytes);
        byte[] bytes2 = {-1, -1, -1, -1};
        MyBigInteger number2 = new MyBigInteger(bytes2);
        byte[] bytes3 = {0b00000000};
        MyBigInteger number3 = new MyBigInteger(bytes3);
        byte[] bytes4 = {0b00000011};
        MyBigInteger number4 = new MyBigInteger(bytes4);
//        byte[] bytes5 = {0x01, 0x11, 0x11, 0x11, 0x11};
//        MyBigInteger number5 = new MyBigInteger(bytes5);

        assertEquals("31", number.toString());
        assertEquals("42949672950", number2.toString());
        assertEquals("0", number3.toString());
        assertEquals("3", number4.toString());
//        assertEquals("4294967295", number5.toString());
    }
    
    @Test
    public void myBigIntegerAddWorks() {
        assertEquals("10", nine.add(one).toString());
        assertEquals("2", one.add(one).toString());
        assertEquals("108", nine.add(ninetynine).toString());
        assertEquals("100000000000000000000000000000000000000000000000000", bigNumber1.add(bigNumber2).toString());
    }
    
    @Test
    public void myBigIntegerCompareToWorks() {
        assertEquals(0, nine.compareTo(nine));
        assertEquals(1, one.compareTo(zero));
        assertEquals(-1, one.compareTo(nine));
    }
    
    @Test
    public void myBigIntegerShiftLeftWorks() {
        assertEquals("18", nine.shiftLeft(1).toString());
        assertEquals("0", MyBigInteger.ZERO.shiftLeft(1).toString());
        assertEquals("36", nine.shiftLeft(2).toString());
    }
    
    @Test
    public void myBigIntegerShiftRightWorks() {
        assertEquals("4", nine.shiftRight(1).toString());
        assertEquals("0", one.shiftRight(1).toString());
        assertEquals("24", ninetynine.shiftRight(2).toString());
        assertEquals("5000000000000000000000000000000000000000000000000", bigNumber1.shiftRight(1).toString());
    }
    
    
}
