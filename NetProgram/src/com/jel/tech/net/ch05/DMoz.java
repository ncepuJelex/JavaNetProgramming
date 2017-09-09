package com.jel.tech.net.ch05;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
/**
 * 显示读取一个网页内容是多少easy!
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class DMoz {

	public static void main(String[] args) {

		System.out.println("输入要搜索的内容");
		Scanner sc = new Scanner(System.in);
		String target = sc.next();
		sc.close();

		QueryString query = new QueryString();
		query.add("q", target);
		try {
			URL u = new URL("https://search.jd.com/Search?" + query);
			try(InputStream in = new BufferedInputStream(u.openStream())) {
				InputStreamReader html = new InputStreamReader(in);
				int c;
				while((c = html.read()) != -1) {
					System.out.print((char)c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
