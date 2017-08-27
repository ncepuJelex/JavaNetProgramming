package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

/**
 *
 * @author jelex.xu
 * @date 2017年5月31日
 */
public class NetworkInterfaceTest {

	public static void main(String[] args) {

		try {
			NetworkInterface ni = NetworkInterface.getByName("eth0");
			// NetworkInterface ni = NetworkInterface.getByName("lo0");
			if (ni == null) {
				System.err.println("No such interface: eth0");
				return;
			}
			System.out.println(ni);
		} catch (SocketException e) {
			e.printStackTrace();
			System.err.println("Could not list sockets.");
		}
		/*
		 * running result: No such interface: eth0
		 */
	}

	@Test
	public void fun() {
		try {
			InetAddress local = InetAddress.getByName("127.0.0.1");
			NetworkInterface ni = NetworkInterface.getByInetAddress(local);
			if (ni == null) {
				System.err.println("what the fuck...");
				return;
			}
			System.out.println(ni);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		/*
		 * running result: name:lo0 (lo0)
		 */
	}

	@Test
	public void fun2() throws SocketException {
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		while (nis.hasMoreElements()) {
			NetworkInterface element = nis.nextElement();
			System.out.println(element);
		}
		/*
		 * running result:
		 *  name:utun0 (utun0)
			name:awdl0 (awdl0)
			name:en0 (en0)
			name:lo0 (lo0)
		 */
	}
}
