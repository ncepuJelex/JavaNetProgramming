package com.jel.tech.net.ch08;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 从time.nist.gov这个NTP服务器上获取时间，并对获取到
 * 的时间进行处理
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class Daytime {

	public Date getDateFromNetwork() throws UnknownHostException, IOException, ParseException {
		try(Socket s = new Socket("time.nist.gov", 13)) {
			s.setSoTimeout(15000);
			InputStream in = s.getInputStream();
			StringBuilder time = new StringBuilder();
			InputStreamReader r = new InputStreamReader(in, "ASCII");//time.nist.gov使用这个编码
			int c = 0;
			while((c = r.read()) != -1) {
				time.append((char)c);
			}
			return parseDate(time.toString());
		}
	}

	static Date parseDate(String string) throws ParseException {
		String[] pieces = string.split(" ");
		String dateTime = pieces[1] + " " + pieces[2] + " UTC";
		DateFormat df = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
		return df.parse(dateTime);
	}

	public static void main(String[] args) {

		Daytime dt = new Daytime();
		try {
			Date date = dt.getDateFromNetwork();
			System.out.println(date);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*
		 * 很遗憾，这几次都被服务器拒绝了！第一遍跑出来了真是幸运！
		 * java.net.ConnectException: Connection refused
			at java.net.PlainSocketImpl.socketConnect(Native Method)
			at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
			at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
			at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
			at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
			at java.net.Socket.connect(Socket.java:589)
			at java.net.Socket.connect(Socket.java:538)
			at java.net.Socket.<init>(Socket.java:434)
			at java.net.Socket.<init>(Socket.java:211)
			at com.jel.tech.net.ch08.Daytime.getDateFromNetwork(Daytime.java:22)
			at com.jel.tech.net.ch08.Daytime.main(Daytime.java:46)
		 */
	}
}
