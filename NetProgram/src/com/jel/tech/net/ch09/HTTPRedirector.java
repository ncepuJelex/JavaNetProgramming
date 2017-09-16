package com.jel.tech.net.ch09;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Redirector，见名知意！
 * 另外在浏览器中输入http://localhost:8081,真的会转到启动服务时指定的新地址哎！
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class HTTPRedirector {

	private static final Logger log = Logger.getLogger("HTTPRedirector");
	private static final int PORT = 8081;
	//新地址
	private final String newSite;

	public HTTPRedirector(String newSite) {
		this.newSite = newSite;
	}

	public void start() {
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket s = server.accept();
					new RedirectThread(s).start();
				} catch (IOException e) {
					log.warning("Exception accepting connection.");
				} catch(RuntimeException e) {
					log.log(Level.SEVERE, "Unexpected error", e);
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "could not start server", e);
		} catch(RuntimeException e) {
			log.log(Level.SEVERE, "could not start the server", e);
		}
	}

	private class RedirectThread extends Thread {
		Socket s;

		public RedirectThread(Socket s) {
			this.s = s;
		}

		@Override
		public void run() {
			try {
				Writer out = new OutputStreamWriter(s.getOutputStream(), "US-ASCII");
				Reader in = new InputStreamReader(new BufferedInputStream(s.getInputStream()));
				//读取client第一行
				StringBuilder sb = new StringBuilder(80);
				while(true) {
					int c = in.read();
					if(c=='\r' || c=='\n' || c==-1) break;
					sb.append((char)c);
				}
				String request = sb.toString();
				String[] pieces = request.split(" ");
				String theFile = pieces[1];
				// If this is HTTP/1.0 or later send a MIME header
				if(request.indexOf("HTTP") != -1) {
					out.write("HTTP/1.0 302 FOUND\r\n");
					Date now = new Date();
					out.write("Date: " + now + "\r\n");
					out.write("Server: Redirector 1.1\r\n");
					out.write("Location: " + newSite + theFile + "\r\n");
					out.write("Content-type: text/html\r\n\r\n");
					out.flush();
				}
				// Not all browsers support redirection so we need to
				// produce HTML that says where the document has moved to.
				out.write("<HTML><HEAD><TITLE>Document Moved</TITLE></HEAD>");
				out.write("<BODY><H1>Document Moved</H1>");
				out.write("The document " + theFile + " has moved to \r\n<A HREF=\"" +
						newSite+theFile + "\">" + newSite+theFile + "</A>\r\n" +
						"<P>please update your bookmark</P>");
				out.write("</BODY></HTML>\r\n");
				out.flush();
				log.log(Level.INFO, "Redirected " + s.getRemoteSocketAddress());
			} catch (IOException e) {
				log.log(Level.WARNING, "Error talking to " + s.getRemoteSocketAddress(), e);
			} finally {
				try {
					s.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("请输入新地址：");
		Scanner sc = new Scanner(System.in);
		String newSite = sc.next();
		sc.close();
		if(newSite.endsWith("/")) {
			newSite = newSite.substring(0, newSite.length()-1);
		}
		HTTPRedirector redirector = new HTTPRedirector(newSite);
		redirector.start();
	}
	/*
	 * running result:
	 * Jelex:~ zhenhua$ telnet localhost 8081
		Trying ::1...
		Connected to localhost.
		Escape character is '^]'.
		GET / HTTP/1.0 //输入
		响应：HTTP/1.0 302 FOUND
		Date: Sat Sep 16 14:15:10 CST 2017
		Server: Redirector 1.1
		Location: http://www.github.com
		Content-type: text/html

		<HTML><HEAD><TITLE>Document Moved</TITLE></HEAD><BODY><H1>Document Moved</H1>The document  has moved to
		<A HREF="http://www.github.com">http://www.github.com</A>
		<P>please update your bookmark</P></BODY></HTML>
		Connection closed by foreign host.
		Jelex:~ zhenhua$
	 */
}
