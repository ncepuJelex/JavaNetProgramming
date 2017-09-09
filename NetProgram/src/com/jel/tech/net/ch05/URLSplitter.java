package com.jel.tech.net.ch05;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
/**
 * 分解URL，得到其中各个部分
 * @author jelex.xu
 * @date 2017年9月6日
 */
public class URLSplitter {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入url，以逗号分隔：");
		String urls = sc.next();
		sc.close();
		String[] urlArr = urls.split(",");
		for(String url : urlArr) {
			try {
				URL u = new URL(url);
				System.out.println("The URL is: " + u);
				System.out.println("The scheme is: " + u.getProtocol());
				System.out.println("The user info is: " + u.getUserInfo());

				String host = u.getHost();
				if(host != null) {
					int atSign = host.indexOf('@');
					if(atSign != -1) {
						host = host.substring(atSign+1);
					}
					System.out.println("The host is: " + host);
				}
				else {
					System.out.println("The host is null.");
				}

				System.out.println("The port is :" + u.getPort());
				System.out.println("The path is: " + u.getPath());
				System.out.println("The ref is: " + u.getRef());
				System.out.println("The query string is: " + u.getQuery());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * input:ftp://mp3:mp3@138.247.121.61:21000/c%3a/,http://www.oreilly.com,http://www.ibiblio.org/nywc/compositions.phtml?category=Piano,http://admin@www.blackstar.com:8080/
		output:	The URL is: ftp://mp3:mp3@138.247.121.61:21000/c%3a/
			The scheme is: ftp
			The user info is: mp3:mp3
			The host is: 138.247.121.61
			The port is :21000
			The path is: /c%3a/
			The ref is: null
			The query string is: null
			The URL is: http://www.oreilly.com
			The scheme is: http
			The user info is: null
			The host is: www.oreilly.com
			The port is :-1
			The path is:
			The ref is: null
			The query string is: null
			The URL is: http://www.ibiblio.org/nywc/compositions.phtml?category=Piano
			The scheme is: http
			The user info is: null
			The host is: www.ibiblio.org
			The port is :-1
			The path is: /nywc/compositions.phtml
			The ref is: null
			The query string is: category=Piano
			The URL is: http://admin@www.blackstar.com:8080/
			The scheme is: http
			The user info is: admin
			The host is: www.blackstar.com
			The port is :8080
			The path is: /
			The ref is: null
			The query string is: null
		 */
	}
}
