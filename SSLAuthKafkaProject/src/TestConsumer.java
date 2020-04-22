import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class TestConsumer {

	public static void main(String[] args)
	
	{
		
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "ubuntu-master-vm:9093");
			
			
		 props.put("security.protocol", "SSL");
		 props.put("ssl.truststore.location", "client.truststore.jks");
		 props.put("ssl.truststore.password", "123456");
		 props.put("ssl.keystore.location", "client.keystore.jks");
		 props.put("ssl.keystore.password", "123456");
		 props.put("ssl.key.password", "12345678");	
		 
		 
		 
	     props.setProperty("group.id", "testgroup");
	     props.setProperty("enable.auto.commit", "true");
	     props.setProperty("auto.commit.interval.ms", "1000");
	     props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("sample"));
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	     }
		
		
	}
	
}
