package com.zk.monitor.processors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

import com.zk.monitor.utils.Logger;

public class ZkFetchProcessor implements Fetch {
	private static final Logger _logger = Logger.getLogger(ZkFetchProcessor.class);
	
	private final String host;
	private final int port;

	public ZkFetchProcessor(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public String fetch(String command) {
		Socket socket = null;
		try {
			socket = new Socket();
			socket.setSoLinger(false, 10);
			socket.setSoTimeout(20000);
			socket.connect(new InetSocketAddress(host, port));
			IOUtils.write(command, socket.getOutputStream());
			return IOUtils.toString(socket.getInputStream());
		} catch (IOException e) {
			_logger.error("Fetch command=[{}] response failed. Zookeeper host=[{}], port=[{}].", command, host, port);
			return null;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					_logger.error("Close socket failed. Zookeeper host=[{}], port=[{}].", host, port);
				}
			}
		}
	}
	
	
}
