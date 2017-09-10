package com.jel.tech.net.ch07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;

/**
 * By default, Java does not cache anything.
 * To install a system-wide cache of * the URL class will use, you need the following:
 *  • A concrete subclass of ResponseCache
 *  • A concrete subclass of CacheRequest
 *  • A concrete subclass of CacheResponse
 *  You install your subclass of ResponseCache that works with your
 * subclass of CacheRe quest and CacheResponse
 * by passing it to the static method ResponseCache.setDe fault().
 * This installs your cache object as the system default.
 * A Java virtual machine can only support a single shared cache.
 *
 *一个简单的缓存-2
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class SimpleCacheRequest extends CacheRequest {
	/*
	 * An obvious alternative strategy would be to store results in
	 * files and use a FileOutputStream instead.
	 */
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Override
	public OutputStream getBody() throws IOException {
		return out;
	}

	@Override
	public void abort() {
		out.reset();
	}

	public byte[] getData() {
		if(out.size() == 0) {
			return null;
		}
		return out.toByteArray();
	}
}
