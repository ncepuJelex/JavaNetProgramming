package com.jel.tech.net.ch05;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;

/**
 * 龙兴园28号楼一单元303
 *测试protocol是否可用，共15种，8种标准协议，
 *3种可订制协议，4种java内部使用的（没有document出来）
 * @author jelex.xu
 * @date 2017年6月4日
 */
public class ProtocolTest {

	public static void main(String[] args) {

		// hypertext transfer protocol
		testProtocol("http://www.adc.org");
		// secure http
		testProtocol("https://www.amazon.com/exec/obidos/order2/");
		// file
		// transfer
		// protocol
		testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq/");
		// Simple
		// Mail
		testProtocol("mailto:elharo@ibiblio.org"); // telnet
		testProtocol("telnet://dibner.poly.edu/"); // local file access
		testProtocol("file:///etc/passwd"); // gopher
		testProtocol("gopher://gopher.anc.org.za/");
		// Lightweight Directory Access Protocol
		testProtocol("ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");
		// JAR
		testProtocol(
				"jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!" + "/com/macfaq/io/StreamCopier.class");
		// NFS,
		// Network
		// File
		// System
		testProtocol("nfs://utopia.poly.edu/usr/tmp/");
		// a custom protocol for
		// JDBC
		testProtocol("jdbc:mysql://luna.ibiblio.org:3306/NEWS");
		// rmi, a custom protocol for remote method invocation
		testProtocol("rmi://ibiblio.org/RenderEngine");
		// custom protocols for HotJava
		testProtocol("doc:/UsersGuide/release.html");
		testProtocol("netdoc:/UsersGuide/release.html");
		testProtocol("systemresource://www.adc.org/+/index.html");
		testProtocol("verbatim:http://www.adc.org/");

		/*
		 * running result: http is supported.
		 *  https is supported.
		 *   ftp is supported.
		 *   mailto is supported.
		 *   telnet is not supported.
		 *   file is supported.
		 *   gopher is not supported.
		 *   ldap is not supported.
		 *   jar is supported.
		 *   nfs is not supported.
		 *   jdbc is not supported.
		 *   rmi is not supported.
		 *   doc is not supported.
		 *   netdoc is supported.
		 *   systemresource is not supported.
		 *   verbatim is not supported.
		 */
	}

	private static void testProtocol(String string) {
		try {
			URL url = new URL(string);
			System.out.println(url.getProtocol() + " is supported.");
		} catch (MalformedURLException e) {
			System.out.println(string.substring(0, string.indexOf(':')) + " is not supported.");
		}
	}

	@Test
	public void fun() {
		try {
			new URL("http", "open.163.com/", "/special/cuvocw/dixiashui.html");
		} catch (MalformedURLException e) {
			throw new RuntimeException("show not happen,all VMs recoginize http.");
		}

		try {
			new URL("http", "fourier.dur.ac.uk", 8000, "/~dma3mjh/jsci/");
		} catch (MalformedURLException ex) {
			throw new RuntimeException("shouldn't happen; all VMs recognize http with port 8000.");
		}
	}

	@Test
	public void fun2() {
		try {
			URL u1 = new URL("http://open.163.com/special/cuvocw/dixiashui.html");
			URL u2 = new URL(u1, "M9AV3V7QN_M9BEIKLFF.html");
			System.out.println(u2);
		} catch (MalformedURLException e) {
			System.err.println(e);
		}
		/*
		 * running result:
		 * http://open.163.com/special/cuvocw/M9AV3V7QN_M9BEIKLFF.html
		 */
	}

	@Test
	public void fun3() {
		try {
			URL u = new URL("http://www.imooc.com/video/369");
			InputStream stream = u.openStream();
			int c, i = 0, available = stream.available();
			byte[] bytes = new byte[available];
			while ((c = stream.read()) != -1 && i < available) {
				// System.out.println(c);
				bytes[i++] = (byte) c;
			}
			String s = new String(bytes);
			System.out.println(s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * running result: <!DOCTYPE html> <html> <head> <meta charset="utf-8">
		 * <meta property="qc:admins" content="77103107776553571676375" /> <meta
		 * property="wb:webmaster" content="c4f857219bfae3cb" /> <meta
		 * http-equiv="Access-Control-Allow-Origin" content="*" /> <meta
		 * name="Keywords" content="" /> <meta name="Description" content=
		 * "超酷的互联网、IT技术免费学习平台，创新的网络一站式学习、实践体验；服务及时贴心，内容专业、有趣易学。专注服务互联网工程师快速成为技术高手！"
		 * />
		 *
		 * <title>JS实现购物车功能介绍-慕课网</ti
		 *
		 */
	}

	@Test
	public void fun4() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个URL字符串");
		String urlStr = sc.next();
		sc.close();
		InputStream in = null;
		try {
			URL u = new URL(urlStr);
			in = u.openStream();
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(u.openStream()));
			Reader reader = new InputStreamReader(new BufferedInputStream(in));
			int c, i = 0;
			while ((c = reader.read()) != -1) {
				System.out.print((char) c);
				if (i++ % 20 == 0) {
					System.out.println();
				}
			}
		} catch (MalformedURLException e) {
			System.out.println("Not a parsable URL.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		/*
		 * running result: < !DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
		 * <html><hea d> <title>301 Moved Permanently</title>
		 *
		 * </head><body> <h1>Mo ved Permanently</h1>
		 *
		 * <p>The document has moved <a href="http s://www.oreilly.com/
		 * ">here</a>.</p> </bo dy></html>
		 */
	}
}
