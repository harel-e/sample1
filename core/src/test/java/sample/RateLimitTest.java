package sample;

import com.google.common.util.concurrent.RateLimiter;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class RateLimitTest {

    public void testName() throws Exception {
        TPS tps = new TPS("").start();
        RateLimiter limiter = RateLimiter.create(0.166);

        for (int i=0;i<1000;i++) {
            double sleep = limiter.acquire();
            System.out.println(sleep);
            tps.inc(0);
        }
    }

    public void testNoWait() throws Exception {
        TPS tps = new TPS("").start();
        RateLimiter limiter = RateLimiter.create(0.166, 10, TimeUnit.SECONDS);

        for (int i=0;i<1000;i++) {
            double sleep = limiter.acquire();
            System.out.println(sleep);
            tps.inc(0);
        }
    }

    public void testTryAcquire() throws Exception {
        TPS tps = new TPS("").start();
        RateLimiter limiter = RateLimiter.create(10/60.0);

        for (int i=0;i<1000;i++) {
            boolean success = limiter.tryAcquire();
            System.out.println(success);
            if (success) {
                tps.inc(0);
            }
            Thread.sleep(1000);
        }
    }
}
