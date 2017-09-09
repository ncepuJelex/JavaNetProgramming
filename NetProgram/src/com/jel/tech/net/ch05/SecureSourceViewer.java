package com.jel.tech.net.ch05;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 查看受密码保护的资源，演示http authenticator功能
 *
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class SecureSourceViewer {

	public static void main(String[] args) {
		/*
		 * Sets the authenticator that will be used by the networking code when
		 * a proxy or an HTTP server asks for authentication
		 */
		Authenticator.setDefault(new DialogAuthenticator());

		/*System.out.println("输入要搜索的内容");
		Scanner sc = new Scanner(System.in);
		String target = sc.next();
		sc.close();

		QueryString query = new QueryString();
		query.add("q", target);*/
		try {
			URL u = new URL("https://github.com/"); //哎，我没去个需要认证的家伙
			try(InputStream in = new BufferedInputStream(u.openStream())) {
				InputStreamReader html = new InputStreamReader(in);
				int c;
				while((c = html.read()) != -1) {
					System.out.print((char)c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
