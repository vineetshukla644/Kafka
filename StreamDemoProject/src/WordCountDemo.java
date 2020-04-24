import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

public class WordCountDemo {

	public static void main(String[] args) {
		
		
		
		
		Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe4");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "http://192.168.99.100:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
 
        final StreamsBuilder builder = new StreamsBuilder();
 
        
        KStream<String, String> source = builder.stream("wordsin");
        
        KStream<String, String> words=   source.flatMapValues(value -> Arrays.asList(value.split(" "))) ;
        
        KGroupedStream<String, String> groupedStream = words.groupBy(
        		
        	    (key, value) -> value ,Grouped.with( Serdes.String(), Serdes.String()) 
        	  );
        
       groupedStream.count().toStream().foreach( (key,value) -> System.out.println(key + " " + value)      );
       
       
       
        final Topology topology = builder.build();
        
        System.out.println(topology.describe()); 
      
        final KafkaStreams streams = new KafkaStreams(topology, props);
        
        final CountDownLatch latch = new CountDownLatch(1);
 
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
 
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
        
        

	}

}
