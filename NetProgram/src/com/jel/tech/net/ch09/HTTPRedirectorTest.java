package com.jel.tech.net.ch09;

import java.util.Arrays;

import org.junit.Test;

public class HTTPRedirectorTest {

	@Test
	public void test() {
		String s = "GET /directory/filename.html HTTP/1.0";
		String[] split = s.split(" ");
		System.out.println(Arrays.toString(split));
	}

}
