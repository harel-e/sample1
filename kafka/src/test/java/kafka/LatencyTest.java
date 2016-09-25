package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class LatencyTest {

    private TPS tps = new TPS("").start();

    @Test(enabled = false)
    public void testLatency() throws Exception {

        new Thread(this::producer).start();
        new Thread(this::consumer).start();
        Thread.sleep(25000);
    }

    private void producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 1);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Random random = new Random();
        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100000; i++) {
            String val = Long.toString(tps.now());
            producer.send(new ProducerRecord<>("topic1", Integer.toString(i), val));
            producer.flush();



            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        System.out.println("producer - before close");
        producer.close();
        System.out.println("producer - after close");

    }

    private void consumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("fetch.min.bytes", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //consumer.subscribe(Arrays.asList("topic1"));
        consumer.assign(Arrays.asList(new TopicPartition("topic1", 0)));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            //System.out.println(records.count());
            for (ConsumerRecord<String, String> record : records) {
                tps.inc(tps.now()-Long.parseLong(record.value()));
                //System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());

            }
        }
    }

}
