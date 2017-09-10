package com.jel.tech.net.ch07;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

/**
 * 发送 http HEAD 请求来获取一个资源是否已被修改过
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class LastModified {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("输入url:");
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			HttpURLConnection http = (HttpURLConnection) u.openConnection();
			/*
			 * //设置head请求，默认为GET请求
			 * 当然，这里使用GET也可，只是整个请求的文件内容都会
			 * 通过网络传输过来，这太不高效了！你只是想获取这个文件
			 * 最后修改的时间啊！所以使用HEAD请求，返回请求头即可。
			 */
			http.setRequestMethod("HEAD");
			System.out.println(u + " was last modified at " + new Date(http.getLastModified()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * running result:
		 *  输入：http://www.ibiblio.org/xml/
			输出：http://www.ibiblio.org/xml/ was last modified at Tue Apr 06 19:45:29 CST 2010
		 */
	}
}
