package com.jel.tech.net.ch05;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;
/**
 * 龙兴园303室
 * @author jelex.xu
 * @date 2017年6月4日
 */
public class ContentGetter {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个URL字符串");
		String urlStr = sc.next();

		sc.close();

		try {
			URL u = new URL(urlStr);
			Object o = u.getContent();
			System.out.println(o.getClass().getName());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * running result:
		 * https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=c%E7%BD%97%E5%9B%BE%E7%89%87&hs=2&pn=1&spn=0&di=125900391320&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=2948603572%2C2452508190&os=1194148301%2C3438059885&simid=3381097217%2C125506595&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=c%E7%BD%97%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201602%2F02%2F20160202133402_QPLRe.thumb.700_0.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B17tpwg2_z%26e3Bv54AzdH3Fks52AzdH3F%3Ft1%3Dcd9ml8cll&gsm=0
		 sun.net.www.protocol.http.HttpURLConnection$HttpInputStream
		 */
	}

	@Test
	public void fun() throws MalformedURLException {
		URL u = new URL("https://xkcd.com/727/");
		System.out.println(u.getProtocol());
		System.out.println(u.getHost());
		System.out.println(u.getPort());
		System.out.println(u.getAuthority());
		System.out.println(u.getFile());
		System.out.println(u.getPath());
		System.out.println(u.getRef());
		/*
		 * running result:
		 	https
			xkcd.com
			-1
			xkcd.com
			/727/
			/727/
			null
		 */
	}
	@Test
	public void fun2() throws MalformedURLException {
		URL u= new URL( "http://www.ibiblio.org/javafaq/javafaq.html#xtocid1902914");
	    System.out.println("The fragment ID of " + u + " is " + u.getRef());
	    /*
	     * running result:
	     * The fragment ID of http://www.ibiblio.org/javafaq/javafaq.html#xtocid1902914 is xtocid1902914
	     */
	}
}
