package com.jel.tech.net.ch02;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Java 7 introduces the try with resources construct to make this cleanup
 * neater.The finally clause is no longer needed. Java automatically invokes
 * close() on any AutoCloseable objects declared inside the argument list of the
 * try block.
 *
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class TryResourceTest {

	public static void main(String[] args) {

		try (OutputStream out = new FileOutputStream("a.txt")) {
			out.write(33);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
