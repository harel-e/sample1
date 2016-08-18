package sample;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test(groups = "unit")
public class SampleTest {

    public static final int ACTUAL = 1;

    @Test
    public void testA() throws Exception {
        for (int i=0;i<10;i++) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Thread.sleep(10);
        }
    }

    @Test
    public void testB() throws Exception {
        assertEquals(ACTUAL,1);
    }

    @Test
    public void testC() throws Exception {
    }

    @Test
    public void testD() throws Exception {
    }

    @Test
    public void testE() throws Exception {
    }

    @Test
    public void testF() throws Exception {
    }

    @Test
    public void testG() throws Exception {
    }

    @Test
    public void testH() throws Exception {
    }

    @Test
    public void testI() throws Exception {
    }
}
