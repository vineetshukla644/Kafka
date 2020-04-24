package producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

public class TransactionProducer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		final Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props)) {

			for (long i = 0; i < 10; i++) {

				
				ProducerRecord<String, String> record = new ProducerRecord<String, String>("transaction","trump","vineet:100");
				ProducerRecord<String, String> record1 = new ProducerRecord<String, String>("transaction","boris","ravi:90");
				
				

				Future<RecordMetadata> future = producer.send(record);
				RecordMetadata metadata = future.get();
				System.out.println(metadata.offset() + " " + metadata.partition() + " " + metadata.topic());

				future = producer.send(record1);
				metadata = future.get();
				System.out.println(metadata.offset() + " " + metadata.partition() + " " + metadata.topic());

			}
			

			producer.flush();
			
			
			System.out.printf("Successfully produced 10 messages to a topic called %s%n", "transaction");

		} catch (final SerializationException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

	}

}
