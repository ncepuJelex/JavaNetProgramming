package com.jel.tech.net.ch07;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class AllHeaders {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("输入url:");
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			for(int i=0; ; i++) {
				String header = uc.getHeaderField(i);
				if(header == null) break;
				System.out.println(uc.getHeaderFieldKey(i) + ": " + header);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	/*
	 * 输入：http://www.oreilly.com
	   输出：null: HTTP/1.1 301 Moved Permanently
		Server: Apache
		Location: https://www.oreilly.com/
		Content-Length: 232
		Content-Type: text/html; charset=iso-8859-1
		Cache-Control: max-age=12597
		Expires: Sun, 10 Sep 2017 08:54:06 GMT
		Date: Sun, 10 Sep 2017 05:24:09 GMT
		Connection: keep-alive
	 */
}
