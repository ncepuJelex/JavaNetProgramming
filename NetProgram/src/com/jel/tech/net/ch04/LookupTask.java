package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.util.concurrent.Callable;
/**
 * 通过Callable任务来解析ip地址成hostname,
 * 线程池又要搞起了！它和PoolWeblog.java结合使用
 * @author jelex.xu
 * @date 2017年9月5日
 */
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
