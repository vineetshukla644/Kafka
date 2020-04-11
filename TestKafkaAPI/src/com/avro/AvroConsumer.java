package com.avro;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class AvroConsumer {

	public static void main(String[] args) {
		
		final Properties props = new Properties();
        
		    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ubuntu-master-vm:9092");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-payments");
	        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
	        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	      
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
	        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true); 
            props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://ubuntu-master-vm:8081");

        
        try (KafkaConsumer<String, Payment> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Arrays.asList("payments"));
     
			
			 while (true) {
			     ConsumerRecords<String, Payment> records = consumer.poll(Duration.ofMillis(100));
			     for (ConsumerRecord<String, Payment> record : records)
			         System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value().amount, record.value().id);
			 }
		}
		

	}

}
