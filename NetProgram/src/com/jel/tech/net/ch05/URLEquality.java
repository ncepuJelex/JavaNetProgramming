package com.jel.tech.net.ch05;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 比较2个URL是否相等
 * The URL class also has a sameFile() method that checks whether
 * two URLs point to the same resource:
 * 		public boolean sameFile(URL other)
 * The comparison is essentially the same as with equals(), DNS queries included,
 * except that sameFile() does not consider the fragment identifier. This
 * sameFile() returns true when comparing http://www.oreilly.com/index.html#p1
 * and http://www.oreil‐ ly.com/index.html#q2 while equals() would return false.
 *
 * @author jelex.xu
 * @date 2017年9月6日
 */
public class URLEquality {

	public static void main(String[] args) {

		try {
			URL www = new URL("http://www.ibiblio.org/");
			URL ibiblio = new URL("http://ibiblio.org/");
			if (ibiblio.equals(www)) {
				System.out.println("is the same");
			} else {
				System.out.println("not the same.");
			}

			URL u1 = new URL("http://www.ncsa.uiuc.edu/HTMLPrimer.html#GS");
			URL u2 = new URL("http://www.ncsa.uiuc.edu/HTMLPrimer.html#HD");
			if (u1.sameFile(u2)) {
				System.out.println(u1 + " is the same file as \n" + u2); }
			else {
		      System.out.println(u1 + " is not the same file as \n" + u2);
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		/*
		 * output:
		 * is the same
			http://www.ncsa.uiuc.edu/HTMLPrimer.html#GS is the same file as
			http://www.ncsa.uiuc.edu/HTMLPrimer.html#HD
		 */
	}
}
