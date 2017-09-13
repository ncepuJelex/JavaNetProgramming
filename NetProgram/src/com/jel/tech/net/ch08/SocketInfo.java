package com.jel.tech.net.ch08;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
/**
 * 获取本地和远程主机的地址和端口信息
 * @author jelex.xu
 * @date 2017年9月12日
 */
public class SocketInfo {

	public static void main(String[] args) {

		Socket s = new Socket();
		System.out.println("输入主机：");
		Scanner sc = new Scanner(System.in);
		String host = sc.next();
		sc.close();
		SocketAddress addr = new InetSocketAddress(host, 80);
		try {
			s.connect(addr);
			System.out.println("Connected to " + s.getInetAddress() + ", on port " +
					s.getPort() + " from port " + s.getLocalPort() + " of " + s.getLocalAddress());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
			}
		}
		/*
		 * running result:
		 输入： www.oreilly.com
		 输出：Connected to www.oreilly.com/184.31.52.74, on port 80 from port 50031 of /192.168.1.6
		 */
	}
}
