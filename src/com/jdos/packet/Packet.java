package com.jdos.packet;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a packet.
 * 
 * @author Desmond Jackson
 */
public abstract class Packet extends Object {
	
	/**
	 * The packet id.
	 */
	private int id;
	
	/**
	 * Creates the packet.
	 * 
	 * @param id The packet id
	 */
	public Packet(int id) {
		super();
		this.id = id;
	}
	
	/**
	 * Gets the packet id.
	 * 
	 * @return The packet id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Executes the packet code.
	 * 
	 * @param din The data input stream to use in execution
	 * 
	 * @throws IOException
	 */
	public abstract void execute(DataInputStream din) throws IOException ;

}
