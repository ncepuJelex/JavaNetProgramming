package com.jel.tech.net.ch07;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * 获取URLConnection 类中的 url属性
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class URLPrinter {

	public static void main(String[] args) {

		try {
			URL u = new URL("http://www.oreilly.com/");
			URLConnection uc = u.openConnection();
			System.out.println(uc.getURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * running result:http://www.oreilly.com/
	 */
}
