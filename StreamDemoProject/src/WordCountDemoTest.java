package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stream.WordCountDemo;

class WordCountDemoTest {

	
	private TopologyTestDriver testDriver;
	private TestInputTopic<String, String> inputTopic;
	private TestOutputTopic<String, String> outputTopic;
	

	private Serde<String> stringSerde = new Serdes.StringSerde();

	@BeforeEach
	public void setup() {
	   
	   
	    WordCountDemo demo = new WordCountDemo();
	    
	    Topology topology = demo.getTopology();

	    // setup test driver
	    Properties props = new Properties();
	    props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "test");
	    props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
	    props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    
	    testDriver = new TopologyTestDriver(topology, props);
	    
	    System.out.println("hello1");

	    // setup test topics
	    inputTopic = testDriver.createInputTopic("wordsin", stringSerde.serializer(), stringSerde.serializer());
	    outputTopic = testDriver.createOutputTopic("wordsout", stringSerde.deserializer(), stringSerde.deserializer());
	    
	    
	    System.out.println("hello2");
	    
	}

	@AfterEach
	public void tearDown() {
		System.out.println("hello4");
	    testDriver.close();
	}

	@Test
	public void testA() {
	    inputTopic.pipeInput("a", "Hello World");
		
	    List keyvaluelist = outputTopic.readKeyValuesToList();
	    
	    
	   System.out.println( keyvaluelist.get(0));
	    
	    
	}

	
	@Test
	public void testB() {
	    inputTopic.pipeInput("a", "Hello World");
		//System.out.println("hello3");
	    
	    //assertThat(, equals(2));
		assertEquals(2, outputTopic.readKeyValuesToList().size());
		
		
	  //  System.out.println(outputTopic.readKeyValuesToList().size());
	    //assertThat(outputTopic.isEmpty(), is(true));
	}
	
}
