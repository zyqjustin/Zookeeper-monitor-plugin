package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;

import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo conf | nc localhost 2181 <br>
 * 
 * return like: <br>
 *     clientPort=2181 <br>
 *     dataDir=/home/uaq/local/zookeeper/data/version-2 <br>
 *     dataLogDir=/home/uaq/local/zookeeper/data/version-2 <br>
 *     tickTime=2000 <br>
 *     maxClientCnxns=60 <br>
 *     minSessionTimeout=4000 <br>
 *     maxSessionTimeout=40000 <br>
 *     serverId=4 <br>
 *     initLimit=10 <br>
 *     syncLimit=5 <br>
 *     electionAlg=3 <br>
 *     electionPort=3888 <br>
 *     quorumPort=2888 <br>
 *     peerType=0 <br>
 * 
 * @author justwin
 * @date 2016年9月9日 下午5:54:31
 * @version 1.0
 */
public class ConfParser implements Parser {
	
	private static final int LENGTH = 2;
	private static final String METRIC_START = "zk/conf/";

	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		for (String line : reply.split(NEW_LINE)) {
			String[] kv = line.split(EQUALS);
			if (LENGTH == kv.length) {
				String key = METRIC_START + kv[0].trim();
				retMap.put(key, kv[1]);
			}
		}
		
		return retMap;
	}

}
