package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;

import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo envi | nc localhost 2181 <br>
 * 
 * return like: <br>
 *     Environment: <br>
 *     zookeeper.version=3.4.6-1569965, built on 02/20/2014 09:09 GMT <br>
 *     host.name=localhost <br>
 *     java.version=1.7.0_79 <br>
 *     java.vendor=Oracle Corporation <br>
 *     java.home=/home/uaq/local/jdk1.7.0_79/jre <br>
 *     java.class.path=/home/uaq/local/zookeeper/bin/../build/classes:/home/uaq/local/zookeeper/bin/../build/lib/*.jar:/home/uaq/local/zookeeper/bin/../lib/slf4j-log4j12-1.6.1.jar:/home/uaq/local/zookeeper/bin/../lib/slf4j-api-1.6.1.jar:/home/uaq/local/zookeeper/bin/../lib/netty-3.7.0.Final.jar:/home/uaq/local/zookeeper/bin/../lib/log4j-1.2.16.jar:/home/uaq/local/zookeeper/bin/../lib/jline-0.9.94.jar:/home/uaq/local/zookeeper/bin/../zookeeper-3.4.6.jar:/home/uaq/local/zookeeper/bin/../src/java/lib/*.jar:/home/uaq/local/zookeeper/bin/../conf:.:/home/uaq/local/java/lib/dt.jar:/home/uaq/local/java/lib/tools.jar <br>
 *     java.library.path=/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib <br>
 *     java.io.tmpdir=/tmp <br>
 *     java.compiler=<NA> <br>
 *     os.name=Linux <br>
 *     os.arch=amd64 <br>
 *     os.version=3.10.0-229.1.2.el7.x86_64 <br>
 *     user.name=uaq <br>
 *     user.home=/home/uaq <br>
 *     user.dir=/home/uaq/local/zookeeper/bin <br>
 *     
 * @author justwin
 * @date 2016年9月9日 下午5:35:39
 * @version 1.0
 */
public class EnviParser implements Parser {
	
	private static final int LENGTH = 2;
	private static final String UNUSED_ENV_STR = "Environment:";

	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		for (String line : reply.split(NEW_LINE)) {
			if (line.startsWith(UNUSED_ENV_STR)) {
				continue;
			}
			
			String[] kv = line.split(EQUALS);
			if (LENGTH == kv.length) {
				retMap.put(kv[0].replace(POINT, SLASH), kv[1]);
			}
		}
		
		return retMap;
	}

}
