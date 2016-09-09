package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;

import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo mntr | nc localhost 2181 <br>
 * 
 * return like: <br>
 *     zk_version	3.4.6-1569965, built on 02/20/2014 09:09 GMT <br>
 *     zk_avg_latency	0 <br>
 *     zk_max_latency	8104 <br>
 *     zk_min_latency	0 <br>
 *     zk_packets_received	29995759 <br>
 *     zk_packets_sent	29999526 <br>
 *     zk_num_alive_connections	4 <br>
 *     zk_outstanding_requests	0 <br>
 *     zk_server_state	leader <br>
 *     zk_znode_count	3698 <br>
 *     zk_watch_count	243 <br>
 *     zk_ephemerals_count	15 <br>
 *     zk_approximate_data_size	958775 <br>
 *     zk_open_file_descriptor_count	39 <br>
 *     zk_max_file_descriptor_count	65536 <br>
 *     zk_followers	4 <br>
 *     zk_synced_followers 4 <br>
 *     zk_pending_syncs	0 <br>
 * 
 * @author justwin
 * @date 2016年9月9日 下午4:37:15
 * @version 1.0
 */
public class MntrParser implements Parser {

	private static final int LENGTH = 2;
	
	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		for (String line : reply.split(NEW_LINE)) {
			if (line.isEmpty()) {
				continue;
			}
			String[] kv = line.split(TAB);
			if (LENGTH == kv.length) {
				retMap.put(kv[0].replace(UNDERSCORE, SLASH), kv[1]);
			}
		}
		
		return retMap;
	}

}
