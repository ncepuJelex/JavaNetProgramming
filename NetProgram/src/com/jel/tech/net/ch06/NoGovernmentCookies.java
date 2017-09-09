package com.jel.tech.net.ch06;

import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
/**
 * 自定义cookie策略
 * 政府部门的网站不要保留cookie.
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class NoGovernmentCookies implements CookiePolicy {

	@Override
	public boolean shouldAccept(URI uri, HttpCookie cookie) {
		if(uri.getAuthority().toLowerCase().endsWith(".gov") ||
				cookie.getDomain().toLowerCase().endsWith(".gov")) {
			return false;
		}
		return true;
	}

}
