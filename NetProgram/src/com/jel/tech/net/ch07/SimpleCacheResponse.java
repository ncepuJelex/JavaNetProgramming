package com.jel.tech.net.ch07;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 一个简单的缓存-3
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class SimpleCacheResponse extends CacheResponse {

	private final Map<String, List<String>> headers;
	private final SimpleCacheRequest request;
	private final Date expire;
	private final CacheControl control;



	public SimpleCacheResponse(SimpleCacheRequest request, URLConnection uc, CacheControl control) {
		this.request = request;
		this.control = control;
		this.expire = new Date(uc.getExpiration());
		this.headers = Collections.unmodifiableMap(uc.getHeaderFields());
	}

	@Override
	public Map<String, List<String>> getHeaders() throws IOException {
		return headers;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(request.getData());
	}

	public boolean isExpired() {
		Date now = new Date();
		//cache-control是http1.1版本，它比http1.0的expires级别要高，
		//优先级高
		if(control.getMaxAge().before(now)) {
			return true;
		}
		else if(expire != null && control.getMaxAge() != null) {
			return expire.before(now);
		}
		else {
			return false;
		}
	}

	public CacheControl getControl() {
		return control;
	}

}
