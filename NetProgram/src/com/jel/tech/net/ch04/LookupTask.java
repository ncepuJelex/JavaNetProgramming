package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.util.concurrent.Callable;

public class LookupTask implements Callable<String>{

	private String line;

	public LookupTask(String line) {
		this.line = line;
	}

	@Override
	public String call() throws Exception {
		try {
			int index = line.indexOf(' ');
			String addr = line.substring(0, index);
			String rest = line.substring(index);
			String hostname = InetAddress.getByName(addr).getHostName();
			return hostname + rest;
		} catch (Exception e) {
			return line;
		}
	}

}
