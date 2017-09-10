package com.jel.tech.net.ch07;

import java.util.Date;
import java.util.Locale;
/**
 * The Cache-control header (HTTP 1.1) offers fine-grained cache policies:
	—max-age=[seconds]: Number of seconds from now before the cached entry should expire
	—s-maxage=[seconds]: Number of seconds from now before the cached entry should expire from a shared cache.
		Private caches can store the entry for longer.
	—public: OK to cache an authenticated response. Otherwise authenticated re‐ sponses are not cached.
	— private: Only single user caches should store the response; shared caches should not.
	— no-cache: Not quite what it sounds like. The entry may still be cached, but the client
	 	should reverify the state of the resource with an ETag or Last-modified header on each access.
	— no-store: Do not cache the entry no matter what.

Cache-control overrides Expires if both are present.

A server can send multiple Cache-control headers
 in a single header as long as they don’t conflict.

• The ETag header (HTTP 1.1) is a unique identifier for the resource
 that changes when the resource does.
 A client can use a HEAD request to check this and only come back
  for a full GET if its local cached copy has a different ETag.
  一个简单的缓存-1
 * @author jelex.xu
 * @date 2017年9月10日
 */
public class CacheControl {

	private Date maxAge = null;
	private Date sMaxAge = null;
	private boolean mustRevalidate = false;
	private boolean noCache = false;
	private boolean nostore = false;
	private boolean proxyRevalidate = false;
	private boolean publicCache = false;
	private boolean privateCache = false;

	public CacheControl(String s) {
		if(s == null || !s.contains(":")) {
			return; //default policy
		}
		String value = s.split(":")[1].trim();
		String[] components = value.split(",");

		Date now = new Date();
		for(String c : components) {
			try {
				c = c.trim().toLowerCase(Locale.CHINA);
				if(c.startsWith("max-age=")) {
					int secondsInTheFuture = Integer.parseInt(c.substring(8));
					maxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
				}
				else if(c.startsWith("s-maxage=")) {
					int secondsInTheFuture = Integer.parseInt(c.substring(8));
					sMaxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
				}
				else if(c.equals("must-revalidate")) {
					mustRevalidate = true;
				}
				else if(c.equals("proxy-revalidate")) {
					proxyRevalidate = true;
				}
				else if(c.equals("no-cache")) {
					noCache = true;
				}
				else if(c.equals("public")) {
					publicCache = true;
				}
				else if(c.equals("private")) {
					privateCache = true;
				}
			} catch (RuntimeException e) {
				continue;
			}
		}
	}

	public Date getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Date maxAge) {
		this.maxAge = maxAge;
	}

	public Date getsMaxAge() {
		return sMaxAge;
	}

	public void setsMaxAge(Date sMaxAge) {
		this.sMaxAge = sMaxAge;
	}

	public boolean isMustRevalidate() {
		return mustRevalidate;
	}

	public void setMustRevalidate(boolean mustRevalidate) {
		this.mustRevalidate = mustRevalidate;
	}

	public boolean isNoCache() {
		return noCache;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public boolean isNostore() {
		return nostore;
	}

	public void setNostore(boolean nostore) {
		this.nostore = nostore;
	}

	public boolean isProxyRevalidate() {
		return proxyRevalidate;
	}

	public void setProxyRevalidate(boolean proxyRevalidate) {
		this.proxyRevalidate = proxyRevalidate;
	}

	public boolean isPublicCache() {
		return publicCache;
	}

	public void setPublicCache(boolean publicCache) {
		this.publicCache = publicCache;
	}

	public boolean isPrivateCache() {
		return privateCache;
	}

	public void setPrivateCache(boolean privateCache) {
		this.privateCache = privateCache;
	}

}
