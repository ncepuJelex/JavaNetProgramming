package com.jel.tech.net.ch05;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * 测试URLEncoder的encode功能，把非ascii字符转换能ascii字符，
 * URI经常需要这么干
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class EncoderTest {

	public static void main(String[] args) {

		try {
			System.out.println(URLEncoder.encode("This string has spaces", "UTF-8"));
			System.out.println(URLEncoder.encode("this*string*has*asterisks",  "UTF-8"));
			System.out.println(URLEncoder.encode("this%string%has%percent%signs", "UTF-8"));
			System.out.println(URLEncoder.encode("this+string+has+pluses", "UTF-8"));
			System.out.println(URLEncoder.encode("this/string/has/slashes", "UTF-8"));
			System.out.println(URLEncoder.encode("this\"string\"has\"quotes", "UTF-8"));
			System.out.println(URLEncoder.encode("this:string:has:colons", "UTF-8"));
			System.out.println(URLEncoder.encode("this~string~has~tildes", "UTF-8"));
			System.out.println(URLEncoder.encode("this(string)has(parentheses)", "UTF-8"));
			System.out.println(URLEncoder.encode("this.string.has.periolds.", "UTF-8"));
			System.out.println(URLEncoder.encode("this=string=has=equal=sings", "UTF-8"));
			System.out.println(URLEncoder.encode("this&string&has&ampersands", "UTF-8"));
			System.out.println(URLEncoder.encode("Thiséstringéhase ́non-ASCII characters", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*
		 * running result:
			This+string+has+spaces
			this*string*has*asterisks
			this%25string%25has%25percent%25signs
			this%2Bstring%2Bhas%2Bpluses
			this%2Fstring%2Fhas%2Fslashes
			this%22string%22has%22quotes
			this%3Astring%3Ahas%3Acolons
			this%7Estring%7Ehas%7Etildes
			this%28string%29has%28parentheses%29
			this.string.has.periolds.
			this%3Dstring%3Dhas%3Dequal%3Dsings
			this%26string%26has%26ampersands
			Thise%CC%81stringe%CC%81hase+%CC%81non-ASCII+characters
		 */
	}
}
