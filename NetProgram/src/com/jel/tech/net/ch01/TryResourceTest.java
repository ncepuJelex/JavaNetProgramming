package com.jel.tech.net.ch01;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class TryResourceTest {

	public static void main(String[] args) {

		try(OutputStream out = new FileOutputStream("a.txt")) {
			out.write(33);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
