package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * 它增加了容错机制，举个例子，如果你访问的页面不存在，server返回一个导航搜索页面，
 * 告诉你要访问的页面可能在哪里
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class SourceView4 {

	public static void main(String[] args) {

		System.out.println("输入url:");
		Scanner sc = new Scanner(System.in);
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			try(InputStream raw = conn.getInputStream()) {
				printFromStream(raw);
			} catch(IOException e) {
				//一般是放在catch块中处理error stream
				printFromStream(conn.getErrorStream());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void printFromStream(InputStream raw) throws IOException {
		try(InputStream buffer = new BufferedInputStream(raw)) {
			Reader reader = new InputStreamReader(buffer);
			int c;
			while((c=reader.read()) != -1) {
				System.out.print((char)c);
			}
		}
	}
}
