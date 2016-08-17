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
        for (int i=0;i<10;i++) {
            System.out.println("test B - "+i);
            System.out.println("log.url="+System.getProperty("log.url"));
            Thread.sleep(500);
        }
    }
}
