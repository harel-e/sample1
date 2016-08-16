package sample;

import org.testng.annotations.Test;

public class SystemTest {
    @Test
    public void testIntA() throws Exception {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("log.url="+System.getProperty("log.url"));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Thread.sleep(2000);
    }

    @Test
    public void testIntB() throws Exception {
        Thread.sleep(2000);
    }
}
