package com.jel.tech.net.ch05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * http 代理服务器,要让这个服务器起效，
 * 还需要设置ProxySelector.setDefault(this java bean);
 * 另外：each virtual machine has exactly one ProxySelector
 *
 * You normally shouldn’t use this in code running in a shared environment.
 * For instance, you wouldn’t change the ProxySelector in a servlet because that
 * would change the ProxySelector for all servlets running in the same
 * container.
 *
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class LocalProxySelector extends ProxySelector {
	// 代理失败的请求URI列表
	private List<URI> failed = new ArrayList<>();

	@Override
	public List<Proxy> select(URI uri) {
		List<Proxy> result = new ArrayList<>();
		// 对于那些代理失败，或者不是http协议的请求，直接让它们去连接server
		// 代理不管这些！！
		if (failed.contains(uri) || !"http".equalsIgnoreCase(uri.getScheme())) {
			result.add(Proxy.NO_PROXY);
		} else {
			SocketAddress proxyAddr = new InetSocketAddress("proxy.example.com", 8000);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
			result.add(proxy);
		}
		return result;
	}

	/*
	 * 回调方法：告之proxy并没有替client去连接server
	 * 直接把代理失败的uri保存到列表中，以便让这些uri请求去直连server.
	 */
	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		failed.add(uri);
	}

}
