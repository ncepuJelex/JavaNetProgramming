package com.jel.tech.net.ch10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

public class NonblockingSingleFileHTTPServer {

	private ByteBuffer contentBuf;
	private static final int PORT = 8080;

	public NonblockingSingleFileHTTPServer(ByteBuffer data, String encoding, String mimeType) {

		String header = "HTTP/1.1 200OK\r\n"
				+ "Server: NonblockingSingleFileHTTPServer\r\n"
				+ "Content-length: " + data.limit() + "\r\n"
				+ "Content-type: " + mimeType + "\r\n\r\n";

		byte[] headerData = header.getBytes(Charset.forName(encoding));
		ByteBuffer buf = ByteBuffer.allocate(data.limit() + headerData.length);
		buf.put(headerData);
		buf.put(data);
		buf.flip();
		this.contentBuf = buf;
	}

	public void start() throws IOException {

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(PORT));
		serverChannel.configureBlocking(false);

		Selector selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		while(true) {
			selector.select();
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while(keys.hasNext()) {
				SelectionKey key = keys.next();
				keys.remove();

				try {
					if(key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					}
					else if(key.isReadable()) {
						//假装读取content http header,解析下...
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buf = ByteBuffer.allocate(4096);
						channel.read(buf);
						//解析header内容，决定发送什么数据到client.这里就算了
						//因为只发送一个固定文件给client,所以就不看header头
						//直接对写操作感兴趣
						key.interestOps(SelectionKey.OP_WRITE);
						//这里用了duplicate()，异步的好处，各发送各的，而且每个
						//buffer 有它自己的position,limit等属性值
						key.attach(contentBuf.duplicate());
					}
					else if(key.isWritable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buf = (ByteBuffer) key.attachment();
						if(buf.hasRemaining()) {
							channel.write(buf);
						} else {
							//we are done.
							channel.close();
						}
					}
				} catch (Exception e) {
					//client 取消操作了
					key.cancel();
					try {
						key.channel().close();
					} catch (Exception e1) {
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("输入要发送的文件.");
		Scanner sc = new Scanner(System.in);
		String filePath = sc.next();
		sc.close();
		String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
		//还能这样搞！
		Path file = FileSystems.getDefault().getPath(filePath);
		try {
			byte[] data = Files.readAllBytes(file);
			ByteBuffer input = ByteBuffer.wrap(data);
			String encoding = "UTF-8";
			NonblockingSingleFileHTTPServer server =
					new NonblockingSingleFileHTTPServer(input,encoding,contentType);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * running result:
	 * 输入：a.txt
	 * Jelex:~ zhenhua$ telnet localhost 8080
		Trying ::1...
		Connected to localhost.
		Escape character is '^]'.
		aa
		输出：HTTP/1.1 200OK
		Server: NonblockingSingleFileHTTPServer
		Content-length: 39
		Content-type: text/plain

		test SingleFileHttpHandler 服务器。Connection closed by foreign host.
		Jelex:~ zhenhua$
	 */
}
