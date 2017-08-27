package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.junit.Test;

/**
 *
 * @author jelex.xu
 * @date 2017年5月30日
 */
public class OReillyByName {

	public static void main(String[] args) {

		try {
			InetAddress address = InetAddress.getByName("www.oreilly.com");
			System.out.println(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		/*
		 * running result at 龙兴园西区28号楼1单元303
		 *
		 * www.oreilly.com/184.28.14.152
		 */
	}

	@Test
	public void fun() {
		try {
			InetAddress[] addresses = InetAddress.getAllByName("www.oreilly.com");
			System.out.println(Arrays.toString(addresses));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		/*
		 * running result: [www.oreilly.com/184.28.14.152]
		 */
	}

	@Test
	public void fun2() {
		try {
			InetAddress me = InetAddress.getLocalHost();
			System.out.println(me);
			System.out.println(me.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		/*
		 * running result: Jelex.local/127.0.0.1 127.0.0.1
		 */
	}

	@Test
	public void fun3() {
		byte[] address = { 104, 23, (byte) 216, (byte) 196 };
		try {
			InetAddress lessWrong = InetAddress.getByAddress(address);
			InetAddress lessWrongWithName = InetAddress.getByAddress("lesswrong.com", address);
			System.out.println(lessWrong);
			System.out.println(lessWrongWithName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		/*
		 * running result: /104.23.216.196 lesswrong.com/104.23.216.196
		 */
	}

	@Test
	public void fun4() throws UnknownHostException {
		InetAddress ia = InetAddress.getByName("184.28.14.152");
		System.out.println(ia.getCanonicalHostName());
		/*
		 * running result: a184-28-14-152.deploy.static.akamaitechnologies.com
		 */
	}

	/*
	 * 测试ipv4还是ipv6地址
	 */
	@Test
	public void fun5() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		byte[] address = addr.getAddress();
		if (address.length == 4) {
			System.out.println("ipv4 address");
		} else if (address.length == 16) {
			System.out.println("ipv6 address");
		} else {
			System.out.println("ipvX address,where are you from?");
		}

	}

	@Test
	public void fun6() throws UnknownHostException {
		try {
			InetAddress ibiblio = InetAddress.getByName("www.163.com");
			InetAddress helios = InetAddress.getByName("open.163.com");
			if (ibiblio.equals(helios)) {
				System.out.println("same");
			} else {
				System.out.println("not the same"); //output
			}
		} catch (UnknownHostException ex) {
			System.out.println("Host lookup failed.");
		}
	}
}
