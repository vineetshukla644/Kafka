import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TestProducer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
		Properties config = new Properties();
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("bootstrap.servers", "ubuntu-master-vm:9092");
		config.put("acks", "all");
		config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		
		KafkaProducer<String, String> producer= new KafkaProducer<String, String>(config);
		
		final ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", "v", "dewdwed");
		Future<RecordMetadata> future = producer.send(record);
		
		
		RecordMetadata metadata = future.get();
		
	}

}
