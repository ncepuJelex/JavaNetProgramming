package com.jel.tech.net.ch07;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;
/**
 * 获取http request header 信息
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class HeaderViewer {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("输入url:");
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			URLConnection uc = u.openConnection();

			System.out.println("Content-Type:" + uc.getContentType());

			if(uc.getContentEncoding() != null) {
				System.out.println("Content-Encoding:" + uc.getContentEncoding());
			}
			if(uc.getDate() != 0) {
				System.out.println("Date:" + new Date(uc.getDate()));
			}
			if(uc.getExpiration() != 0) {
				System.out.println("Expiration date:" + new Date(uc.getExpiration()));
			}
			if(uc.getLastModified() != 0) {
				System.out.println("Last modified:" + uc.getLastModified());
			}
			if(uc.getContentLength() != -1) {
				System.out.println("Content-length:" + uc.getContentLength());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	/*
	 * 输入：https://www.jd.com/?cu=true&utm_source=www.hao123.com&utm_medium=tuiguang&utm_campaign=t_1000003625_hao123mz&utm_term=9ab9cd6ade094cf38093f35ea53c6106
	 * 输出：
	 *  Content-Type:text/html; charset=utf-8
		Date:Sun Sep 10 13:15:26 CST 2017
		Expiration date:Sun Sep 10 13:15:24 CST 2017
		Content-length:124154
	 */
}
