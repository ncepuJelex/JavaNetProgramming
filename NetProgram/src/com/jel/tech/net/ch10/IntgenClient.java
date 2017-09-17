package com.jel.tech.net.ch10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;
/**
 * int 产生器客户端，这里使用阻塞模式，
 * 非阻塞模式可能会写一半int值进去，会出问题。
 *
 * @author jelex.xu
 * @date 2017年9月17日
 */
public class IntgenClient {

	public static final int PORT = 1919;

	public static void main(String[] args) {

		SocketAddress addr = new InetSocketAddress("localhost", PORT);
		try {
			SocketChannel client = SocketChannel.open(addr);
			ByteBuffer buf = ByteBuffer.allocate(4);
			IntBuffer view = buf.asIntBuffer();

			for(int expected=0; ; expected++) {
				client.read(buf);
				int actual = view.get();
				buf.clear();
				view.rewind();
				if(actual != expected) {
					System.err.println("Expected: " + expected);
					break;
				}
				System.out.println(actual);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
