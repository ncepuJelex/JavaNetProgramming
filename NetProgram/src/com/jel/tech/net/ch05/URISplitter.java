package com.jel.tech.net.ch05;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class URISplitter {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入uri，以逗号分隔：");
		String uris = sc.next();
		sc.close();
		String[] uriArr = uris.split(",");
		for(String uri: uriArr) {
			try {
				URI u = new URI(uri);
				System.out.println("The URI is: " + u);
				if(u.isOpaque()) {
					System.out.println("This is a opaque uri.");
					System.out.println("The scheme is: " + u.getScheme());
					System.out.println("The scheme specific part is: " + u.getSchemeSpecificPart());
					System.out.println("The fragment ID: " + u.getFragment());
				}
				else {
					System.out.println("The is a hierarchical uri.");
					System.out.println("The scheme is: " + u.getScheme());
					try {
						u = u.parseServerAuthority();
						System.out.println("The host is: " + u.getHost());
						System.out.println("The user info is: " + u.getUserInfo());
						System.out.println("The port is: " + u.getPort());
					} catch(URISyntaxException e) {
						System.out.println("The authority is: " + u.getAuthority());
					}
					System.out.println("The path is: " + u.getPath());
					System.out.println("the query string is: " + u.getQuery());
					System.out.println("The fragment ID is: " + u.getFragment());
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
		/*
		 * running result:
		 * 请输入uri，以逗号分隔：
			tel:+1-800-9988-9938,http://www.xml.com/pub/a/2003/09/17/stax.html#id=_hbc,urn:isbn:1-565-92870-9
			The URI is: tel:+1-800-9988-9938
			This is a opaque uri.
			The scheme is: tel
			The scheme specific part is: +1-800-9988-9938
			The fragment ID: null

			The URI is: http://www.xml.com/pub/a/2003/09/17/stax.html#id=_hbc
			The is a hierarchical uri.
			The scheme is: http
			The host is: www.xml.com
			The user info is: null
			The port is: -1
			The path is: /pub/a/2003/09/17/stax.html
			the query string is: null
			The fragment ID is: id=_hbc

			The URI is: urn:isbn:1-565-92870-9
			This is a opaque uri.
			The scheme is: urn
			The scheme specific part is: isbn:1-565-92870-9
			The fragment ID: null
		 */
	}
}
