package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * 测试 characteristics of an IP address
 * @author jelex.xu
 * @date 2017年9月5日
 */
public class IPCharacteristics {

	public static void main(String[] args) {

		System.out.println("请输入ip地址:");
		Scanner sc = new Scanner(System.in);
		String host = sc.next();
		sc.close();
		try {
			InetAddress address = InetAddress.getByName(host);
			if(address.isAnyLocalAddress()) {
				System.out.println(address + " is a wildcard address.");
			}
			if(address.isLoopbackAddress()) {
				System.out.println(address + " is a loopback address.");
			}

			if(address.isLinkLocalAddress()) {
				System.out.println(address + " is a link-local address.");
			}
			else if(address.isSiteLocalAddress()) {
				System.out.println(address + " is a site-local address.");
			} else {
				System.out.println(address + " is a global address.");
			}

			if(address.isMulticastAddress()) {
				if(address.isMCGlobal()) {
					System.out.println(address + " is a global multicast address.");
				} else if(address.isMCOrgLocal()) {
					System.out.println(address + " is a organization with multicast address.");
				} else if(address.isMCSiteLocal()) {
					System.out.println(address + " is a site wide multicast address.");
				} else if(address.isMCLinkLocal()) {
					System.out.println(address + " is a site sub-net wide multicast address.");
				} else if(address.isMCNodeLocal()) {
					System.out.println(address + " is a site interface local multicast address.");
				} else {
					System.out.println(address + " is an unknow multicast address.");
				}
			}
			else {
				System.out.println(address + " is a unicast address.");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
