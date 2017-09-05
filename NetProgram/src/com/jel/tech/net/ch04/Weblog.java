package com.jel.tech.net.ch04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * reads a web server logfile and prints each line with IP addresses converted
 * to hostnames
 *
 * @author jelex.xu
 * @date 2017年9月5日
 */
public class Weblog {

	public static void main(String[] args) {

		System.out.println("please input the fileName.");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String fileName = fileNames.split(",")[0];

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
			String entry;
			while ((entry = br.readLine()) != null) {
				int index = entry.indexOf(' ');
				String ip = entry.substring(0, index);
				String rest = entry.substring(index);
				// Ask DNS for the hostname
				InetAddress address = InetAddress.getByName(ip);
				System.out.println(address.getHostName() + rest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * 输入：weblog.log，不得不说，耗时还是挺长的！7s
			输出：205.160.186.76 unknown - [17/Jun/2013:22:53:58 -0500] "GET /bgs/greenbg.gif HTTP 1.0" 200 50
			192.168.1.6 unknown - [17/Jun/2013:22:53:58 -0500] "GET /bgs/greenbg.gif HTTP 1.0" 200 50
			localhost unknown - [17/Jun/2013:22:53:58 -0500] "GET /bgs/greenbg.gif HTTP 1.0" 200 50
			111.132.83.120 unknown - [17/Jun/2013:22:53:58 -0500] "GET /bgs/greenbg.gif HTTP 1.0" 200 50
		 */
	}
}
