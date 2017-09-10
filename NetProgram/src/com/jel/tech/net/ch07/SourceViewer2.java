package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 使用URLConnection来下载网页，和之前的SourceView类基本一样
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class SourceViewer2 {

	public static void main(String[] args) {

		System.out.println("请输入url:");
		Scanner sc = new Scanner(System.in);
		String url = sc.next();
		sc.close();

		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			Map<String, List<String>> properties = connection.getRequestProperties();
			for(String key : properties.keySet()) {
				System.out.println(key + "==>" + properties.get(key));
			}
			try(InputStream stream = connection.getInputStream()) {
				InputStream buffer = new BufferedInputStream(stream);
				Reader reader = new InputStreamReader(buffer);
				int c;
				while((c=reader.read()) != -1) {
					System.out.print((char)c);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 输入：https://www.jd.com/
	 * 你看看有什么结果！
	 */
}
