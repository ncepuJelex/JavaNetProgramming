package com.jel.tech.net.ch10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * This test can highlight problems that aren’t apparent in the ASCII chargen
 * protocol, such as an old gateway configured to strip off the high-order bit
 * of every byte, throw away every 230 byte, or put into diagnostic mode by an
 * unexpected sequence of control characters.
 * int 产生器
 *	1. The client connects to the server.
	2. The server immediately begins sending four-byte,big-endian integers,
		starting with 0 and incrementing by 1 each time. The server will
		eventually wrap around into the negative numbers.
	3. The server runs indefinitely.The client closes the connection when it’s had enough.
 * @author jelex.xu
 * @date 2017年9月17日
 */
public class IntgenServer {

	public static final int PORT = 1919;

	public static void main(String[] args) {

		ServerSocketChannel serverChannel;
		Selector selector;
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(PORT));
			serverChannel.configureBlocking(false);
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
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
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if(key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						System.out.println("Accept connection from: " + client);
						SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);

						ByteBuffer output = ByteBuffer.allocate(4);
						output.putInt(0);
						output.flip();
						key2.attach(output);
					} else if(key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer output = (ByteBuffer) key.attachment();
						if(!output.hasRemaining()) {
							output.rewind();
							int value = output.getInt();
							output.clear();
							output.putInt(value+1);
							output.flip();
						}
						client.write(output);
					}
				} catch(IOException e) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e1) {
					}
				}
			}
		}
	}
}
