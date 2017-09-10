package com.jel.tech.net.ch07;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.jel.tech.net.ch05.QueryString;

/**
 * 表单post方式提交工具类
 * 这里收获了一个网站：http://www.cafeaulait.org/books/jnp4/postquery.phtml
 * 用它可以得到http请求头信息，具体信息可以参看本程序运行出的结果，copy到网页中查看
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class FormPoster {

	private final URL url;
	private final QueryString query = new QueryString();

	public FormPoster(URL url) {
		if(!url.getProtocol().toLowerCase().startsWith("http")) {
			throw new IllegalArgumentException("http URLs才有post工作方式！");
		}
		this.url = url;
	}

	public void add(String name, String value) {
		query.add(name, value);
	}

	public URL getUrl() {
		return url;
	}

	/*
	 * 重量级的来了！
	 * 发送post请求，返回input stream流
	 */
	public InputStream post() throws IOException {
		URLConnection uc = url.openConnection();
		uc.setDoOutput(true); //有了它才能写数据到server端
		try(OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), "UTF-8")) {
			out.write(query.toString());
			out.write("\r\n");
			out.flush();
			out.close(); //一定要有这行的，不然不会发送出去！
		}
		return uc.getInputStream();
	}

	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
			FormPoster poster = new FormPoster(url);
			poster.add("name", "Jelex.xu");
			poster.add("email", "fanguyinhengJelex@gmail.com");
			try(InputStream in = poster.post()) {
				Reader r = new InputStreamReader(in);
				int c;
				while((c=r.read()) != -1) {
					System.out.print((char)c);
				}
				System.out.println();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * running result:
	 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>Query Results</title>
		</head>
		<body>

		<h1>Query Results</h1>

		<p>You submitted the following name/value pairs:</p>

		<ul>
		<li>name = Jelex.xu</li>
		<li>email = fanguyinhengJelex@gmail.com
		</li>
		</ul>

		<p>
		The request included the following cookies:
		</p>

		<ul>
		</ul>

		<p>
		Here's the HTTP request header:
		</p>

		<pre>
		USER_AGENT: Java/1.8.0_101
		HOST: www.cafeaulait.org
		ACCEPT: text/html, image/gif, image/jpeg, *; q=.2, *\/*; q=.2
		X_FORWARDED_PROTO: http
		X_FORWARDED_FOR: 39.155.185.102
		CONNECTION: close
		</pre>

		<p>
		Here's the complete body of the request:
		</p>

		<pre>&name=Jelex.xu&email=fanguyinhengJelex%40gmail.com
		</pre>

		<hr />
		<p>Some links:</p>

		<a href="postquery.phtml">This Page</a>
		<a href="postquery2.phtml">Another Page</a>

		<hr />
		Last Modified July 25, 2004

		</body>
		</html>
	 */
}
