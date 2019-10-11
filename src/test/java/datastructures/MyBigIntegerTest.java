package datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    public void myBigIntegerAddWorks() {
        assertEquals("10", nine.add(one).toString());
        assertEquals("2", one.add(one).toString());
        assertEquals("108", nine.add(ninetynine).toString());
        assertEquals("110", new MyBigInteger("109").add(one).toString());
        assertEquals("100000000000000000000000000000000000000000000000000", bigNumber1.add(bigNumber2).toString());
    }
    
    @Test
    public void myBigIntegerCompareToWorks() {
        assertEquals(0, nine.compareTo(nine));
        assertEquals(1, one.compareTo(zero));
        assertEquals(1, ninetynine.compareTo(one));
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
    
    @Test
    public void myBigIntegerIntValueReturnsCorrectValue() {
        assertEquals(9, nine.intValue());
        assertEquals(0, zero.intValue());
        assertEquals(99, ninetynine.intValue());
        assertEquals(-1, bigNumber1.intValue());
    }
    
    @Test
    public void myBigIntegerTestBitWorksCorrectly() {
        assertTrue(one.testBit(0));
        assertTrue(ninetynine.testBit(0));
        assertTrue(ninetynine.testBit(1));
        assertTrue(ninetynine.testBit(5));
        assertTrue(ninetynine.testBit(6));
        assertTrue(!ninetynine.testBit(2));
        assertTrue(!ninetynine.testBit(3));
        assertTrue(!ninetynine.testBit(4));
    }
    
    @Test
    public void myBigIntegerModWorksCorrectly() {
        assertEquals("0", nine.mod(one).toString());
        assertEquals("9", nine.mod(ninetynine).toString());
        assertEquals("0", ninetynine.mod(nine).toString());
        assertEquals("1", bigNumber1.mod(nine).toString());
    }
    
    @Test
    public void myBigIntegerBitLengthWorksCorrectly() {
        assertEquals(1, one.bitLength());
        assertEquals(7, ninetynine.bitLength());
        assertEquals(163, bigNumber1.bitLength());
    }
    
    @Test
    public void myBigIntegerModPowWorksCorrectly() {
        assertEquals("0", nine.modPow(nine, nine).toString());
        assertEquals("25", nine.modPow(ninetynine, new MyBigInteger("64")).toString());
        assertEquals("1", nine.modPow(new MyBigInteger("2"), new MyBigInteger("2")).toString());
        assertEquals("1", nine.modPow(new MyBigInteger("2"), new MyBigInteger("80")).toString());
    }
    
    @Test
    public void myBigIntegerMultiplyWorksCorrectly() {
        assertEquals("81", nine.multiply(nine).toString());
        assertEquals("9", nine.multiply(one).toString());
        assertEquals("0", nine.multiply(zero).toString());
        assertEquals("0", zero.multiply(one).toString());
        assertEquals("90000000000000000000000000000000000000000000000000", bigNumber1.multiply(nine).toString());
        assertEquals("90000000000000000000000000000000000000000000000000", nine.multiply(bigNumber1).toString());
        String value1 = "29512665430652752148753480226197736314359272517043832886063884637676943433478020332709411004889";
        String value2 = "3717398172938172956385761873561365086509831650987472109476543179875463";
        String result = "109710328550444118182444626074755353008758673259658339000940241676688146291635307868605149506127755809072094010042649909014870267037521471074498658923167688704138607";
        assertEquals(result, new MyBigInteger(value1).multiply(new MyBigInteger(value2)).toString());
        assertEquals("0", bigNumber1.multiply(zero).toString());
        assertEquals("0", zero.multiply(bigNumber2).toString());
        assertEquals("10000000000000000000000000000000000000000000000000", one.multiply(bigNumber1).toString());
    }
    
    @Test
    public void myBigIntegerSubtractWorksCorrectly() {
        assertEquals("8", nine.subtract(one).toString());
        assertEquals("9", nine.subtract(zero).toString());
        assertEquals("0", nine.subtract(nine).toString());
        assertEquals("90", ninetynine.subtract(nine).toString());
        assertEquals("91", new MyBigInteger("100").subtract(nine).toString());
        assertEquals("95", new MyBigInteger("104").subtract(nine).toString());
        assertEquals("80000000000000000000000000000000000000000000000000", bigNumber2.subtract(bigNumber1).toString());
        assertEquals("89999999999999999999999999999999999999999999999991", bigNumber2.subtract(nine).toString());
    }
    
    @Test
    public void myBigIntegerDivideWorksCorrectly() {
        assertEquals("11", ninetynine.divide(nine).toString());
        assertEquals("1", nine.divide(nine).toString());
        assertEquals("9", nine.divide(one).toString());
        assertEquals("0", zero.divide(bigNumber1).toString());
        assertEquals("4", new MyBigInteger("14").divide(new MyBigInteger("3")).toString());
        assertEquals("9", bigNumber2.divide(bigNumber1).toString());
        assertEquals("4", new MyBigInteger("14000000023213123123").divide(new MyBigInteger("3000000000000000000")).toString());
        String value1 = "109710328550444118182444626074755353008758673259658339000940241676688146291635307868605149506127755809072094010042649909014870267037521471074498658923167688704138607";
        String value2 = "29512665430652752148753480226197736314359272517043832886063884637676943433478020332709411004889";
        String result = "3717398172938172956385761873561365086509831650987472109476543179875463";
        assertEquals(result, new MyBigInteger(value1).divide(new MyBigInteger(value2)).toString());
        assertEquals("0", bigNumber1.divide(new MyBigInteger("10000000000000000000000000000000000000000000000001")).toString());
        assertEquals("1", bigNumber1.divide(new MyBigInteger("9999999999999999999999999999999999999999999999999")).toString());
    }
    
    
}
