package com.jel.tech.net.ch08;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * demonstrates when speaking other protocols, you may encounter data formats
 * even more alien to Java.
 * For instance, a few network protocols use 64-bit fixed- point numbers.
 * There’s no shortcut to handle all possible cases. You
 * simply have to grit your teeth and code the math you need to handle the data
 * in whatever format the server sends
 *
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class Time {

	private static final String HOSTNAME = "time.nist.gov";

	public static void main(String[] args) {
		Date d = Time.GetDateFromNetwork();
		System.out.println("It is " + d);
		/*
		 * 遗憾的是：没有能够连接服务器成功，都是Connection refused,哎
		 */
	}
	public static Date GetDateFromNetwork() {
		// The time protocol sets the epoch at 1900,
		// the Java Date class at 1970. This number
		// converts between them.
		long differenceBetweenEpochs = 2208988800L;
		/*
		 * 上面的整数是这样算出来的！
		 */
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		Calendar epoch1900 = Calendar.getInstance(gmt);
		epoch1900.set(1900, 01, 01, 00, 00, 00);
		long epoch1900ms = epoch1900.getTime().getTime();
		//long epoch1900ms2 = epoch1900.getTimeInMillis(); //这样也行！

		Calendar epoch1970 = Calendar.getInstance(gmt);
		epoch1970.set(1970, 01, 01, 00, 00, 00);
		long epoch1970ms = epoch1970.getTime().getTime();

		long differenceInMS = epoch1970ms - epoch1900ms;
		long differenceBetweenEpochs2 = differenceInMS / 1000;
System.out.println(differenceBetweenEpochs == differenceBetweenEpochs2); //true
		Socket socket = null;
		try {
			socket = new Socket(HOSTNAME, 37);
			socket.setSoTimeout(15000);
			InputStream raw = socket.getInputStream();

			long secondsSince1900 = 0;
			for(int i=0; i<4; i++) {
				secondsSince1900 = (secondsSince1900<<8) + raw.read();
			}
			long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
			long msSince1970 = secondsSince1970 * 1000;
			Date time = new Date(msSince1970);
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
		}
		return null;
	}
}
