package com.jel.tech.net.ch08;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 去问服务器时间，直接打印出来
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class DayTimeClient {

	public static void main(String[] args) {

		Socket s = null;
		try {
			s = new Socket("time.nist.gov", 13);
			s.setSoTimeout(15000); //就怕server准许连接，但是不响应，出错也行啊！所以设置这个了
			InputStream in = s.getInputStream();
			StringBuilder time = new StringBuilder();
			InputStreamReader r = new InputStreamReader(in);
			for(int c=r.read(); c!=-1; c=r.read()) {
				time.append((char)c);
			}
			System.out.println(time);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(s != null) {
				try {
					s.close();
				} catch (IOException e) {
				}
			}
		}
		/*
		 * running result:
			58006 17-09-10 13:33:26 50 0 0   0.0 UTC(NIST) *
		 */
	}
}
