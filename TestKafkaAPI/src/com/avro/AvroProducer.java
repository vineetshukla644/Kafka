package com.avro;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class AvroProducer {

	public static void main(String[] args) {
		
		
		
		final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ubuntu-master-vm:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://ubuntu-master-vm:8081");
           
       // Payment payment = Payment.newBuilder().setId("vineet").setAmount(120).build();
        
         Payment payment = new Payment("hll",4545.00);
        
		
		  try (KafkaProducer<String, Payment> producer = new KafkaProducer<String,Payment>(props)) {
		  
		  for (long i = 0; i < 10; i++) 
		  { final String orderId = "id" +Long.toString(i);
		  
		  ProducerRecord<String, Payment> record = new ProducerRecord<String, Payment>("payments", payment.getId().toString(), payment); producer.send(record); Thread.sleep(1000L); }
		  
		  producer.flush();
		  System.out.printf("Successfully produced 10 messages to a topic called %s%n","transactions");
		  
		  } catch (final SerializationException e) { e.printStackTrace(); } catch
		  (final InterruptedException e) { e.printStackTrace(); }
		 


	}

}
