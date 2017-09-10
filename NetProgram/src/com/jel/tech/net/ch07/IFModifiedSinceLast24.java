package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

/**
 * prints the default value of ifModifiedSince, sets its value to 24 hours ago,
 * and prints the new value. It then downloads and displays the document—but
 * only if it’s been modified in the last 24 hours
 *
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class IFModifiedSinceLast24 {

	public static void main(String[] args) {

		Date today = new Date();
		long millisecondsPerDay = 24 * 60 * 60 * 1000;

		Scanner sc = new Scanner(System.in);
		System.out.println("输入url:");
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			System.out.println("Original if modified since:" + new Date(uc.getIfModifiedSince()));
			uc.setIfModifiedSince(new Date(today.getTime() - millisecondsPerDay).getTime());
			System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
			try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
				Reader r = new InputStreamReader(in);
				int c;
				while ((c = r.read()) != -1) {
					System.out.print((char) c);
				}
				System.out.println();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
