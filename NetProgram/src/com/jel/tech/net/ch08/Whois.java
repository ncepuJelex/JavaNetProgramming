package com.jel.tech.net.ch08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Whois 网站查询，它结合GUI界面一起搞起来！
 * 不过国内好像访问不上啊……
 * @author jelex.xu
 * @date 2017年9月13日
 */
public class Whois {

	public static final int DEFAULT_PORT = 43;
	public static final String DEFAULT_HOST = "http://whois.internic.net/";

	private int port = DEFAULT_PORT;
	private InetAddress host;

	public Whois(InetAddress host, int port) {
		this.port = port;
		this.host = host;
	}

	public Whois(InetAddress host) {
		this(host, DEFAULT_PORT);
	}

	public Whois(String hostname, int port) throws UnknownHostException {
		this(InetAddress.getByName(hostname), port);
	}

	public Whois(String hostname) throws UnknownHostException {
		this(InetAddress.getByName(hostname), DEFAULT_PORT);
	}
	public Whois() throws UnknownHostException {
		this(DEFAULT_HOST, DEFAULT_PORT);
	}

	//Items to search
	public enum SearchFor {

		ANY("Any"), NETWORK("Network"), PERSON("Person"),HOST("Host"),
		DOMAIN("Domain"), ORGANIZATION("Organization"), GROUP("Group"),
		GATEWAY("Gateway"), ASN("ASN");

		private String label;
		private SearchFor(String label) {
			this.label = label;
		}
	}

	// Categories to search in
	public enum SearchIn {

		ALL(""), NAME("Name"), MAILBOX("Mailbox"), HANDLE("!");

		private String label;

		private SearchIn(String label) {
			this.label = label;
		}
	}

	public String lookUpNames(String target, SearchFor category, SearchIn group, boolean exactMatch) {

		String suffix = "";
		if(!exactMatch) suffix = ".";

		String prefix = category.label + " " + group.label;
		String query = prefix + target + suffix;

		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress(host, port);
		try {
			socket.connect(address);
			Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));
			out.write(query + "\r\n");
			out.flush();

			StringBuilder sb = new StringBuilder();
			String line;
			while((line = in.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
	//==============
	public InetAddress getHost() {
		return host;
	}
	public void setHost(String host) throws UnknownHostException {
		this.host = InetAddress.getByName(host);
	}
}
