package com.jel.tech.net.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

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

		for(String ip : ips) {
			if(isSpam(ip)) {
				System.out.println(ip + " is a known spammer.");
			} else {
				System.out.println(ip + " appears legitimate.");
			}
		}
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
			for(byte b : quard) {
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
