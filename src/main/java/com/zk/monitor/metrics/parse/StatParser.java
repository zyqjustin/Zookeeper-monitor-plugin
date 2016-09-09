package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;

/**
 * execute command: <br>
 *     echo stat | nc localhost 2181 <br>
 * 
 * return like: <br>
 *     Zookeeper version: 3.4.6-1569965, built on 02/20/2014 09:09 GMT <br>
 *     Clients: <br>
 *      /10.10.100.30:46222[1](queued=0,recved=223953,sent=226904) <br>
 *      /10.10.100.30:59597[1](queued=0,recved=733470,sent=733470) <br>
 *      /10.10.100.13:48732[1](queued=0,recved=722285,sent=722285) <br>
 *      /10.10.100.13:45624[0](queued=0,recved=1,sent=0) <br>
 *     
 *     Latency min/avg/max: 0/0/8104 <br>
 *     Received: 30001633 <br>
 *     Sent: 30005400 <br>
 *     Connections: 4 <br>
 *     Outstanding: 0 <br>
 *     Zxid: 0x3d0006eaf2f <br>
 *     Mode: leader <br>
 *     Node count: 3698 <br>
 * 
 * @author justwin
 * @date 2016年9月9日 下午7:02:39
 * @version 1.0
 */
public class StatParser implements Parser {

	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		// TODO
		
		return retMap;
	}

}
