package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * To find out if a certain IP address is a known spammer, reverse the bytes of
 * the address, add the domain of the blackhole service, and look it up. If the
 * address is found, it’s a spammer. If it isn’t, it’s not. If the DNS query
 * succeeds (and, more specifically, if it returns the address 127.0.0.2), then
 * the host is known to be a spammer. If the lookup fails—that is, it throws an
 * UnknownHostException—it isn’t
 *
 * @author jelex.xu
 * @date 2017年9月5日
 */
public class SpamCheck {

	public static final String BLACK_HOLE = "sbl.spamhaus.org";

	public static void main(String[] args) {

		System.out.println("请输入ip地址，多个地址之间以,隔开");
		Scanner sc = new Scanner(System.in);
		String ipAddrs = sc.next();
		sc.close();

		if (ipAddrs.trim().isEmpty())
			return;
		String[] ips = ipAddrs.split(",");

		for (String ip : ips) {
			if (isSpam(ip)) {
				System.out.println(ip + " is a known spammer.");
			} else {
				System.out.println(ip + " appears legitimate.");
			}
		}
		/*2017-9-5 21:36
		 * 北京昌平龙兴园西区28号楼303室
		 * 输入：
		 * 207.34.56.23,125.12.32.4,130.130.130.130
		 * 输出：
			207.34.56.23 is a known spammer.
			125.12.32.4 is a known spammer.
			130.130.130.130 is a known spammer.
		 */
	}

	/*
	 * 判断是否是攻击者
	 * @param ip
	 * @return
	 */
	private static boolean isSpam(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip);
			byte[] quard = address.getAddress();
			String query = BLACK_HOLE;
			for (byte b : quard) {
				int unsignedB = b < 0 ? b + 256 : b;
				query = unsignedB + "." + query;
			}
			InetAddress.getByName(query);
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}
}
