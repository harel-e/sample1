package sample;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

@Test(groups = "integration")
public class IntegrationTest {

    @Test
    public void testIntA() throws Exception {
        fail();
        Thread.sleep(3000);
    }

    @Test
    public void testIntB() throws Exception {
        Thread.sleep(3000);
    }
}
