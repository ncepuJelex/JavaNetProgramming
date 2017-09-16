package com.jel.tech.net.ch09;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 回声服务器，这里使用了nio技术
 * @author jelex.xu
 * @date 2017年9月15日
 */
public class EchoServer {

	public static final int PORT = 1717;

	public static void main(String[] args) {

		ServerSocketChannel serverChannel;
		Selector selector;
		try {
			serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket();
			InetSocketAddress address = new InetSocketAddress(PORT);
			ss.bind(address);
			serverChannel.configureBlocking(false); //非阻塞异步模型
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT); //等待客户端的连接
		} catch (IOException e) {
			e.printStackTrace();
			//这里出错，那还玩个毛啊！
			return;
		}
		/*
		 * 一直处理连接请求
		 */
		while(true) {
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while(iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove(); //这个很重要，不然一直重复处理

				try {
					if(selectionKey.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
						SocketChannel client = server.accept();
						System.out.println("Accept connection from: " + client);
						client.configureBlocking(false); //又是异步
						client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
					}
					if(selectionKey.isReadable()) {
						SocketChannel channel = (SocketChannel) selectionKey.channel();
						ByteBuffer out = (ByteBuffer)selectionKey.attachment();
						channel.read(out);
					}
					if(selectionKey.isWritable()) {
						SocketChannel channel = (SocketChannel) selectionKey.channel();
						ByteBuffer out = (ByteBuffer) selectionKey.attachment();
						out.flip();
						channel.write(out);
						out.compact();
					}
				} catch (IOException e) {
					e.printStackTrace();
					//取消掉
					selectionKey.cancel();
					try {
						selectionKey.channel().close();
					} catch (IOException e1) {
					}
				}
			}
		}
	}
}
