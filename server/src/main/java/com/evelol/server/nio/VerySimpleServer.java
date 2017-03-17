import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * NIO 不实用selector ， 在非阻塞模式下完成server相应。
 * 看来channel并不需要和selector一起使用也可以读写操作啊。
 * 只不过，非阻塞的轮需比较耗费资源罢了。
 */
public class VerySimpleServer {
	public static String GREETING = "Hello World\r\n";

	public static void main(String[] args) throws Exception {
		int port = 1234;

		ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());

		ServerSocketChannel ssc = ServerSocketChannel.open();

		ssc.socket().bind(new InetSocketAddress(port));

		ssc.configureBlocking(false);

		while (true) {
			System.out.println("waiting for connecting....");

			SocketChannel sc = ssc.accept();
			if (null == sc) {
				Thread.sleep(3000);
			} else {
				//只能响应一次连接
				System.out.println("Incoming connecting from : " + sc.socket().getRemoteSocketAddress());
				sc.write(buffer);
				System.out.println("write 1");


				ByteBuffer allocate = ByteBuffer.allocate(1024);
				sc.read(allocate);
				allocate.flip();


				CharBuffer decode = StandardCharsets.ISO_8859_1.decode(allocate);//正确解析出字母！！
				//CharBuffer decode = allocate.asCharBuffer();// 会是乱码！！
				// 因为浏览器最终传输的字符编码，就是ISO_8859_1
				// 试试中文 Referer: http://localhost:1234/?s=%E4%B8%AD%E5%9B%BD  可见urlencode了

				System.out.println(decode.toString());
				buffer.rewind();
				sc.write(buffer);

				System.out.println("write 2");
				buffer.rewind();
				sc.close();
				//每次响应后就立即断开连接

				// 想实现长lian连接,可以在后面不断读取，不是问题。但是，你显然不能用close来作为客户端判断结束的标志了。
				// 比如http的keep alive的结尾协议。

			}

		}

	}
}  