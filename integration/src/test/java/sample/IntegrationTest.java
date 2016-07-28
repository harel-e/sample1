package sample;

import org.testng.annotations.Test;

@Test(groups = "integration")
public class IntegrationTest {

    @Test
    public void testIntA() throws Exception {
        Thread.sleep(3000);
    }

    @Test
    public void testIntB() throws Exception {
        Thread.sleep(3000);
    }
}
