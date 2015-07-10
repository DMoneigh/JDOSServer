package com.jdos.packet.impl;

import java.io.DataInputStream;
import java.io.IOException;

import com.jdos.packet.Packet;

/**
 * The stop packet.
 * 
 * @author Desmond Jackson
 */
public class StopPacket extends Packet {

	/**
	 * Creates the stop packet.
	 */
	public StopPacket() {
		super(1);
	}

	@Override
	public void execute(DataInputStream din) throws IOException {
		din.readInt(); // End of Packet
		String[] command = new String[] {"killall", "-9", "hping3"};
		try {
			Runtime.getRuntime().exec(command).waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
