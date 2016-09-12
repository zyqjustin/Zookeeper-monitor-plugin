package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo cons | nc localhost 2181 <br>
 * 
 * return like: <br>
 *  /10.10.100.30:46222[1](queued=0,recved=241191,sent=244181,sid=0x2565e1b25fc0002,lop=PING,est=1470467230995,to=40000,lcxid=0xd66,lzxid=0x3d000715c00,lresp=1473648407939,llat=0,minlat=0,avglat=0,maxlat=4329) <br>
 *  /10.10.100.30:59597[1](queued=0,recved=802161,sent=802161,sid=0x454b7f4abf60e7c,lop=PING,est=1470967531454,to=20000,lcxid=0x61eb4,lzxid=0x3d000715c03,lresp=1473648412450,llat=0,minlat=0,avglat=0,maxlat=1863) <br>
 *  /10.10.100.13:49646[0](queued=0,recved=1,sent=0) <br>
 *  /10.10.100.13:48732[1](queued=0,recved=790976,sent=790976,sid=0x35678b9fbf00000,lop=PING,est=1471004956402,to=20000,lcxid=0x61eb5,lzxid=0x3d000715c02,lresp=1473648411942,llat=0,minlat=0,avglat=0,maxlat=1881) <br>
 * 
 * @author zhuyuqiang
 * @date 2016年9月12日 下午1:40:42
 * @version 1.0
 */
public class ConsParser implements Parser {
	
	private final static Pattern _conRegex = Pattern.compile("^/(.+?)\\[(\\d{1})\\]\\((.+?)\\)");
	
	private final static String COMPONENTS_START = "zk/cons/";
	private final static int HOST_INDEX   = 1;
	private final static int STATUS_INDEX = 2;
	private final static int CONTENT_INDEX = 3;

	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		for (String line : reply.split(NEW_LINE)) {
			Matcher matcher = _conRegex.matcher(line.trim());
			if (matcher.matches()) {
				String host = matcher.group(HOST_INDEX);
				String status = matcher.group(STATUS_INDEX);
				String content = matcher.group(CONTENT_INDEX);
				
				retMap.put(COMPONENTS_START + host + "/status", status);
				for (String quota : content.split(COMMA)) {
					String[] kvs = quota.trim().split(EQUALS);
					retMap.put(COMPONENTS_START + host + SLASH + kvs[0], kvs[1]);
				}
			};
		}
		
		return retMap;
	}

	public static void main(String[] args) throws Exception {
		ConsParser parser = new ConsParser();
		
		String reply = " /10.10.100.30:46222[1](queued=0,recved=241191,sent=244181,sid=0x2565e1b25fc0002,lop=PING,est=1470467230995,to=40000,lcxid=0xd66,lzxid=0x3d000715c00,lresp=1473648407939,llat=0,minlat=0,avglat=0,maxlat=4329) \n /10.10.100.30:59597[1](queued=0,recved=802161,sent=802161,sid=0x454b7f4abf60e7c,lop=PING,est=1470967531454,to=20000,lcxid=0x61eb4,lzxid=0x3d000715c03,lresp=1473648412450,llat=0,minlat=0,avglat=0,maxlat=1863) \n /10.10.100.13:49646[0](queued=0,recved=1,sent=0) \n /10.10.100.13:48732[1](queued=0,recved=790976,sent=790976,sid=0x35678b9fbf00000,lop=PING,est=1471004956402,to=20000,lcxid=0x61eb5,lzxid=0x3d000715c02,lresp=1473648411942,llat=0,minlat=0,avglat=0,maxlat=1881) ";
		Map<String, String> map = parser.parse(reply);
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("key: " + entry.getKey() + " ===> value: " + entry.getValue());
		}
	}
}
