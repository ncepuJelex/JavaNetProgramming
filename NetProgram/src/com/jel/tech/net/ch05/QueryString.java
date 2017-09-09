package com.jel.tech.net.ch05;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 使用URLEncoder处理URI中的query 部分，
 * 可以作为一个工具类使用，线程安全
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class QueryString {

	private StringBuilder query = new StringBuilder();

	public QueryString() {
	}

	public synchronized void add(String name, String value) {
		query.append('&');
		encode(name, value);
	}

	private synchronized void encode(String name, String value) {
		try {
			query.append(URLEncoder.encode(name, "UTF-8"));
			query.append('=');
			query.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized String getQuery() {
		return query.toString();
	}
	/*
	 * 重写toString方法(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getQuery();
	}

	public static void main(String[] args) {
		QueryString qs = new QueryString();
		qs.add("hl", "en");
		qs.add("as_q", "Java");
		qs.add("as_epq", "I/O");
		String url = "http://www.google.com/search?" + qs; //看到重写toString的好处了吧！
		System.out.println(url); //http://www.google.com/search?&hl=en&as_q=Java&as_epq=I%2FO

		/*String output = null;
		try {
			output = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    System.out.println(output); //http://www.google.com/search?&hl=en&as_q=Java&as_epq=I/O
*/	}
}
