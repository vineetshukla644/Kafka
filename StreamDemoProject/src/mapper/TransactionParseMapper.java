package mapper;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

public class TransactionParseMapper implements KeyValueMapper<String,String, KeyValue<String,Long>>{

	@Override
	public KeyValue<String, Long> apply(String key, String value) {
		// TODO Auto-generated method stub
		
		String a[] = value.split(":");
		
		//System.out.println(a[0] + " " + a[1]);
		
		return new KeyValue<String, Long>(a[0],Long.parseLong(a[1]));
		
		
		
	}

}
