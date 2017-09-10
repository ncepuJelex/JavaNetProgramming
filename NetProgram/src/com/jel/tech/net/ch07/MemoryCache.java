package com.jel.tech.net.ch07;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is suitable for a single-user, private cache (because it ignores
 * the private and public attributes of Cache-control).
 *  一个简单的缓存-4
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class MemoryCache extends ResponseCache {

	private final Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<>();
	private final int maxEntries;

	public MemoryCache() {
		this(100);
	}
	public MemoryCache(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
		if("GET".equals(rqstHeaders)) {
			SimpleCacheResponse response = responses.get(uri);
			//如果缓存失效，去除
			if(response != null && response.isExpired()) {
				responses.remove(uri);
				response = null;
			}
			return response;
		} else {
			return null;
		}
	}

	@Override
	public CacheRequest put(URI uri, URLConnection conn) throws IOException {
		if(responses.size() >= 100) return null;
		CacheControl control = new CacheControl(conn.getHeaderField("Cache-Control"));
		if(control.isNostore()) return null;
		else if(!conn.getHeaderField(0).startsWith("GET ")) {
			//only cache GET
			return null;
		}
		SimpleCacheRequest request = new SimpleCacheRequest();
		SimpleCacheResponse response = new SimpleCacheResponse(request, conn, control);
		responses.put(uri, response);
		return request;
	}

}
