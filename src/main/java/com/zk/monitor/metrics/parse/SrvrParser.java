package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;

import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo srvr | nc localhost 2181 <br>
 * 
 * return like: <br>
 * Zookeeper version: 3.4.6-1569965, built on 02/20/2014 09:09 GMT <br>
 * Latency min/avg/max: 0/0/8104 <br>
 * Received: 30156249 <br>
 * Sent: 30160055 <br>
 * Connections: 4 <br>
 * Outstanding: 0 <br>
 * Zxid: 0x3d000715bfc <br>
 * Mode: leader <br>
 * Node count: 3698 <br>
 * 
 * @author zhuyuqiang
 * @date 2016年9月12日 上午11:23:36
 * @version 1.0
 */
public class SrvrParser implements Parser {
	
	private static final String LATENCY_KEY = "Latency min/avg/max".toLowerCase().replace(SPACE, SLASH);
	private static final String LATENCY_MIN = "latency/min";
	private static final String LATENCY_AVG = "latency/avg";
	private static final String LATENCY_MAX = "latency/max";

	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		for (String line : reply.split(NEW_LINE)) {
			String[] kv = line.trim().split(COLON);
			retMap.put(kv[0].toLowerCase().replace(SPACE, SLASH), kv[1].trim());
		}
		
		String[] latencyAll = retMap.get(LATENCY_KEY).split(SLASH);
		retMap.put(LATENCY_MIN, latencyAll[0]);
		retMap.put(LATENCY_AVG, latencyAll[1]);
		retMap.put(LATENCY_MAX, latencyAll[2]);
		retMap.remove(LATENCY_KEY);
		
		return retMap;
	}

}
