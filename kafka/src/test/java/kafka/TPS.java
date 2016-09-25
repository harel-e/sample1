package kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class TPS extends TimerTask {

    private AtomicLong transactions = new AtomicLong();
    private AtomicLong totalLatency = new AtomicLong();
    private AtomicLong total = new AtomicLong();
    private String prefix;

    private Logger logger;

    public TPS(String prefix) {
        this.prefix = prefix;
        logger = LoggerFactory.getLogger(prefix);
    }

    @Override
    public void run() {
        if (total.longValue() > 0) {
            //System.out.println(prefix + " TPS: " + transactions.getAndSet(0) + ", Total: " + total.longValue() + ", Latency: " + totalLatency.longValue() / total.longValue());
            long tx = transactions.getAndSet(0);
            String latency;
            if (tx > 0) {
                latency = String.valueOf(totalLatency.getAndSet(0) / tx);
            } else {
                latency = "n/a";
            }
            System.out.println("TPS: " + tx + ", Total: " + total.longValue() + ", Latency: " + latency + " us");
        }
    }

    public TPS start() {
        new Timer(true).scheduleAtFixedRate(this, 1000, 1000);
        return this;
    }

    public long now() {
        return System.nanoTime();
    }

    public void inc(long latency) {
        totalLatency.addAndGet(latency / 1000);
        transactions.incrementAndGet();
        total.incrementAndGet();
    }
}
